package org.arrowgame.server.controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.arrowgame.server.ServerApplication;
import org.arrowgame.server.model.*;
import org.arrowgame.server.utils.MinMaxStrategy;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;

@RestController
public class GameController {
    private final GameModel model;

    public GameController() {
        this.model = new GameModel();
        this.model.getComputer().setStrategy(new MinMaxStrategy(4, 10));
    }


    public String userRegisterMove(int row, int column, String selectedDirection, GridPane board) {
        boolean valid = model.makeUserMove(new MoveModel(row, column, new ArrowModel(model.getPlayer().getColor(), selectedDirection)));

        if(!valid || selectedDirection == null){
            return null;
        }

        placeArrow(model.getPlayer().getColor(), selectedDirection, row, column, board);

        if(model.isEndgame()) {
            model.updateUserScore();
            return model.getUser().getUserName();
        }

        MoveModel moveModel = model.getSystemMove();
        if (moveModel != null){
            placeArrow(model.getComputer().getColor(), moveModel.getArrowModel().getDirection(), moveModel.getX(), moveModel.getY(), board);
        }

        if(model.isEndgame()) {
            return "computer";
        }
        return null;
    }

    public void undoMove(GridPane board) {
        MoveModel sysMoveModel = model.undo();
        MoveModel usrMoveModel = model.undo();
        if(sysMoveModel != null) removeArrow(sysMoveModel.getX(), sysMoveModel.getY(), board);
        if(usrMoveModel != null) removeArrow(usrMoveModel.getX(), usrMoveModel.getY(), board);
    }

    public String loadUserList() {
        ArrayList<UserModel> users = model.getUsers();
        StringBuilder stringBuilder = new StringBuilder();

        users.forEach( u -> stringBuilder.append(u).append("\n"));

        return stringBuilder.toString();
    }

    public int loadWonGames() {
        UserModel user = model.getUser();
        return user.getGamesWon();
    }

    public String clickedStartGame(String selectedBoard) {
        String color = "g";
        String board;

        if(!model.getPlayer().getColor().equals(color)) {
            model.getComputer().setColor(model.getPlayer().getColor());
            model.getComputer().setColor(color);
        }

        if(selectedBoard.equals("4x4")) {
            board = "small";
            model.changeBoardSize(4);
            return board;
        } else {
            board = "large";
            model.changeBoardSize(8);
            return board;
        }
    }

    private void placeArrow(String color, String direction, int row, int column, GridPane board) {
        Image image = new Image(new File(ServerApplication.path + color + direction + ".png").toURI().toString());

        board.getChildren().stream()
                .filter(node -> node instanceof ImageView)
                .map(node -> (ImageView) node)
                .filter(imageView -> {
                    Integer rowIdx = GridPane.getRowIndex(imageView);
                    Integer colIdx = GridPane.getColumnIndex(imageView);
                    return rowIdx != null && colIdx != null && rowIdx == row && colIdx == column;
                })
                .findFirst()
                .ifPresent(imageView -> imageView.setImage(image));
    }

    private void removeArrow(int row, int column, GridPane board) {
        board.getChildren().stream()
                .filter(node -> node instanceof ImageView)
                .map(node -> (ImageView) node)
                .filter(imageView -> {
                    Integer rowIdx = GridPane.getRowIndex(imageView);
                    Integer colIdx = GridPane.getColumnIndex(imageView);
                    return rowIdx != null && colIdx != null && rowIdx == row && colIdx == column;
                })
                .findFirst()
                .ifPresent(imageView -> imageView.setImage(new Image(new File(ServerApplication.path + "img.png").toURI().toString())));
    }
}
