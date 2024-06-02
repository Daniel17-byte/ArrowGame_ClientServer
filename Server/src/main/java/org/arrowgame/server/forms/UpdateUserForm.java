package org.arrowgame.server.forms;

import lombok.Getter;
import lombok.Setter;
import org.arrowgame.server.model.UserModel;
import org.arrowgame.server.model.UserType;

@Getter
@Setter
public class UpdateUserForm {
    private UserModel userModel;
    private String username;
    private String password;
    private UserType userType;
}
