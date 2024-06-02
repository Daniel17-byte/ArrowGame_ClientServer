package org.arrowgame.server.controller;

import jakarta.ws.rs.PUT;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.arrowgame.server.ServerApplication;
import org.arrowgame.server.forms.BoardForm;
import org.arrowgame.server.forms.MoveForm;
import org.arrowgame.server.forms.PlaceArrowForm;
import org.arrowgame.server.forms.RemoveArrowForm;
import org.arrowgame.server.model.*;
import org.arrowgame.server.utils.MinMaxStrategy;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;

@RestController
public class GameController {
    private final GameModel model;

    public GameController() {
        this.model = new GameModel();
        this.model.getComputer().setStrategy(new MinMaxStrategy(4, 10));
    }

    @PostMapping("/registerMove")
    public String userRegisterMove(@RequestBody MoveForm moveForm) {
        boolean valid = model.makeUserMove(new MoveModel(moveForm.getRow(), moveForm.getColumn(), new ArrowModel(model.getPlayer().getColor(), moveForm.getSelectedDirection())));

        if(!valid || moveForm.getSelectedDirection() == null){
            return null;
        }

        placeArrow(new PlaceArrowForm(model.getPlayer().getColor(), moveForm.getSelectedDirection(), moveForm.getRow(), moveForm.getColumn(), moveForm.getBoard()));

        if(model.isEndgame()) {
            model.updateUserScore();
            return model.getUser().getUserName();
        }

        MoveModel moveModel = model.getSystemMove();
        if (moveModel != null){
            placeArrow(new PlaceArrowForm(model.getComputer().getColor(), moveModel.getArrowModel().getDirection(), moveModel.getX(), moveModel.getY(), moveForm.getBoard()));
        }

        if(model.isEndgame()) {
            return "computer";
        }

        return null;
    }

    @PutMapping("/undo")
    public void undoMove(@RequestBody BoardForm boardForm) {
        MoveModel sysMoveModel = model.undo();
        MoveModel userMoveModel = model.undo();

        if(sysMoveModel != null) removeArrow(new RemoveArrowForm(sysMoveModel.getX(), sysMoveModel.getY(), boardForm.getBoard()));
        if(userMoveModel != null) removeArrow(new RemoveArrowForm(userMoveModel.getX(), userMoveModel.getY(), boardForm.getBoard()));
    }

    @GetMapping("/getUserList")
    public String loadUserList() {
        ArrayList<UserModel> users = model.getUsers();
        StringBuilder stringBuilder = new StringBuilder();

        users.forEach( u -> stringBuilder.append(u).append("\n"));

        return stringBuilder.toString();
    }

    @GetMapping("/getGamesWon")
    public int loadWonGames() {
        UserModel user = model.getUser();
        return user.getGamesWon();
    }

    @PutMapping("/startGame")
    public String clickedStartGame(@RequestParam String selectedBoard) {
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

    @PostMapping("/placeArrow")
    private void placeArrow(@RequestBody PlaceArrowForm placeArrowForm) {
        Image image = new Image(new File(ServerApplication.path + placeArrowForm.getColor() + placeArrowForm.getDirection() + ".png").toURI().toString());

        placeArrowForm.getBoard().getChildren().stream()
                .filter(node -> node instanceof ImageView)
                .map(node -> (ImageView) node)
                .filter(imageView -> {
                    Integer rowIdx = GridPane.getRowIndex(imageView);
                    Integer colIdx = GridPane.getColumnIndex(imageView);
                    return rowIdx != null && colIdx != null && rowIdx == placeArrowForm.getRow() && colIdx == placeArrowForm.getColumn();
                })
                .findFirst()
                .ifPresent(imageView -> imageView.setImage(image));
    }

    @PostMapping("/removeArrow")
    private void removeArrow(@RequestBody RemoveArrowForm removeArrowForm) {
        removeArrowForm.getBoard().getChildren().stream()
                .filter(node -> node instanceof ImageView)
                .map(node -> (ImageView) node)
                .filter(imageView -> {
                    Integer rowIdx = GridPane.getRowIndex(imageView);
                    Integer colIdx = GridPane.getColumnIndex(imageView);
                    return rowIdx != null && colIdx != null && rowIdx == removeArrowForm.getRow() && colIdx == removeArrowForm.getColumn();
                })
                .findFirst()
                .ifPresent(imageView -> imageView.setImage(new Image(new File(ServerApplication.path + "img.png").toURI().toString())));
    }
}
