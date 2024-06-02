package org.arrowgame.server.forms;

import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveForm {
    private int row;
    private int column;
    private String selectedDirection;
    private GridPane board;
}
