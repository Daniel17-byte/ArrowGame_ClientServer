package org.arrowgame.client.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.arrowgame.client.responses.UserResponse;
import org.arrowgame.client.utils.Endpoints;
import org.arrowgame.client.utils.LanguageManager;

import java.util.Arrays;

@Getter
public class AdminView extends Scene {
    private final TextField userNameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final ComboBox<String> userTypeComboBox = new ComboBox<>();
    private final Button addButton = new Button(LanguageManager.getString("addButton"));
    private final Button updateButton = new Button(LanguageManager.getString("updateButton"));
    private final Button deleteButton = new Button(LanguageManager.getString("deleteButton"));
    private final TableView<UserResponse> userTableView = new TableView<>();
    private final Label resultLabel = new Label();
    @Setter
    private UserResponse selectedUser;


    public AdminView() {
        super(new VBox(), 500, 500);
        initComponents();
    }

    public void initComponents() {

        userTypeComboBox.getItems().addAll(LanguageManager.getStringForKey("userTypeComboBox", "valueAdmin"), LanguageManager.getStringForKey("userTypeComboBox", "valuePlayer"));

        TableColumn<UserResponse, String> userNameColumn = new TableColumn<>(LanguageManager.getString("userNameColumn"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        TableColumn<UserResponse, String> userTypeColumn = new TableColumn<>(LanguageManager.getString("userTypeColumn"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
        TableColumn<UserResponse, Integer> gamesWonColumn = new TableColumn<>(LanguageManager.getString("gamesWonColumn"));
        gamesWonColumn.setCellValueFactory(new PropertyValueFactory<>("gamesWon"));
        userTableView.getColumns().addAll(Arrays.asList(userNameColumn, userTypeColumn, gamesWonColumn));

        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                new HBox(new Label(LanguageManager.getString("userNameColumn") + " "), userNameField),
                new HBox(new Label(LanguageManager.getString("passwordField") + " "), passwordField),
                new HBox(new Label(LanguageManager.getString("userTypeColumn") + " "), userTypeComboBox),
                resultLabel,
                new HBox(addButton, updateButton, deleteButton),
                userTableView
        );
        addButton.setOnAction(event -> Endpoints.addUser());
        updateButton.setOnAction(event -> {
            if (userTableView.getSelectionModel().getSelectedItem() != null) {
                Endpoints.updateUser();
            }
        });
        deleteButton.setOnAction(event -> {
            if (userTableView.getSelectionModel().getSelectedItem() != null) {
                Endpoints.deleteUser();
            }
        });

        userTableView.getItems().addAll(Endpoints.getUsers());
        userTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> setSelectedUser(newVal));
        setRoot(root);
    }

}
