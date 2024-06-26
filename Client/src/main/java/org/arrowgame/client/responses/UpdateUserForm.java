package org.arrowgame.client.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserForm {
    private UserForm userModel;
    private String username;
    private String password;
    private String userType;
}
