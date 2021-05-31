package org.sberbank.simonov.bank.web.controller;

import com.sun.net.httpserver.HttpExchange;
import org.sberbank.simonov.bank.model.Card;
import org.sberbank.simonov.bank.model.Role;
import org.sberbank.simonov.bank.service.CardService;
import org.sberbank.simonov.bank.service.impl.CardServiceImpl;

import java.util.List;
import java.util.Map;

import static org.sberbank.simonov.bank.util.RequestParser.parseJsonBodyFromExchange;
import static org.sberbank.simonov.bank.util.ResponseWrapper.*;
import static org.sberbank.simonov.bank.web.RequestMethod.*;
import static org.sberbank.simonov.bank.web.ResponseCode.*;

public class CardController extends AbstractController {

    public static final String REST_URL = "users/{userId}/cards/{id}";

    private final CardService service = new CardServiceImpl();

    public void getAllByUser(int userId, HttpExchange exchange) {
        handleErrors(exchange, () -> {
            List<Card> cards = service.getAllByUser(userId);
            sendWithBody(cards, exchange, OK_CODE);
        });
    }

    public void getAllUnconfirmed(HttpExchange exchange) {
        handleErrors(exchange, () -> {
            List<Card> cards = service.getAllUnconfirmed();
            sendWithBody(cards, exchange, OK_CODE);
        });
    }

    public void getById(int id, HttpExchange exchange) {
        handleErrors(exchange, () -> {
            Card card = service.getById(id);
            sendWithBody(card, exchange, OK_CODE);
        });
    }

    public void create(HttpExchange exchange) {
        handleErrors(exchange, () -> {
            Card card = parseJsonBodyFromExchange(exchange, Card.class);
            service.create(card);
            sendWithOutBody(exchange, CREATED_CODE);
        });
    }

    public void update(HttpExchange exchange, int id) {
        handleErrors(exchange, () -> {
            Card card = parseJsonBodyFromExchange(exchange, Card.class);
            service.update(card, id);
            sendWithOutBody(exchange, NO_CONTENT_CODE);
        });
    }

    @Override
    protected String getUrl() {
        return REST_URL;
    }

    @Override
    protected void switchMethod(HttpExchange exchange, Role role, String method, Map<String, String> queries, List<Integer> ids) {
        switch (role) {
            case USER:
                switch (method) {
                    case GET:
                        switch (ids.size()) {
                            case 0:
                                if (queries.containsKey("notconfirmed")) {
                                    if (Boolean.parseBoolean(queries.get("notconfirmed"))) {
                                        getAllUnconfirmed(exchange);
                                    }
                                }
                                break;
                            case 1:
                                int userId = ids.get(0);
                                getAllByUser(userId, exchange);
                                break;
                            case 2:
                                int id = ids.get(1);
                                getById(id, exchange);
                        }
                        break;
                    case POST:
                        if (ids.size() == 1) create(exchange);
                        break;
                }
                break;
            case EMPLOYEE:
                if (PUT.equals(method)) {
                    if (ids.size() == 2) update(exchange, ids.get(1));
                }
                break;
            default:
                sendWithOutBody(exchange, BAD_REQUEST_CODE);
        }
    }
}
