package org.sberbank.simonov.bank.data;

import org.sberbank.simonov.bank.model.User;
import org.sberbank.simonov.bank.to.UserTo;

import static org.sberbank.simonov.bank.model.Role.EMPLOYEE;
import static org.sberbank.simonov.bank.model.Role.USER;
import static org.sberbank.simonov.bank.model.UserType.INDIVIDUAL;
import static org.sberbank.simonov.bank.model.UserType.LEGAL_ENTITY;
import static org.sberbank.simonov.bank.service.impl.auth.PasswordCoder.encode;

public class UserTestData {

    public static final User USER_1 = new User(1, "user1", "cGFzcw==", "Ivanov Ivan Ivanovich", USER, INDIVIDUAL);
    public static final User USER_2 = new User(2, "user2", "cGFzczE=", "Semenov Semen Semenovich", USER, INDIVIDUAL);
    public static final User USER_3 = new User(3, "empl1", "cGFzczI=", "Romanova Daria Romanovna", EMPLOYEE, INDIVIDUAL);

    public static final UserTo USER_TO_1 = new UserTo(USER_1);
    public static final UserTo USER_TO_2 = new UserTo(USER_2);
    public static final UserTo USER_TO_3 = new UserTo(USER_3);

    public static final int NEW_USER_ID = USER_3.getId() + 1;

    public static final String USER_1_CREDENTIALS = "Basic " + encode("user1:pass");
    public static final String EMPL_1_CREDENTIALS = "Basic " + encode("empl1:pass2");

    public static User created() {
        return new User("newuser", encode("newpass"), "Novopov Novop Novopolzovovich", USER, INDIVIDUAL);
    }

    public static User updated() {
        return new User(USER_1.getId(), USER_1.getLogin(), USER_1.getPassword(), USER_1.getFullName(), USER_1.getRole(), LEGAL_ENTITY);
    }
}
