package org.sberbank.simonov.bank.repository;

import org.sberbank.simonov.bank.model.Card;

import java.util.List;

public interface CardRepository {

    boolean save(Card card);

    List<Card> getAllUnconfirmed();

    List<Card> getAllByUser(int userId);
}
