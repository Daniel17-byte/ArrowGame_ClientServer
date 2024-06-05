package org.arrowgame.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("userType")
    private UserType userType;

    @JsonProperty("gamesWon")
    private int gamesWon;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    public UserModel(String userName, String usertype, int gamesWon) {
        this.userName = userName;
        if (usertype.equals("ADMIN")){
            this.userType = UserType.ADMIN;
        }else {
            this.userType = UserType.PLAYER;
        }
        this.gamesWon = gamesWon;
    }

    public UserModel(String userName, String usertype, int gamesWon, String email, String phoneNumber) {
        this.userName = userName;
        if (usertype.equals("ADMIN")){
            this.userType = UserType.ADMIN;
        }else {
            this.userType = UserType.PLAYER;
        }
        this.gamesWon = gamesWon;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return userName + " : " + gamesWon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel user = (UserModel) o;
        return gamesWon == user.gamesWon && Objects.equals(id, user.id) && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && userType == user.userType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, userType, gamesWon);
    }

    public String data() {
        return "UserModel{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", gamesWon=" + gamesWon +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
