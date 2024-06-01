package org.arrowgame.server.controller;

import org.arrowgame.server.model.LoginModel;
import org.arrowgame.server.model.RegisterModel;
import org.arrowgame.server.model.UserModel;
import org.arrowgame.server.model.UserType;
import org.arrowgame.server.utils.SessionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final LoginModel loginModel;
    private final RegisterModel registerModel;

    public UserController() {
        this.registerModel = new RegisterModel();
        this.loginModel = new LoginModel();
    }

    @GetMapping("/authenticate")
    public boolean authenticate(@RequestParam String username, @RequestParam String password) {
        return loginModel.authenticate(username, password);
    }

    @GetMapping("/getLoggedInUser")
    public UserModel getUser() {
        return SessionManager.getUserFromSession();
    }

    @PostMapping("/register")
    public boolean register(@RequestParam String username, @RequestParam String password, @RequestParam String usertype) {
        return registerModel.register(username, password, usertype);
    }

}
