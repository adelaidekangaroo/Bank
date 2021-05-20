package org.sberbank.simonov.bank.service;

import org.sberbank.simonov.bank.model.Card;

import java.util.List;

public interface CardService {

    void create(Card card);

    void update(Card card, int id);

    Card getById(int id);

    List<Card> getAllUnconfirmed();

    List<Card> getAllByUser(int userId);
}
