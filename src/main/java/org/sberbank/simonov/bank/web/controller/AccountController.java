package org.sberbank.simonov.bank.web.controller;

import com.sun.net.httpserver.HttpExchange;
import org.sberbank.simonov.bank.Context;
import org.sberbank.simonov.bank.model.Account;
import org.sberbank.simonov.bank.service.AccountService;
import org.sberbank.simonov.bank.service.impl.AccountServiceImpl;
import org.sberbank.simonov.bank.web.ResponseWrapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class AccountController {

    public static final String ACCOUNT_CONTROLLER_PATH = "accounts";

    private final AccountService service = new AccountServiceImpl();

    public void getAllByUser(int userId, HttpExchange exchange) throws IOException {
        List<Account> accounts = service.getAllByUser(userId);
        ResponseWrapper.sendWithBody(accounts, exchange, 200);
    }

    public void getById(int id, int userId, HttpExchange exchange) throws IOException {
        Account account = service.getById(id, userId);
        ResponseWrapper.sendWithBody(account, exchange, 200);
    }

    public void create(int userId, HttpExchange exchange) throws IOException {
        Account account = Context.getGson()
                .fromJson(new InputStreamReader(exchange.getRequestBody()), Account.class);
        service.create(account, userId);
        exchange.sendResponseHeaders(201, -1);
        exchange.close();
    }

    public void update(int id, int userId, HttpExchange exchange) throws IOException {
        Account account = Context.getGson()
                .fromJson(new InputStreamReader(exchange.getRequestBody()), Account.class);
        service.update(account, id, userId);
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
    }
}
