package org.arrowgame.server.controller;

import org.arrowgame.server.forms.UserForm;
import org.arrowgame.server.forms.UpdateUserForm;
import org.arrowgame.server.forms.UserListElement;
import org.arrowgame.server.model.AdminModel;
import org.arrowgame.server.model.UserModel;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminController {
    private final AdminModel model;

    public AdminController() {
        this.model = new AdminModel();
    }


    @PostMapping("/addUser")
    public UserListElement addUser(@RequestBody UserForm addUserForm) {
        boolean success = model.register(addUserForm.getUserName(), addUserForm.getPassword(), addUserForm.getUserType());

        if (success) {
            UserModel u = model.getUserByUsername(addUserForm.getUserName());
            return new UserListElement(u.getUserName(), u.getUserType().name(), u.getGamesWon());
        } else {
            System.out.println("User not added!");
        }

        return null;
    }

    @PutMapping("/updateUser")
    public void updateUser(@RequestBody UpdateUserForm updateUserForm) {
        UserModel updatedUser = model.updateUser(updateUserForm.getUserModel().getUserName(), updateUserForm.getUsername(), updateUserForm.getPassword(), updateUserForm.getUserType().name());

        if (updatedUser == null) {
            System.out.println("User not updated!");
        }
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

    @GetMapping("/getUsers")
    public List<UserListElement> getUsers() {
        List<UserListElement> response = new ArrayList<>();
        model.getUsers().forEach( u -> response.add(new UserListElement(u.getUserName(), u.getUserType().name(), u.getGamesWon())));
        return response;
    }

}
