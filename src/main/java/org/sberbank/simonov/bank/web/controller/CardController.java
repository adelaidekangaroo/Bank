package org.sberbank.simonov.bank.web.controller;

import com.sun.net.httpserver.HttpExchange;
import org.sberbank.simonov.bank.Context;
import org.sberbank.simonov.bank.model.Card;
import org.sberbank.simonov.bank.repository.CardRepository;
import org.sberbank.simonov.bank.repository.jdbc.CardRepositoryImpl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CardController {

    private final CardRepository repository = new CardRepositoryImpl();

    public void getAllByUser(int userId, HttpExchange exchange) throws IOException {
        List<Card> cards = repository.getAllByUser(userId);
        ResponseWrapper.wrapWithBody(cards, exchange, 200);
    }

    public void getById(int id, HttpExchange exchange) throws IOException {
        Card card = repository.getById(id);
        ResponseWrapper.wrapWithBody(card, exchange, 200);
    }

    public void create(HttpExchange exchange) throws IOException {
        Card card = Context.getGson()
                .fromJson(new InputStreamReader(exchange.getRequestBody()), Card.class);
        repository.save(card);
        exchange.sendResponseHeaders(201, -1);
        exchange.close();
    }

    public void update(HttpExchange exchange, int id) throws IOException {
        Card card = Context.getGson()
                .fromJson(new InputStreamReader(exchange.getRequestBody()), Card.class);
        repository.save(card);
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
    }
}
