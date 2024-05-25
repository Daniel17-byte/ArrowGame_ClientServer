package org.arrowgame.server.model;

import org.arrowgame.server.ServerApplication;
import org.arrowgame.server.utils.DatabaseService;

public class RegisterModel {
    private final DatabaseService databaseService;

    public RegisterModel() {
        this.databaseService = ServerApplication.context.getBean(DatabaseService.class);
    }

    public boolean register(String username, String password, String usertype) {
        return databaseService.register(username, password, usertype);
    }

}
