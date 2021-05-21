package org.sberbank.simonov.bank.model;

import org.sberbank.simonov.bank.model.abstraction.BaseEntity;

import java.util.Objects;

import static org.sberbank.simonov.bank.model.Role.USER;
import static org.sberbank.simonov.bank.model.UserType.INDIVIDUAL;

public class User extends BaseEntity {

    private final String login;
    private final String password;
    private final String fullName;
    private Role role = USER;
    private UserType userType = INDIVIDUAL;

    public User(String login, String password, String fullName) {
        super(null);
        this.login = login;
        this.password = password;
        this.fullName = fullName;
    }

    public User(String login, String password, String fullName, Role role, UserType userType) {
        super(null);
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.userType = userType;
    }

    public User(Integer id, String login, String password, String fullName, Role role, UserType userType) {
        super(id);
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.userType = userType;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public Role getRole() {
        return role;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) &&
                Objects.equals(fullName, user.fullName) &&
                role == user.role &&
                userType == user.userType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login, fullName, role, userType);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role=" + role +
                ", userType=" + userType +
                '}';
    }
}
