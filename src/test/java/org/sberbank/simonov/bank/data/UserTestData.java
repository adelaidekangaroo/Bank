package org.sberbank.simonov.bank.data;

import org.sberbank.simonov.bank.model.User;

import static org.sberbank.simonov.bank.model.Role.EMPLOYEE;
import static org.sberbank.simonov.bank.model.Role.USER;
import static org.sberbank.simonov.bank.model.UserType.INDIVIDUAL;
import static org.sberbank.simonov.bank.model.UserType.LEGAL_ENTITY;

public class UserTestData {

    public static final User USER_1 = new User(1, "user1", "pass", "Иванов Иван Иванович", USER, INDIVIDUAL);
    public static final User USER_2 = new User(2, "user2", "pass1", "Семёнов Семён Семёнович", USER, INDIVIDUAL);
    public static final User USER_3 = new User(3, "empl1", "pass2", "Романова Дарья Ивановна", EMPLOYEE, INDIVIDUAL);

    public static final int NEW_USER_ID = USER_3.getId() + 1;

    public static User created() {
        return new User("newuser", "newpass", "Новопов Новоп Новопользовович", USER, INDIVIDUAL);
    }

    public static User updated() {
        return new User(USER_1.getId(), USER_1.getLogin(), USER_1.getPassword(), USER_1.getFullName(), USER_1.getRole(), LEGAL_ENTITY);
    }
}
