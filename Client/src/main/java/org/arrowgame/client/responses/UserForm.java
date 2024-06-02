package org.arrowgame.client.responses;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {
    private String userName;
    private String password;
    private String userType;
    private int gamesWon;

    public UserForm(String currentUsername, String newPassword, String userType) {
        this.userName = currentUsername;
        this.password = newPassword;
        this.userType = userType;
    }
}
