package org.arrowgame.client.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultMoveResponse {
    private String color;
    private String direction;
    private int row;
    private int column;
    private boolean isWinner;
    private boolean isUser;

    public ResultMoveResponse(String color, String direction, int row, int column) {
        this.color = color;
        this.direction = direction;
        this.row = row;
        this.column = column;
    }
}