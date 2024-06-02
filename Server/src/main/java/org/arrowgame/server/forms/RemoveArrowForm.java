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
public class RemoveArrowForm {
    private int row;
    private int column;
    private GridPane board;
}
