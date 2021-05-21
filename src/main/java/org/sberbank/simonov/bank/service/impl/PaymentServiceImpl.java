package org.sberbank.simonov.bank.service.impl;

import org.sberbank.simonov.bank.model.Payment;
import org.sberbank.simonov.bank.repository.PaymentRepository;
import org.sberbank.simonov.bank.repository.jdbc.PaymentRepositoryImpl;
import org.sberbank.simonov.bank.service.PaymentService;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.sberbank.simonov.bank.util.ValidationUtil.*;

public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository = new PaymentRepositoryImpl();

    @Override
    public void create(Payment payment, int userId) {
        requireNonNull(payment);
        checkNew(payment);
        checkSave(repository.create(payment, userId), Payment.class);
    }

    @Override
    public void confirm(Payment payment, int id) {
        requireNonNull(payment);
        assureIdConsistent(payment, id);
        checkUpdate(repository.confirm(payment), payment.getId());
    }

    @Override
    public Payment getById(int id) {
        return checkNotFoundWithId(repository.getById(id), id);
    }

    @Override
    public List<Payment> getAllUnconfirmed() {
        return repository.getAllUnconfirmed();
    }
}
