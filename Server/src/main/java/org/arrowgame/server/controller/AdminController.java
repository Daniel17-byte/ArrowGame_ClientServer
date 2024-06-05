package org.arrowgame.server.controller;

import org.arrowgame.server.forms.UserForm;
import org.arrowgame.server.forms.UpdateUserForm;
import org.arrowgame.server.forms.UserListElement;
import org.arrowgame.server.model.AdminModel;
import org.arrowgame.server.model.UserModel;
import org.arrowgame.server.utils.EmailService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class AdminController {
    private final AdminModel model;
    private final EmailService emailService;

    public AdminController(EmailService emailService) {
        this.emailService = emailService;
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

        if (updatedUser != null) {
            UserModel userModel = model.getUserByUsername(Objects.requireNonNull(updatedUser).getUserName());
            emailService.sendEmail(userModel.getEmail(), "NEW DATA", userModel.data());
        }

    }

    @GetMapping("/sendMail")
    public void sendMail() {
        emailService.sendEmail("lungud63@yahoo.com", "NEW DATA", "data");
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
