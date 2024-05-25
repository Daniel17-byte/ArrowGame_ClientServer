package org.arrowgame.server.model;

import lombok.Getter;
import org.arrowgame.server.ServerApplication;
import org.arrowgame.server.utils.DatabaseService;

import java.util.ArrayList;
import java.util.Stack;

public class GameModel {
    @Getter
    private final ComputerModel computer;
    @Getter
    private final PlayerModel player;
    private GameBoardModel board;
    private final Stack<MoveModel> moveModelStack;
    private final DatabaseService databaseService;

    public GameModel() {
        this.databaseService = ServerApplication.context.getBean(DatabaseService.class);
        this.computer = new ComputerModel("r");
        this.player = new PlayerModel("g");
        this.board = new GameBoardModel(8);
        this.moveModelStack = new Stack<>();
    }

    public boolean makeUserMove(MoveModel moveModel) {
        if (this.board.makeMove(moveModel)) {
            moveModelStack.push(moveModel);
            return true;
        }
        return false;
    }

    public MoveModel getSystemMove() {
        MoveModel moveModel = computer.makeMove(new GameBoardModel(this.board));
        if (moveModel != null) {
            moveModelStack.push(moveModel);
            board.makeMove(moveModel);
            return moveModel;
        }
        return null;
    }

    public MoveModel undo() {
        if(moveModelStack.isEmpty()) return null;
        MoveModel moveModel = moveModelStack.pop();
        this.board.undoMove(moveModel);
        return moveModel;
    }

    public boolean isEndgame() {
        return this.board.noValidMoves() == 0;
    }

    public void clearBoard() {
        this.board.clearBoard();
        moveModelStack.clear();
    }

    public void changeBoardSize(int size) {
        if(this.board.getSize() == size)
            return;
        this.board = new GameBoardModel(size);
    }

    public UserModel getUser() {
        return databaseService.getUser();
    }

    public void updateUserScore() {
        databaseService.updateUserScore();
    }

    public ArrayList<UserModel> getUsers() {
        return databaseService.getUsers();
    }

}
