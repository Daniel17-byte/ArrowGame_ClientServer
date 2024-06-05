package org.arrowgame.client.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.arrowgame.client.responses.GameResponse;
import org.arrowgame.client.responses.UserListElement;
import org.arrowgame.client.utils.Endpoints;
import org.arrowgame.client.utils.LanguageManager;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class AdminView extends Scene {
    private final TextField userNameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final ComboBox<String> userTypeComboBox = new ComboBox<>();
    private final Button addButton = new Button(LanguageManager.getString("addButton"));
    private final Button updateButton = new Button(LanguageManager.getString("updateButton"));
    private final Button deleteButton = new Button(LanguageManager.getString("deleteButton"));
    private final Button exportToJsonButton = new Button("JSON");
    private final Button exportToXmlButton = new Button("XML");
    private final TableView<UserListElement> userTableView = new TableView<>();
    private final Label resultLabel = new Label();
    @Setter
    private UserListElement selectedUser;
    private final Button showStatsButton = new Button("STATS");

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
                new HBox(exportToJsonButton, exportToXmlButton, showStatsButton),
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

        exportToJsonButton.setOnAction(_ -> exportToJson());
        exportToXmlButton.setOnAction(_ -> exportToXml());
        showStatsButton.setOnAction(_ -> showStats());

        userTableView.getItems().addAll(Objects.requireNonNull(Endpoints.getUsersList()));
        userTableView.getSelectionModel().selectedItemProperty().addListener((_, _, newVal) -> setSelectedUser(newVal));

        setRoot(root);
    }

    private void exportToJson() {
        List<UserListElement> users = new ArrayList<>(userTableView.getItems());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            objectMapper.writeValue(new File("users.json"), users);
            resultLabel.setText("Exported to JSON successfully!");
        } catch (IOException e) {
            resultLabel.setText(STR."Error exporting to JSON: \{e.getMessage()}");
        }
    }

    private void exportToXml() {
        List<UserListElement> users = userTableView.getItems();
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            xmlMapper.writeValue(new File("users.xml"), users);
            resultLabel.setText("Exported to XML successfully!");
        } catch (IOException e) {
            resultLabel.setText(STR."Error exporting to XML: \{e.getMessage()}");
        }
    }

    private void showStats() {
        List<GameResponse> games = Endpoints.getGames();
        if (games == null) {
            resultLabel.setText("Error fetching game data.");
            return;
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        games.stream()
                .collect(Collectors.groupingBy(GameResponse::getDifficulty, Collectors.counting()))
                .forEach((difficulty, count) -> pieChartData.add(new PieChart.Data(STR."Difficulty \{difficulty}", count)));

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Games by Difficulty Level");

        Stage chartStage = new Stage();
        BorderPane pane = new BorderPane(pieChart);
        Scene scene = new Scene(pane, 800, 600);
        chartStage.setScene(scene);
        chartStage.setTitle("Game Statistics");
        chartStage.show();
    }

}
