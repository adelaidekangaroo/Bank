package org.sberbank.simonov.bank.web.controller;

import com.sun.net.httpserver.HttpExchange;
import org.sberbank.simonov.bank.Context;
import org.sberbank.simonov.bank.model.Card;
import org.sberbank.simonov.bank.service.CardService;
import org.sberbank.simonov.bank.service.impl.CardServiceImpl;
import org.sberbank.simonov.bank.web.ResponseWrapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CardController {

    public static final String CARD_CONTROLLER_PATH = "cards";

    private final CardService service = new CardServiceImpl();

    public void getAllByUser(int userId, HttpExchange exchange) throws IOException {
        List<Card> cards = service.getAllByUser(userId);
        ResponseWrapper.sendWithBody(cards, exchange, 200);
    }

    public void getAllUnconfirmed(boolean confirmed, HttpExchange exchange) throws IOException {
        if (confirmed) {
            List<Card> cards = service.getAllUnconfirmed();
            ResponseWrapper.sendWithBody(cards, exchange, 200);
        }
    }

    public void getById(int id, HttpExchange exchange) throws IOException {
        Card card = service.getById(id);
        ResponseWrapper.sendWithBody(card, exchange, 200);
    }

    public void create(HttpExchange exchange) throws IOException {
        Card card = Context.getGson()
                .fromJson(new InputStreamReader(exchange.getRequestBody()), Card.class);
        service.create(card);
        exchange.sendResponseHeaders(201, -1);
        exchange.close();
    }

    public void update(HttpExchange exchange, int id) throws IOException {
        Card card = Context.getGson()
                .fromJson(new InputStreamReader(exchange.getRequestBody()), Card.class);
        service.update(card, id);
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
    }
}
