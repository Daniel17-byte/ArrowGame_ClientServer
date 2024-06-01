package org.arrowgame.client.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
        private String id;
        private String userName;
        private String password;
        private UserType userType;
        private int gamesWon;

}
