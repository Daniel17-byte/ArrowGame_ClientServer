package org.arrowgame.server.controller;

import org.arrowgame.server.forms.AddUserForm;
import org.arrowgame.server.forms.UpdateUserForm;
import org.arrowgame.server.model.AdminModel;
import org.arrowgame.server.model.UserModel;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class AdminController {
    private final AdminModel model;

    public AdminController() {
        this.model = new AdminModel();
    }

    @PostMapping("/addUser")
    public UserModel addUser(@RequestBody AddUserForm addUserForm) {
        boolean success = model.register(addUserForm.getUsername(), addUserForm.getPassword(), addUserForm.getUserType().name());

        if (success) {
            return model.getUserByUsername(addUserForm.getUsername());
        } else {
            System.out.println("User not added!");
        }

        return null;
    }

    @PutMapping("/updateUser")
    public ArrayList<UserModel> updateUser(@RequestBody UpdateUserForm updateUserForm) {
        UserModel updatedUser = model.updateUser(updateUserForm.getUserModel().getUserName(), updateUserForm.getUsername(), updateUserForm.getPassword(), updateUserForm.getUserType().name());

        if (updatedUser == null) {
            System.out.println("User not updated!");
            return null;
        }

        return model.getUsers();
    }

    @DeleteMapping("/deleteUser")
    public boolean deleteUser(@RequestParam String username) {
        boolean success = model.deleteUser(username);

        if (!success) {
            System.out.println("User not deleted!");
            return false;
        }

        return true;
    }

}
