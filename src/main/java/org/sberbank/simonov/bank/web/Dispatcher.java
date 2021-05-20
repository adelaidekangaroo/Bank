package org.sberbank.simonov.bank.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.sberbank.simonov.bank.util.RequestParser;
import org.sberbank.simonov.bank.web.controller.AccountController;
import org.sberbank.simonov.bank.web.controller.CardController;
import org.sberbank.simonov.bank.web.controller.PaymentController;
import org.sberbank.simonov.bank.web.controller.UserController;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class Dispatcher {

    public static final String context = "/bank/rest/";
    public static final String USER_CONTROLLER_PATH = "users";
    public static final String CARD_CONTROLLER_PATH = "cards";
    public static final String ACCOUNT_CONTROLLER_PATH = "accounts";
    public static final String PAYMENT_CONTROLLER_PATH = "payments";
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";

    public final UserController userController = new UserController();
    public final CardController cardController = new CardController();
    private final AccountController accountController = new AccountController();
    private final PaymentController paymentController = new PaymentController();

    public Dispatcher(HttpServer server) {
        server.createContext(context, this::dispatch);
    }

    private void dispatch(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String relativePath = RequestParser.getRelativePath(context, exchange);
        Map<String, String> queries = RequestParser.queryToMap(exchange.getRequestURI().getRawQuery());

        AbstractMap.SimpleEntry<String, List<Integer>> pathTokens = RequestParser.getPathTokens(relativePath);
        String controllerMapper = pathTokens.getKey();
        List<Integer> ids = pathTokens.getValue();

        manageRequest(controllerMapper, ids, method, queries, exchange);
    }

    public void manageRequest(String controllerName, List<Integer> ids, String method, Map<String, String> queries, HttpExchange exchange) throws IOException {
        switch (controllerName) {
            case USER_CONTROLLER_PATH:
                switch (method) {
                    case GET:
                        switch (ids.size()) {
                            case 0:
                                userController.getAllCounterparties(exchange);
                                break;
                            case 1:
                                int userId = ids.get(0);
                                userController.getById(userId, exchange);
                        }
                        break;
                    case POST:
                        if (ids.size() == 0) userController.create(exchange);
                        break;
                }
                break;
            case CARD_CONTROLLER_PATH:
                switch (method) {
                    case GET:
                        switch (ids.size()) {
                            case 0:
                                if (queries.containsKey("confirmed")) {
                                    boolean confirmed = Boolean.parseBoolean(queries.get("confirmed"));
                                    cardController.getAllUnconfirmed(confirmed, exchange);
                                }
                                break;
                            case 1:
                                int userId = ids.get(0);
                                cardController.getAllByUser(userId, exchange);
                                break;
                            case 2:
                                int id = ids.get(1);
                                cardController.getById(id, exchange);
                        }
                        break;
                    case POST:
                        if (ids.size() == 1) cardController.create(exchange);
                        break;
                    case PUT:
                        if (ids.size() == 2) cardController.update(exchange, ids.get(1));
                        break;
                }
                break;
            case ACCOUNT_CONTROLLER_PATH:
                switch (method) {
                    case GET:
                        switch (ids.size()) {
                            case 1:
                                int userId = ids.get(0);
                                accountController.getAllByUser(userId, exchange);
                                break;
                            case 2:
                                int id = ids.get(1);
                                accountController.getById(id, ids.get(0), exchange);
                        }
                        break;
                    case POST:
                        if (ids.size() == 1) accountController.create(ids.get(0), exchange);
                        break;
                    case PUT:
                        if (ids.size() == 2) accountController.update(ids.get(1), ids.get(0), exchange);
                        break;
                }
                break;
            case PAYMENT_CONTROLLER_PATH:
                switch (method) {
                    case GET:
                        switch (ids.size()) {
                            case 0:
                                if (queries.containsKey("confirmed")) {
                                    boolean confirmed = Boolean.parseBoolean(queries.get("confirmed"));
                                    paymentController.getAllUnconfirmed(confirmed, exchange);
                                }
                                break;
                            case 2:
                                int id = ids.get(1);
                                paymentController.getById(id, exchange);
                        }
                        break;
                    case POST:
                        if (ids.size() == 1) paymentController.create(ids.get(0), exchange);
                        break;
                    case PUT:
                        if (ids.size() == 2) paymentController.confirm(ids.get(1), exchange);
                        break;
                }
                break;
        }
    }
}