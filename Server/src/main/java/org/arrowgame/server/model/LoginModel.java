package org.arrowgame.server.model;

import org.arrowgame.server.ServerApplication;
import org.arrowgame.server.utils.DatabaseService;

public class LoginModel  {
    private final DatabaseService databaseService;

    public LoginModel() {
        this.databaseService = ServerApplication.context.getBean(DatabaseService.class);
    }

    public boolean authenticate(String username, String password) {
        return databaseService.authenticate(username, password);
    }

}
