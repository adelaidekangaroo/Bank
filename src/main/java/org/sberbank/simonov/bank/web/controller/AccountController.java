package org.sberbank.simonov.bank.web.controller;

import com.sun.net.httpserver.HttpExchange;
import org.sberbank.simonov.bank.model.Account;
import org.sberbank.simonov.bank.model.Role;
import org.sberbank.simonov.bank.service.AccountService;
import org.sberbank.simonov.bank.service.impl.AccountServiceImpl;

import java.util.List;
import java.util.Map;

import static org.sberbank.simonov.bank.util.RequestParser.parseJsonBodyFromExchange;
import static org.sberbank.simonov.bank.web.Dispatcher.*;
import static org.sberbank.simonov.bank.util.ResponseWrapper.*;

public class AccountController extends AbstractController {

    public static final String REST_URL = "users/{userId}/accounts/{id}";

    private final AccountService service = new AccountServiceImpl();

    public void getAllByUser(int userId, HttpExchange exchange) {
        handleErrors(exchange, () -> {
            List<Account> accounts = service.getAllByUser(userId);
            sendWithBody(accounts, exchange, OK_CODE);
        });
    }

    public void getById(int id, int userId, HttpExchange exchange) {
        handleErrors(exchange, () -> {
            Account account = service.getById(id, userId);
            sendWithBody(account, exchange, OK_CODE);
        });
    }

    public void create(int userId, HttpExchange exchange) {
        handleErrors(exchange, () -> {
            Account account = parseJsonBodyFromExchange(exchange, Account.class);
            service.create(account, userId);
            sendWithOutBody(exchange, CREATED_CODE);
        });
    }

    public void update(int id, int userId, HttpExchange exchange) {
        handleErrors(exchange, () -> {
            Account account = parseJsonBodyFromExchange(exchange, Account.class);
            service.update(account, id, userId);
            sendWithOutBody(exchange, NO_CONTENT_CODE);
        });
    }

    @Override
    protected String getUrl() {
        return REST_URL;
    }

    @Override
    protected void switchMethod(HttpExchange exchange, Role role, String
            method, Map<String, String> queries, List<Integer> ids) {
        switch (role) {
            case USER:
                switch (method) {
                    case GET:
                        switch (ids.size()) {
                            case 1:
                                int userId = ids.get(0);
                                getAllByUser(userId, exchange);
                                break;
                            case 2:
                                int id = ids.get(1);
                                getById(id, ids.get(0), exchange);
                        }
                        break;
                    case PUT:
                        System.out.println(ids.size() == 2);
                        if (ids.size() == 2) update(ids.get(1), ids.get(0), exchange);
                        break;
                }
                break;
            case EMPLOYEE:
                if (POST.equals(method)) {
                    if (ids.size() == 1) create(ids.get(0), exchange);
                }
                break;
        }
    }
}
