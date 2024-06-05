package org.arrowgame.client.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.arrowgame.client.responses.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Nullable;

public class Endpoints {
    private static final String BASE_URL = "http://localhost:9180";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String clickedStartGame(String selectedBoard) {
        String url = String.format("%s/startGame?selectedBoard=%s", BASE_URL, selectedBoard);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        return getBody(request);
    }

    public static List<ResultMoveResponse> userRegisterMove(int row, int col, String direction, int difficulty) {
        String url = String.format("%s/registerMove", BASE_URL);

        MoveForm moveForm = new MoveForm(row, col, direction, difficulty);
        String requestBody;

        try {
            requestBody = objectMapper.writeValueAsString(moveForm);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<>() {});
            } else {
                System.out.println(STR."Error: \{response.statusCode()}");
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean authenticate(String username, String password) {
        String url = STR."\{BASE_URL}/authenticate?username=\{username}&password=\{password}";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return getBooleanFromResponse(client, request);
    }

    private static boolean getBooleanFromResponse(HttpClient client, HttpRequest request) {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return Boolean.parseBoolean(response.body());
            } else {
                return false;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean register(String username, String password, String usertype) {
        String url = String.format("%s/register?username=%s&password=%s&usertype=%s", BASE_URL, username, password, usertype);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        return getBooleanFromResponse(client, request);

    }

    public static UserListElement addUser(String username, String password, String userType) {
        String url = String.format("%s/addUser", BASE_URL);

        AddUserForm addUserForm = new AddUserForm(username, password, userType);
        String requestBody;

        try {
            requestBody = objectMapper.writeValueAsString(addUserForm);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), UserListElement.class);
            } else {
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void updateUser(String currentUsername, String newUsername, String newPassword, String userType) {
        String url = String.format("%s/updateUser", BASE_URL);

        UpdateUserForm updateUserForm = new UpdateUserForm(new UserForm(currentUsername, newPassword, userType), newUsername, newPassword, userType);
        String requestBody;

        try {
            requestBody = objectMapper.writeValueAsString(updateUserForm);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean deleteUser(String username) {
        String url = String.format("%s/deleteUser?username=%s", BASE_URL, username);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200;
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static ArrayList<MoveModel> undoMove() {
        String url = String.format("%s/undo", BASE_URL);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(response.body(), new TypeReference<>() {});
            } else {
                System.out.println(STR."Failed to undo move: \{response.statusCode()}");
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(STR."Error occurred: \{e.getMessage()}");
            return null;
        }
    }

    public static String getUsers() {
        String url = String.format("%s/getUserList", BASE_URL);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return getBody(request);
    }

    @Nullable
    private static String getBody(HttpRequest request) {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public static UserResponse getUser() {
        String url = STR."\{BASE_URL}/getLoggedInUser";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), UserResponse.class);
            } else {
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<UserListElement> getUsersList() {
        String url = String.format("%s/getUsers", BASE_URL);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<ArrayList<UserListElement>>() {});
            } else {
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<GameResponse> getGames() {
        String url = String.format("%s/getGames", BASE_URL);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<>() {});
            } else {
                System.out.println(STR."Error: \{response.statusCode()}");
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
