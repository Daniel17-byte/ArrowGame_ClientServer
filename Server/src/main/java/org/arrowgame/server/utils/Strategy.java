package org.arrowgame.server.utils;

import org.arrowgame.server.model.GameBoardModel;
import org.arrowgame.server.model.MoveModel;

public interface Strategy {
    MoveModel makeMove(GameBoardModel board);
}