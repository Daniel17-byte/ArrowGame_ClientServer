package org.arrowgame.client.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import lombok.Getter;
import org.arrowgame.client.utils.LanguageManager;

@Getter
public class LoginView extends Scene {
    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Button loginButton = new Button(LanguageManager.getString("loginButton"));
    private final Label resultLabel = new Label();
    private final ComboBox<String> languageComboBox = new ComboBox<>();
    private final Button registerButton = new Button(LanguageManager.getString("registerButton"));

    public LoginView() {
        super(new VBox(), 300, 200);
        initComponents();
    }

    public void initComponents() {
        VBox root = (VBox) getRoot();
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        usernameField.setPromptText(LanguageManager.getString("usernameField"));
        passwordField.setPromptText(LanguageManager.getString("passwordField"));
        languageComboBox.getItems().addAll("ENGLISH", "ROMANIAN", "DEUTSCH", "ITALIAN", "FRENCH");
        languageComboBox.setPromptText("Language ");

        root.getChildren().addAll(usernameField, passwordField, languageComboBox, loginButton, resultLabel, registerButton);
    }

}
