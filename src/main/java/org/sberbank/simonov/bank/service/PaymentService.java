package org.sberbank.simonov.bank.service;

import org.sberbank.simonov.bank.model.Payment;

import java.util.List;

public interface PaymentService {

    void create(Payment payment, int userId);

    void confirm(Payment payment, int id);

    Payment getById(int id);

    List<Payment> getAllUnconfirmed();
}
