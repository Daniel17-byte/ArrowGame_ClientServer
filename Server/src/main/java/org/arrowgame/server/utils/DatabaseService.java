package org.arrowgame.server.utils;

import lombok.Getter;
import org.arrowgame.server.ServerApplication;
import org.arrowgame.server.model.GameDbModel;
import org.arrowgame.server.model.UserModel;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DatabaseService {
    private Connection connection;

    @Getter
    private UserModel user;

    public DatabaseService() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/arrows", "root", "adminpass");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String usrN = resultSet.getString("username");
                String usrT = resultSet.getString("userType");
                int gamesWon = resultSet.getInt("gameswon");

                user = new UserModel(usrN,usrT,gamesWon);
                ServerApplication.sessionID = SessionManager.createSession(user);
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean register(String username, String password, String usertype) {
        String query = "INSERT INTO users (username, password, usertype) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, usertype);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                user = new UserModel(username, usertype, 0);
                ServerApplication.sessionID = SessionManager.createSession(user);
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ArrayList<UserModel> getUsers() {
        ArrayList<UserModel> users = new ArrayList<>();

        String query = "SELECT * FROM users";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String usrN = resultSet.getString("username");
                String usrT = resultSet.getString("userType");
                int gamesWon = resultSet.getInt("gameswon");
                users.add(new UserModel(usrN,usrT,gamesWon));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

    public UserModel updateUser(String username, String newUsername, String newPassword, String newUserType) {
        String query = "UPDATE users SET username = ?, password = ?, usertype = ? WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newUsername);
            statement.setString(2, newPassword);
            statement.setString(3, newUserType);
            statement.setString(4, username);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return new UserModel(newUsername, newUserType, getUser().getGamesWon());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean deleteUser(String username) {
        String query = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void updateUserScore(int difficulty) {
        String query = "UPDATE users SET gameswon = gameswon + 1 WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, getUser().getUserName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        user = getUserByUsername(user.getUserName());
        addGame(user.getUserName(), difficulty);
    }

    public UserModel getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String usrN = resultSet.getString("username");
                String usrT = resultSet.getString("userType");
                int gamesWon = resultSet.getInt("gameswon");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phoneNumber");
                return new UserModel(usrN, usrT, gamesWon, email, phoneNumber);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void addGame(String username, int difficulty) {
        String query = "INSERT INTO games (username, difficulty) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setInt(2, difficulty);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<GameDbModel> getGames() {
        List<GameDbModel> games = new ArrayList<>();
        String query = "SELECT * FROM games";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                games.add(new GameDbModel(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getInt("difficulty")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return games;
    }
}
