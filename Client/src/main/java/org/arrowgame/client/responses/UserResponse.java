package org.arrowgame.client.responses;

import lombok.Getter;

@Getter
public class UserResponse {
        private String id;
        private String userName;
        private String password;
        private UserType userType;
        private int gamesWon;
}
