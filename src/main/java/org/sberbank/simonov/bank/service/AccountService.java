package org.sberbank.simonov.bank.service;

import org.sberbank.simonov.bank.model.Account;

import java.util.List;

public interface AccountService {

    void create(Account account, int userId);

    void update(Account account, int id, int userId);

    Account getById(int id, int userId);

    List<Account> getAllByUser(int userId);
}
