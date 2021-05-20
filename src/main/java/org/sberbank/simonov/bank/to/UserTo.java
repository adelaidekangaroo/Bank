package org.sberbank.simonov.bank.to;

import org.sberbank.simonov.bank.model.User;
import org.sberbank.simonov.bank.model.UserType;

public class UserTo {

    private final String login;
    private final String fullName;
    private final UserType userType;

    public UserTo(User user) {
        this.login = user.getLogin();
        this.fullName = user.getFullName();
        this.userType = user.getUserType();
    }
}
