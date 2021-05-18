package org.sberbank.simonov.bank.repository.jdbc;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.sberbank.simonov.bank.model.Account;
import org.sberbank.simonov.bank.repository.AccountRepository;

import java.util.List;

import static org.sberbank.simonov.bank.data.AccountTestData.*;

public class AccountRepositoryImplTest extends InitRepositoryTest {

    private final AccountRepository repository = new AccountRepositoryImpl();

    @Test
    public void create() {
        Account created = created();
        repository.save(created, created.getUserId());
        created.setId(NEW_ACCOUNT_ID);
        Account received = repository.getById(NEW_ACCOUNT_ID, created.getUserId());
        Assert.assertEquals(received, created);
    }

    @Test
    public void update() {
        Account updated = updated();
        repository.save(updated, updated.getUserId());
        Account received = repository.getById(updated.getId(), updated.getUserId());
        Assert.assertEquals(received, updated);
    }

    @Test
    public void getById() {
        Account actual = repository.getById(ACCOUNT_1.getId(), ACCOUNT_1.getUserId());
        Assert.assertEquals(ACCOUNT_1, actual);
    }

    @Test
    public void getAllByUser() {
        List<Account> actual = repository.getAllByUser(ACCOUNT_1.getUserId());
        MatcherAssert.assertThat(actual, Matchers.contains(ACCOUNT_1, ACCOUNT_2));
    }
}