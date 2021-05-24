package org.sberbank.simonov.bank.to;

import org.sberbank.simonov.bank.model.User;
import org.sberbank.simonov.bank.model.UserType;

import java.util.Objects;

public class UserTo {

    private final String login;
    private final String fullName;
    private final UserType userType;

    public UserTo(User user) {
        this.login = user.getLogin();
        this.fullName = user.getFullName();
        this.userType = user.getUserType();
    }

    public String getLogin() {
        return login;
    }

    public String getFullName() {
        return fullName;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTo userTo = (UserTo) o;
        return Objects.equals(login, userTo.login) && Objects.equals(fullName, userTo.fullName) && userType == userTo.userType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, fullName, userType);
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "login='" + login + '\'' +
                ", fullName='" + fullName + '\'' +
                ", userType=" + userType +
                '}';
    }
}
