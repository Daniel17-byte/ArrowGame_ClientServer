package org.arrowgame.client.utils;

import javafx.stage.Stage;
import org.arrowgame.client.view.AdminView;
import org.arrowgame.client.view.GameView;
import org.arrowgame.client.view.LoginView;
import org.arrowgame.client.view.RegisterView;

public class OpenViews {
    public void showLogin() {
        LanguageManager.loadLanguage(CustomLocale.ENGLISH);
        LoginView loginView = new LoginView();
        Stage loginStage = new Stage();

        loginStage.setScene(loginView);
        loginStage.setTitle(LanguageManager.getString("loginButton"));
        loginStage.show();
    }

    public void showRegisterResult(String username, String password, String usertype, String language) {
        boolean success = Endpoints.register(username, password, usertype);

        if (success) {
            LanguageManager.loadLanguage(LanguageManager.fromStringToLocale(language));
            Stage gameStage = new Stage();
            GameView gameView = new GameView();

            gameStage.setScene(gameView);
            gameStage.setTitle(LanguageManager.getString("arrowGame"));
            gameStage.show();
        }

    }

    public void showLoginResult(String username, String password, String language) {
        LanguageManager.loadLanguage(LanguageManager.fromStringToLocale(language));
        boolean success = Endpoints.authenticate(username, password);

        if (success) {
            Stage gameStage = new Stage();
            GameView gameView = new GameView();

            gameStage.setScene(gameView);
            gameStage.setTitle(LanguageManager.getString("arrowGame"));
            gameStage.show();
        }

    }

    public void openRegisterWindow() {
        Stage registerStage = new Stage();
        RegisterView registerView = new RegisterView();

        registerStage.setScene(registerView);
        registerStage.setTitle(LanguageManager.getString("registerButton"));
        registerStage.show();
    }

    public static void clickedManageUsersButton() {
        Stage adminStage = new Stage();
        AdminView adminView = new AdminView();

        adminStage.setScene(adminView);
        adminStage.setTitle(LanguageManager.getString("adminPanel"));
        adminStage.show();
    }
}
