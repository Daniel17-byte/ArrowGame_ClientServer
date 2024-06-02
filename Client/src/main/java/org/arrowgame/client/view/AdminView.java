package org.arrowgame.client.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.arrowgame.client.responses.UserForm;
import org.arrowgame.client.responses.UserListElement;
import org.arrowgame.client.utils.Endpoints;
import org.arrowgame.client.utils.LanguageManager;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
public class AdminView extends Scene {
    private final TextField userNameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final ComboBox<String> userTypeComboBox = new ComboBox<>();
    private final Button addButton = new Button(LanguageManager.getString("addButton"));
    private final Button updateButton = new Button(LanguageManager.getString("updateButton"));
    private final Button deleteButton = new Button(LanguageManager.getString("deleteButton"));
    private final TableView<UserListElement> userTableView = new TableView<>();
    private final Label resultLabel = new Label();
    @Setter
    private UserListElement selectedUser;


    public AdminView() {
        super(new VBox(), 500, 500);
        initComponents();
    }

    public void initComponents() {
        userTypeComboBox.getItems().addAll("ADMIN", "PLAYER");

        TableColumn<UserListElement, String> userNameColumn = new TableColumn<>(LanguageManager.getString("userNameColumn"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        TableColumn<UserListElement, String> userTypeColumn = new TableColumn<>(LanguageManager.getString("userTypeColumn"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
        TableColumn<UserListElement, Integer> gamesWonColumn = new TableColumn<>(LanguageManager.getString("gamesWonColumn"));
        gamesWonColumn.setCellValueFactory(new PropertyValueFactory<>("gamesWon"));
        userTableView.getColumns().addAll(Arrays.asList(userNameColumn, userTypeColumn, gamesWonColumn));

        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                new HBox(new Label(STR."\{LanguageManager.getString("userNameColumn")} "), userNameField),
                new HBox(new Label(STR."\{LanguageManager.getString("passwordField")} "), passwordField),
                new HBox(new Label(STR."\{LanguageManager.getString("userTypeColumn")} "), userTypeComboBox),
                resultLabel,
                new HBox(addButton, updateButton, deleteButton),
                userTableView
        );
        addButton.setOnAction(_ -> {
            UserListElement user = Endpoints.addUser(userNameField.getText(), passwordField.getText(), userTypeComboBox.getValue());
            userTableView.getItems().add(user);
        });
        updateButton.setOnAction(_ -> {
            if (userTableView.getSelectionModel().getSelectedItem() != null) {
                Endpoints.updateUser(selectedUser.getUserName(), userNameField.getText(), passwordField.getText(), userTypeComboBox.getValue());
                List<UserListElement> userListElements = Endpoints.getUsersList();
                userTableView.getItems().setAll(Objects.requireNonNull(userListElements));
            }
        });
        deleteButton.setOnAction(_ -> {
            if (userTableView.getSelectionModel().getSelectedItem() != null) {
                boolean success = Endpoints.deleteUser(selectedUser.getUserName());
                if (success) {
                    userTableView.getItems().remove(selectedUser);
                }
            }
        });

        userTableView.getItems().addAll(Objects.requireNonNull(Endpoints.getUsersList()));
        userTableView.getSelectionModel().selectedItemProperty().addListener((_, _, newVal) -> setSelectedUser(newVal));
        setRoot(root);
    }

}
