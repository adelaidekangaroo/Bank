DROP TABLE IF EXISTS card;
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    login     VARCHAR(30)                      NOT NULL,
    password  VARCHAR(15)                      NOT NULL,
    full_name VARCHAR(255)                     NOT NULL,
    role      VARCHAR(20) DEFAULT 'USER'       NOT NULL,
    user_type VARCHAR(20) DEFAULT 'INDIVIDUAL' NOT NULL,
    CONSTRAINT unique_user_login UNIQUE (login)
);

CREATE TABLE account
(
    id      INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    amount  DECIMAL(20, 2) DEFAULT 0.00,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE card
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    account_id   INT    NOT NULL,
    is_active    BOOLEAN DEFAULT FALSE,
    is_confirmed BOOLEAN DEFAULT FALSE,
    number       BIGINT NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account (id),
    CONSTRAINT unique_card_number UNIQUE (number)
);

CREATE TABLE payment
(
    id               INT PRIMARY KEY AUTO_INCREMENT,
    amount           DECIMAL(20, 2) NOT NULL,
    account_owner_id INT            NOT NULL,
    counterparty_id  INT            NOT NULL,
    is_confirmed     BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (account_owner_id) REFERENCES account (id),
    FOREIGN KEY (counterparty_id) REFERENCES account (id),
    CONSTRAINT columns_cannot_equal CHECK (account_owner_id <> counterparty_id)
);