package org.sberbank.simonov.bank.repository;

import org.sberbank.simonov.bank.model.Account;

import java.util.List;

public interface AccountRepository {

    String INSERT = "INSERT INTO account(user_id, amount) VALUES (?, ?)";
    String UPDATE = "UPDATE account SET amount = ? WHERE id = ? AND user_id = ?";
    String GET_BY_ID = "SELECT id, user_id, amount FROM account WHERE id = ? AND user_id = ?";
    String GET_ALL_BY_USER = "SELECT id, user_id, amount FROM account WHERE user_id = ?";

    boolean save(Account account, int userId);

    Account getById(int id, int userId);

    List<Account> getAllByUser(int userId);
}