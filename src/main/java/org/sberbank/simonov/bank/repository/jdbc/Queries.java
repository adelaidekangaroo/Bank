package org.sberbank.simonov.bank.repository.jdbc;

public class Queries {

    public static final String INIT_DB = "DROP TABLE IF EXISTS card;" +
            "DROP TABLE IF EXISTS payment;" +
            "DROP TABLE IF EXISTS account;" +
            "DROP TABLE IF EXISTS user;" +
            "CREATE TABLE user" +
            "(" +
            "    id        INT PRIMARY KEY AUTO_INCREMENT," +
            "    login     VARCHAR(30)                      NOT NULL," +
            "    password  VARCHAR(15)                      NOT NULL," +
            "    full_name VARCHAR(255)                     NOT NULL," +
            "    role      VARCHAR(20) DEFAULT 'USER'       NOT NULL," +
            "    user_type VARCHAR(20) DEFAULT 'INDIVIDUAL' NOT NULL," +
            "    CONSTRAINT unique_user_login UNIQUE (login)" +
            ");";

    public static final String POPULATE = "INSERT INTO user(login, password, full_name, role)" +
            "VALUES ('user1', 'pass', 'Иванов Иван Иванович', 'USER')," +
            "       ('user2', 'pass1', 'Семёнов Семён Семёнович', 'USER')," +
            "       ('empl1', 'pass2', 'Романова Дарья Ивановна', 'EMPLOYEE');";

    private Queries() {
    }
}