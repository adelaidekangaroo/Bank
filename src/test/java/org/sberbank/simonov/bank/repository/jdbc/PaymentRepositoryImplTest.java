package org.sberbank.simonov.bank.repository.jdbc;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.sberbank.simonov.bank.data.PaymentTestData;
import org.sberbank.simonov.bank.model.Account;
import org.sberbank.simonov.bank.model.Payment;
import org.sberbank.simonov.bank.repository.AccountRepository;
import org.sberbank.simonov.bank.repository.PaymentRepository;

import java.util.List;

import static org.sberbank.simonov.bank.data.AccountTestData.ACCOUNT_2;
import static org.sberbank.simonov.bank.data.AccountTestData.ACCOUNT_3;
import static org.sberbank.simonov.bank.data.PaymentTestData.*;
import static org.sberbank.simonov.bank.data.UserTestData.USER_1;

public class PaymentRepositoryImplTest extends InitRepositoryTest {

    private final PaymentRepository paymentRepository = new PaymentRepositoryImpl();
    private final AccountRepository accountRepository = new AccountRepositoryImpl();

    @Test
    public void create() {
        Payment created = PaymentTestData.created();
        paymentRepository.create(created, USER_1.getId());
        created.setId(NEW_PAYMENT_ID);
        Payment received = paymentRepository.getById(created.getId());
        Assert.assertEquals(received, created);
    }

    @Test
    public void confirm() {
        Payment created = PaymentTestData.updated();
        paymentRepository.confirm(created);
        Payment received = paymentRepository.getById(created.getId());

        Account user = new Account(ACCOUNT_2);
        Account counterparty = new Account(ACCOUNT_3);

        user.setAmount(user.getAmount()
                .subtract(created.getAmount()));
        counterparty.setAmount(counterparty.getAmount()
                .add(created.getAmount()));

        Assert.assertEquals(received, created);
        Assert.assertEquals(accountRepository.getById(ACCOUNT_2.getId(), ACCOUNT_2.getUserId()), user);
        Assert.assertEquals(accountRepository.getById(ACCOUNT_3.getId(), ACCOUNT_3.getUserId()), counterparty);
    }

    @Test
    public void getById() {
        Payment actual = paymentRepository.getById(PAYMENT_1.getId());
        Assert.assertEquals(PAYMENT_1, actual);
    }

    @Test
    public void getAllUnconfirmed() {
        List<Payment> actual = paymentRepository.getAllUnconfirmed();
        MatcherAssert.assertThat(actual, Matchers.contains(PAYMENT_1, PAYMENT_2));
    }
}