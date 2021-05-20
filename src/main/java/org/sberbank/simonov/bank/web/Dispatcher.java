package org.sberbank.simonov.bank.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.sberbank.simonov.bank.model.Role;
import org.sberbank.simonov.bank.service.auth.AuthUserService;
import org.sberbank.simonov.bank.service.impl.UserServiceImpl;
import org.sberbank.simonov.bank.util.RequestParser;
import org.sberbank.simonov.bank.web.controller.AccountController;
import org.sberbank.simonov.bank.web.controller.CardController;
import org.sberbank.simonov.bank.web.controller.PaymentController;
import org.sberbank.simonov.bank.web.controller.UserController;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static org.sberbank.simonov.bank.model.Role.EMPLOYEE;
import static org.sberbank.simonov.bank.model.Role.USER;
import static org.sberbank.simonov.bank.web.controller.AccountController.ACCOUNT_CONTROLLER_PATH;
import static org.sberbank.simonov.bank.web.controller.CardController.CARD_CONTROLLER_PATH;
import static org.sberbank.simonov.bank.web.controller.PaymentController.PAYMENT_CONTROLLER_PATH;
import static org.sberbank.simonov.bank.web.controller.UserController.USER_CONTROLLER_PATH;

public class Dispatcher {

    private static final String userContext = "/bank/rest/profile";
    private static final String employeeContext = "/bank/rest/admin";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";

    private final UserController userController = new UserController();
    private final CardController cardController = new CardController();
    private final AccountController accountController = new AccountController();
    private final PaymentController paymentController = new PaymentController();

    public Dispatcher(HttpServer server) {
        AuthUserService authService = new UserServiceImpl();
        server.createContext(userContext, exchange -> dispatch(exchange, USER))
                .setAuthenticator(authService.getAuthByRole(USER));
        server.createContext(employeeContext, exchange -> dispatch(exchange, EMPLOYEE))
                .setAuthenticator(authService.getAuthByRole(EMPLOYEE));
    }

    private void dispatch(HttpExchange exchange, Role role) throws IOException {
        String method = exchange.getRequestMethod();
        String relativePath = RequestParser.getRelativePath(userContext, exchange);
        Map<String, String> queries = RequestParser.queryToMap(exchange.getRequestURI().getRawQuery());

        AbstractMap.SimpleEntry<String, List<Integer>> pathTokens = RequestParser.getPathTokens(relativePath);
        String controllerMapper = pathTokens.getKey();
        List<Integer> ids = pathTokens.getValue();

        if (role == USER)
            manageUserRequest(controllerMapper, ids, method, queries, exchange);
        else if (role == EMPLOYEE)
            manageAdminRequest(controllerMapper, ids, method, queries, exchange);
    }

    private void manageAdminRequest(String controllerName, List<Integer> ids, String method, Map<String, String> queries, HttpExchange exchange) throws IOException {
        switch (controllerName) {
            case USER_CONTROLLER_PATH:
                if (POST.equals(method)) {
                    if (ids.size() == 0) userController.create(exchange);
                }
                break;
            case CARD_CONTROLLER_PATH:
                if (PUT.equals(method)) {
                    if (ids.size() == 2) cardController.update(exchange, ids.get(1));
                }
                break;
            case ACCOUNT_CONTROLLER_PATH:
                if (POST.equals(method)) {
                    if (ids.size() == 1) accountController.create(ids.get(0), exchange);
                }
                break;
            case PAYMENT_CONTROLLER_PATH:
                if (PUT.equals(method)) {
                    if (ids.size() == 2) paymentController.confirm(ids.get(1), exchange);
                }
                break;
            default:
                sendNotFound(exchange);
                break;
        }
    }

    private void manageUserRequest(String controllerName, List<Integer> ids, String method, Map<String, String> queries, HttpExchange exchange) throws IOException {
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
                }
                break;
            case CARD_CONTROLLER_PATH:
                switch (method) {
                    case GET:
                        switch (ids.size()) {
                            case 0:
                                if (queries.containsKey("notconfirmed")) {
                                    boolean confirmed = Boolean.parseBoolean(queries.get("notconfirmed"));
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
                                if (queries.containsKey("notconfirmed")) {
                                    boolean confirmed = Boolean.parseBoolean(queries.get("notconfirmed"));
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
                }
                break;
            default:
                sendNotFound(exchange);
                break;
        }
    }

    private void sendNotFound(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(404, -1);
        exchange.close();
    }
}