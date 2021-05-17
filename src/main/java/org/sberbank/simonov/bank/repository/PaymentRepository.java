package org.sberbank.simonov.bank.repository;

import org.sberbank.simonov.bank.model.Payment;

import java.util.List;

public interface PaymentRepository {

    String INSERT = "INSERT INTO payment(amount, account_owner_id, counterparty_id, is_confirmed) VALUES (?, ?, ?, ?)";
    String UPDATE = "UPDATE payment SET amount = ?, account_owner_id = ?, counterparty_id = ?, is_confirmed = ? WHERE id = ?";
    String GET_BY_ID = "SELECT id, amount, account_owner_id, counterparty_id, is_confirmed FROM payment WHERE id = ?";
    String GET_ALL_UNCONFIRMED = "SELECT id, amount, account_owner_id, counterparty_id, is_confirmed FROM payment WHERE is_confirmed = false";

    boolean save(Payment payment);

    Payment getById(int id);

    List<Payment> getAllUnconfirmed();
}