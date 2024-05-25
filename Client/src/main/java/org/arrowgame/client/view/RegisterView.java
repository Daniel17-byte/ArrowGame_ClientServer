package org.arrowgame.client.view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.arrowgame.client.utils.LanguageManager;

@Getter
public class RegisterView extends Scene {
    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Button registerButton = new Button(LanguageManager.getString("registerButton"));
    private final Label resultLabel = new Label();
    private final ComboBox<String> userTypeComboBox = new ComboBox<>();
    private final ComboBox<String> languageComboBox = new ComboBox<>();

    public RegisterView() {
        super(new VBox(), 300, 200);
        initComponents();
    }

    public void initComponents() {
        VBox root = (VBox) getRoot();
        root.setSpacing(10);

        usernameField.setPromptText(LanguageManager.getString("usernameField"));
        passwordField.setPromptText(LanguageManager.getString("passwordField"));
        userTypeComboBox.getItems().addAll(LanguageManager.getStringForKey("userTypeComboBox", "valueAdmin"), LanguageManager.getStringForKey("userTypeComboBox", "valuePlayer"));
        userTypeComboBox.setPromptText(LanguageManager.getStringForKey("userTypeComboBox", "label"));
        languageComboBox.getItems().addAll("ENGLISH", "ROMANIAN", "DEUTSCH", "ITALIAN", "FRENCH");
        languageComboBox.setPromptText("Language ");

        root.getChildren().addAll(usernameField, passwordField, userTypeComboBox, languageComboBox, registerButton, resultLabel);
    }

}
