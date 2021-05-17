package org.sberbank.simonov.bank.repository.jdbc;

import org.junit.Assert;
import org.junit.Test;
import org.sberbank.simonov.bank.model.User;
import org.sberbank.simonov.bank.repository.UserRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.sberbank.simonov.bank.data.UserTestData.*;

public class UserRepositoryImplTest extends RepositoryTest {

    private final UserRepository repository = new UserRepositoryImpl();

    @Test
    public void create() {
        User created = created();
        repository.save(created);
        created.setId(NEW_USER_ID);
        User received = repository.getById(NEW_USER_ID);
        Assert.assertEquals(received, created);
    }

    @Test
    public void update() {
        User updated = updated();
        repository.save(updated);
        User received = repository.getById(updated.getId());
        Assert.assertEquals(received, updated);
    }

    @Test
    public void getAllCounterparties() {
        List<User> actual = repository.getAllCounterparties(USER_1.getId());
        assertThat(actual, contains(USER_2, USER_3));
    }

    @Test
    public void getById() {
        User actual = repository.getById(USER_1.getId());
        Assert.assertEquals(USER_1, actual);
    }
}