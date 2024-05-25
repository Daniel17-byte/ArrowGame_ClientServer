package org.arrowgame.server.model;


import org.arrowgame.server.ServerApplication;
import org.arrowgame.server.utils.DatabaseService;

import java.util.ArrayList;

public class AdminModel {
    private final DatabaseService databaseService;

    public AdminModel() {
        this.databaseService = ServerApplication.context.getBean(DatabaseService.class);
    }

    public ArrayList<UserModel> getUsers() {
        return databaseService.getUsers();
    }

    public UserModel updateUser(String username, String newUsername, String newPassword, String newUserType) {
        return databaseService.updateUser(username, newUsername, newPassword, newUserType);
    }

    public boolean deleteUser(String username) {
        return databaseService.deleteUser(username);
    }

    public UserModel getUserByUsername(String username) {
        return databaseService.getUserByUsername(username);
    }

    public boolean register(String username, String password, String usertype) {
        return databaseService.register(username, password, usertype);
    }

}
