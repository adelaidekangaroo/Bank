package org.sberbank.simonov.bank.repository;

import org.sberbank.simonov.bank.model.Account;

import java.util.List;

public interface AccountRepository {

    boolean save(Account account);

    Account getById(int userId, int accountId);

    List<Account> getAllByUser(int userId);
}
