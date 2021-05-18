package org.sberbank.simonov.bank.repository.jdbc;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.sberbank.simonov.bank.data.PaymentTestData;
import org.sberbank.simonov.bank.model.Payment;
import org.sberbank.simonov.bank.repository.PaymentRepository;

import java.util.List;

import static org.sberbank.simonov.bank.data.PaymentTestData.*;

public class PaymentRepositoryImplTest extends InitRepositoryTest {

    private final PaymentRepository repository = new PaymentRepositoryImpl();

    @Test
    public void create() {
        Payment created = PaymentTestData.created();
        repository.save(created);
        created.setId(NEW_PAYMENT_ID);
        Payment received = repository.getById(created.getId());
        Assert.assertEquals(received, created);
    }

    @Test
    public void update() {
        Payment created = PaymentTestData.updated();
        repository.save(created);
        Payment received = repository.getById(created.getId());
        Assert.assertEquals(received, created);
    }

    @Test
    public void getById() {
        Payment actual = repository.getById(PAYMENT_1.getId());
        Assert.assertEquals(PAYMENT_1, actual);
    }

    @Test
    public void getAllUnconfirmed() {
        List<Payment> actual = repository.getAllUnconfirmed();
        MatcherAssert.assertThat(actual, Matchers.contains(PAYMENT_1, PAYMENT_2));
    }
}