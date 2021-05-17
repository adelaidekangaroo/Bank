package org.sberbank.simonov.bank.repository.jdbc;

public class Queries {

    public static final String INIT_DB = "DROP TABLE IF EXISTS card;\n" +
            "DROP TABLE IF EXISTS payment;\n" +
            "DROP TABLE IF EXISTS account;\n" +
            "DROP TABLE IF EXISTS user;\n" +
            "\n" +
            "CREATE TABLE user\n" +
            "(\n" +
            "    id        INT PRIMARY KEY AUTO_INCREMENT,\n" +
            "    login     VARCHAR(30)                      NOT NULL,\n" +
            "    password  VARCHAR(15)                      NOT NULL,\n" +
            "    full_name VARCHAR(255)                     NOT NULL,\n" +
            "    role      VARCHAR(20) DEFAULT 'USER'       NOT NULL,\n" +
            "    user_type VARCHAR(20) DEFAULT 'INDIVIDUAL' NOT NULL,\n" +
            "    CONSTRAINT unique_user_login UNIQUE (login)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE account\n" +
            "(\n" +
            "    id      INT PRIMARY KEY AUTO_INCREMENT,\n" +
            "    user_id INT NOT NULL,\n" +
            "    amount  DECIMAL(20, 2) DEFAULT 0.00,\n" +
            "    FOREIGN KEY (user_id) REFERENCES user (id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE card\n" +
            "(\n" +
            "    id           INT PRIMARY KEY AUTO_INCREMENT,\n" +
            "    account_id   INT    NOT NULL,\n" +
            "    is_active    BOOLEAN DEFAULT FALSE,\n" +
            "    is_confirmed BOOLEAN DEFAULT FALSE,\n" +
            "    number       BIGINT NOT NULL,\n" +
            "    FOREIGN KEY (account_id) REFERENCES account (id),\n" +
            "    CONSTRAINT unique_card_number UNIQUE (number)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE payment\n" +
            "(\n" +
            "    id               INT PRIMARY KEY AUTO_INCREMENT,\n" +
            "    amount           DECIMAL(20, 2) NOT NULL,\n" +
            "    account_owner_id INT            NOT NULL,\n" +
            "    counterparty_id  INT            NOT NULL,\n" +
            "    is_confirmed     BOOLEAN DEFAULT FALSE,\n" +
            "    FOREIGN KEY (account_owner_id) REFERENCES account (id),\n" +
            "    FOREIGN KEY (counterparty_id) REFERENCES account (id),\n" +
            "    CONSTRAINT columns_cannot_equal CHECK (account_owner_id <> counterparty_id)\n" +
            ");";

    public static final String POPULATE = "INSERT INTO user(login, password, full_name, role)\n" +
            "VALUES ('user1', 'pass', 'Иванов Иван Иванович', 'USER'),\n" +
            "       ('user2', 'pass1', 'Семёнов Семён Семёнович', 'USER'),\n" +
            "       ('empl1', 'pass2', 'Романова Дарья Ивановна', 'EMPLOYEE');\n" +
            "\n" +
            "INSERT INTO account(user_id, amount)\n" +
            "VALUES (1, 0.00),\n" +
            "       (1, 10000.00),\n" +
            "       (2, 50000.00),\n" +
            "       (3, 400000.00);\n" +
            "\n" +
            "INSERT INTO card(account_id, is_active, number)\n" +
            "VALUES (1, false, 1111111111111111),\n" +
            "       (2, true, 1234567890123456),\n" +
            "       (3, true, 3456786543211111),\n" +
            "       (4, true, 3456786543666666);\n" +
            "\n" +
            "INSERT INTO payment(amount, account_owner_id, counterparty_id)\n" +
            "VALUES (100.00, 2, 3),\n" +
            "       (1000.00, 3, 2);";

    private Queries() {
    }
}