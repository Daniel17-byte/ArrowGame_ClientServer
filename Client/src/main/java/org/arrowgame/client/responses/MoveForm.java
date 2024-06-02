package org.arrowgame.client.responses;

import javafx.scene.layout.GridPane;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoveForm {
    private int row;
    private int column;
    private String selectedDirection;
    private GridPane board;
}
