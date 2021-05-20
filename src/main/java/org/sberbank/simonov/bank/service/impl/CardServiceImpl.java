package org.sberbank.simonov.bank.service.impl;

import org.sberbank.simonov.bank.model.Card;
import org.sberbank.simonov.bank.repository.CardRepository;
import org.sberbank.simonov.bank.repository.jdbc.CardRepositoryImpl;
import org.sberbank.simonov.bank.service.CardService;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.sberbank.simonov.bank.util.ValidationUtil.*;

public class CardServiceImpl implements CardService {

    private final CardRepository repository = new CardRepositoryImpl();

    @Override
    public void create(Card card) {
        requireNonNull(card);
        checkSave(repository.save(card), card.getId());
    }

    @Override
    public void update(Card card, int id) {
        requireNonNull(card);
        assureIdConsistent(card, id);
        checkSave(repository.save(card), card.getId());
    }

    @Override
    public Card getById(int id) {
        return checkNotFoundWithId(repository.getById(id), id);
    }

    @Override
    public List<Card> getAllUnconfirmed() {
        return repository.getAllUnconfirmed();
    }

    @Override
    public List<Card> getAllByUser(int userId) {
        return repository.getAllByUser(userId);
    }
}