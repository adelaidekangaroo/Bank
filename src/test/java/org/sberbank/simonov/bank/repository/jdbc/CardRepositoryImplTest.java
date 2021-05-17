package org.sberbank.simonov.bank.repository.jdbc;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.sberbank.simonov.bank.model.Card;
import org.sberbank.simonov.bank.repository.CardRepository;

import java.util.List;

import static org.sberbank.simonov.bank.data.CardTestData.*;
import static org.sberbank.simonov.bank.data.UserTestData.USER_1;

public class CardRepositoryImplTest extends RepositoryTest {

    private final CardRepository repository = new CardRepositoryImpl();

    @Test
    public void create() {
        Card created = created();
        repository.save(created);
        created.setId(NEW_CARD_ID);
        Card received = repository.getById(created.getId());
        Assert.assertEquals(received, created);
    }

    @Test
    public void update() {
        Card created = updated();
        repository.save(created);
        Card received = repository.getById(created.getId());
        Assert.assertEquals(received, created);
    }

    @Test
    public void getById() {
        Card actual = repository.getById(CARD_1.getId());
        Assert.assertEquals(CARD_1, actual);
    }

    @Test
    public void getAllUnconfirmed() {
        List<Card> actual = repository.getAllUnconfirmed();
        MatcherAssert.assertThat(actual, Matchers.contains(CARD_1, CARD_2, CARD_3, CARD_4));
    }

    @Test
    public void getAllByUser() {
        List<Card> actual = repository.getAllByUser(USER_1.getId());
        MatcherAssert.assertThat(actual, Matchers.contains(CARD_1, CARD_2));
    }
}