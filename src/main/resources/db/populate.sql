INSERT INTO user(login, password, full_name, role)
VALUES ('user1', 'pass', 'Ivanov Ivan Ivanovich', 'USER'),
       ('user2', 'pass1', 'Semenov Semen Semenovich', 'USER'),
       ('empl1', 'pass2', 'Romanova Daria Romanovna', 'EMPLOYEE');

INSERT INTO account(user_id, amount)
VALUES (1, 0.00),
       (1, 10000.00),
       (2, 50000.00),
       (3, 400000.00);

INSERT INTO card(account_id, is_active, number)
VALUES (1, false, 1111111111111111),
       (2, true, 1234567890123456),
       (3, true, 3456786543211111),
       (4, true, 3456786543666666);

INSERT INTO payment(amount, account_owner_id, counterparty_id)
VALUES (100.00, 2, 3),
       (1000.00, 3, 2);