package org.sberbank.simonov.bank.service.impl;

import org.sberbank.simonov.bank.exception.NotFoundException;
import org.sberbank.simonov.bank.model.Payment;
import org.sberbank.simonov.bank.repository.PaymentRepository;
import org.sberbank.simonov.bank.repository.jdbc.PaymentRepositoryImpl;
import org.sberbank.simonov.bank.service.PaymentService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.sberbank.simonov.bank.util.ValidationUtil.checkCreate;

public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository = new PaymentRepositoryImpl();

    @Override
    public void create(Payment payment) {
        Objects.requireNonNull(payment);
       // checkCreate(paymentRepository.save(payment), payment);
    }

    @Override
    public void confirmPayment(int id) {
       // return false;
    }

    @Override
    public Payment getById(int id) {
        return Optional.of(paymentRepository.getById(id))
                .orElseThrow(() -> new NotFoundException(String.format("Entity %d not found", id)));
    }

    @Override
    public List<Payment> getAllUnconfirmed() {
        return paymentRepository.getAllUnconfirmed();
    }
}
