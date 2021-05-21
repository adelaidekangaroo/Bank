package org.sberbank.simonov.bank.service.impl;

import org.sberbank.simonov.bank.model.Account;
import org.sberbank.simonov.bank.repository.AccountRepository;
import org.sberbank.simonov.bank.repository.jdbc.AccountRepositoryImpl;
import org.sberbank.simonov.bank.service.AccountService;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.sberbank.simonov.bank.util.ValidationUtil.*;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository = new AccountRepositoryImpl();

    @Override
    public void create(Account account, int userId) {
        requireNonNull(account);
        checkNew(account);
        checkSave(repository.create(account, userId), Account.class);
    }

    @Override
    public void update(Account account, int id, int userId) {
        requireNonNull(account);
        assureIdConsistent(account, id);
        checkUpdate(repository.update(account, userId), account.getId());
    }

    @Override
    public Account getById(int id, int userId) {
        return checkNotFoundWithId(repository.getById(id, userId), id);
    }

    @Override
    public List<Account> getAllByUser(int userId) {
        return repository.getAllByUser(userId);
    }
}
