package org.sberbank.simonov.bank.repository;

import org.sberbank.simonov.bank.model.Payment;

import java.util.List;

public interface PaymentRepository {

    boolean save(Payment payment);

    List<Payment> getAllUnconfirmed();
}
