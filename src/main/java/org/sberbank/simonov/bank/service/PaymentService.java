package org.sberbank.simonov.bank.service;

import org.sberbank.simonov.bank.model.Payment;

import java.util.List;

public interface PaymentService {

    void create(Payment payment, int userId);

    void confirmPayment(Payment payment);

    Payment getById(int id);

    List<Payment> getAllUnconfirmed();
}
