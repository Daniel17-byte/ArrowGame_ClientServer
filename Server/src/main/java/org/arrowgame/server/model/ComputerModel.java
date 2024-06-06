package org.arrowgame.server.model;

import lombok.Setter;
import org.arrowgame.server.utils.Strategy;

@Setter
public class ComputerModel extends PlayerModel {
    private Strategy strategy;

    public ComputerModel(String color, Strategy strategy) {
        super(color);
        this.strategy = strategy;
    }

    public MoveModel makeMove(GameBoardModel board) {
        return strategy.makeMove(board);
    }

}
