package org.sberbank.simonov.bank.web.controller;

import com.sun.net.httpserver.HttpExchange;
import org.sberbank.simonov.bank.model.Role;
import org.sberbank.simonov.bank.model.User;
import org.sberbank.simonov.bank.service.UserService;
import org.sberbank.simonov.bank.service.impl.UserServiceImpl;
import org.sberbank.simonov.bank.to.UserTo;

import java.util.List;
import java.util.Map;

import static org.sberbank.simonov.bank.util.RequestParser.parseJsonBodyFromExchange;
import static org.sberbank.simonov.bank.util.ResponseWrapper.*;
import static org.sberbank.simonov.bank.web.RequestMethod.GET;
import static org.sberbank.simonov.bank.web.RequestMethod.POST;
import static org.sberbank.simonov.bank.web.ResponseCode.*;

public class UserController extends AbstractController {

    public static final String REST_URL = "users/{id}";

    private final UserService service = new UserServiceImpl();

    public void getAllCounterparties(int userId, HttpExchange exchange) {
        handleErrors(exchange, () -> {
            List<UserTo> users = service.getAllCounterparties(userId);
            sendWithBody(users, exchange, OK_CODE);
        });
    }

    public void getById(int id, HttpExchange exchange) {
        handleErrors(exchange, () -> {
            UserTo user = service.getById(id);
            sendWithBody(user, exchange, OK_CODE);
        });
    }

    public void create(HttpExchange exchange) {
        handleErrors(exchange, () -> {
            User user = parseJsonBodyFromExchange(exchange, User.class);
            service.create(user);
            sendWithOutBody(exchange, CREATED_CODE);
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
                if (GET.equals(method)) {
                    if (ids.size() == 1) {
                        int userId = ids.get(0);
                        if (queries.containsKey("counterparties")) {
                            if (Boolean.parseBoolean(queries.get("counterparties"))) {
                                getAllCounterparties(userId, exchange);
                            }
                        } else {
                            getById(userId, exchange);
                        }
                    }
                }
                break;
            case EMPLOYEE:
                if (POST.equals(method)) {
                    if (ids.size() == 0) create(exchange);
                }
                break;
            default:
                sendWithOutBody(exchange, BAD_REQUEST_CODE);
        }
    }
}