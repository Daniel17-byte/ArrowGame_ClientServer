package org.arrowgame.server.controller;

import org.arrowgame.server.model.AdminModel;
import org.arrowgame.server.model.UserModel;
import org.arrowgame.server.model.UserType;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class AdminController {
    private final AdminModel model;

    public AdminController() {
        this.model = new AdminModel();
    }

    public UserModel addUser(String username, String password, UserType userType) {
        boolean success = model.register(username, password, userType.name());

        if (success) {
            return model.getUserByUsername(username);
        } else {
            System.out.println("User not added!");
        }

        return null;
    }

    public ArrayList<UserModel> updateUser(UserModel userModel, String username, String password, UserType userType) {
        UserModel updatedUser = model.updateUser(userModel.getUserName(), username, password, userType.name());

        if (updatedUser == null) {
            System.out.println("User not updated!");
            return null;
        }

        return model.getUsers();
    }

    public boolean deleteUser(String username) {
        boolean success = model.deleteUser(username);

        if (!success) {
            System.out.println("User not deleted!");
            return false;
        }

        return true;
    }

}
