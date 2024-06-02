package org.arrowgame.server.forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveForm {
    private int row;
    private int column;
    private String selectedDirection;
}
