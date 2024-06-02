package org.arrowgame.server.forms;

import javafx.scene.layout.GridPane;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceArrowForm {
    private String color;
    private String direction;
    private int row;
    private int column;
    private GridPane board;
}
