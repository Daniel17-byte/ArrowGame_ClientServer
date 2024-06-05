package org.arrowgame.server.controller;

import org.arrowgame.server.forms.*;
import org.arrowgame.server.model.*;
import org.arrowgame.server.utils.MinMaxStrategy;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GameController {
    private final GameModel model;

    public GameController() {
        this.model = new GameModel();
        this.model.getComputer().setStrategy(new MinMaxStrategy(4, 10));
    }

    @PostMapping("/registerMove")
    public List<ResultMoveResponse> userRegisterMove(@RequestBody MoveForm moveForm) {
        List<ResultMoveResponse> list = new ArrayList<>();
        ResultMoveResponse resultMoveResponseUser;
        ResultMoveResponse resultMoveResponseComputer = null;
        boolean valid = model.makeUserMove(new MoveModel(moveForm.getRow(), moveForm.getColumn(), new ArrowModel(model.getPlayer().getColor(), moveForm.getSelectedDirection())));

        if(!valid || moveForm.getSelectedDirection() == null){
            return null;
        }

        resultMoveResponseUser = new ResultMoveResponse(model.getPlayer().getColor(), moveForm.getSelectedDirection(), moveForm.getRow(), moveForm.getColumn());
        list.add(resultMoveResponseUser);

        if(model.isEndgame()) {
            model.updateUserScore(moveForm.getDifficulty());
            resultMoveResponseUser.setUser(true);
            resultMoveResponseUser.setWinner(true);
            return list;
        }

        MoveModel moveModel = model.getSystemMove();

        if (moveModel != null){
            resultMoveResponseComputer = new ResultMoveResponse(model.getComputer().getColor(), moveModel.getArrowModel().getDirection(), moveModel.getX(), moveModel.getY());
            list.add(resultMoveResponseComputer);
        }

        if(resultMoveResponseComputer != null && model.isEndgame()) {
            resultMoveResponseComputer.setWinner(true);
        }


        return list;
    }

    @GetMapping("/undo")
    public ArrayList<MoveModelResponse> undoMove() {
        MoveModel sysMoveModel = model.undo();
        MoveModel userMoveModel = model.undo();
        ArrayList<MoveModelResponse> undoList = new ArrayList<>();
        if(sysMoveModel != null) undoList.add(new MoveModelResponse(sysMoveModel.getX(), sysMoveModel.getY()));
        if(userMoveModel != null) undoList.add(new MoveModelResponse(userMoveModel.getX(), userMoveModel.getY()));

        return undoList;
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

    @GetMapping("/getGames")
    public List<GameDbModel> getGames() {
        return model.getGames();
    }

    @PutMapping("/startGame")
    public String clickedStartGame(@RequestParam String selectedBoard) {
        model.clearBoard();

        String color = "g";
        String board;

        if(!model.getPlayer().getColor().equals(color)) {
            model.getComputer().setColor(model.getPlayer().getColor());
            model.getComputer().setColor(color);
        }

        if(selectedBoard.equals("4x4")) {
            board = "small";
            model.changeBoardSize(4);
        } else {
            board = "large";
            model.changeBoardSize(8);
        }

        return board;
    }
}
