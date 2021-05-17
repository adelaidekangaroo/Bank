package org.sberbank.simonov.bank.repository;

import org.sberbank.simonov.bank.model.Card;

import java.util.List;

public interface CardRepository {

    String INSERT = "INSERT INTO card(account_id, is_active, is_confirmed, number) VALUES (?, ?, ?, ?)";
    String UPDATE = "UPDATE card SET account_id = ?, is_active = ?, is_confirmed = ?, number = ? WHERE id = ?";
    String GET_BY_ID = "SELECT id, account_id, is_active, is_confirmed, number FROM card WHERE id = ?";
    String GET_ALL_UNCONFIRMED = "SELECT id, account_id, is_active, is_confirmed, number FROM card WHERE is_confirmed = false";
    String GET_ALL_BY_USER = "SELECT card.id, account_id, is_active, is_confirmed, number FROM card JOIN account a on a.id = card.account_id WHERE user_id = ?";

    boolean save(Card card);

    Card getById(int id);

    List<Card> getAllUnconfirmed();

    List<Card> getAllByUser(int userId);
}