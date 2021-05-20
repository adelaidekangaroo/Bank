package org.sberbank.simonov.bank.web.controller;

import com.sun.net.httpserver.HttpExchange;
import org.sberbank.simonov.bank.Context;
import org.sberbank.simonov.bank.model.Account;
import org.sberbank.simonov.bank.repository.AccountRepository;
import org.sberbank.simonov.bank.repository.jdbc.AccountRepositoryImpl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class AccountController {

    private final AccountRepository repository = new AccountRepositoryImpl();

    public void getAllByUser(int userId, HttpExchange exchange) throws IOException {
        List<Account> accounts = repository.getAllByUser(userId);
        ResponseWrapper.wrapWithBody(accounts, exchange, 200);
    }

    public void getById(int id, int userId, HttpExchange exchange) throws IOException {
        Account account = repository.getById(id, userId);
        ResponseWrapper.wrapWithBody(account, exchange, 200);
    }

    public void create(int userId, HttpExchange exchange) throws IOException {
        Account account = Context.getGson()
                .fromJson(new InputStreamReader(exchange.getRequestBody()), Account.class);
        repository.create(account, userId);
        exchange.sendResponseHeaders(201, -1);
        exchange.close();
    }

    public void update(int id, int userId, HttpExchange exchange) throws IOException {
        Account account = Context.getGson()
                .fromJson(new InputStreamReader(exchange.getRequestBody()), Account.class);
        repository.update(account, userId);
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
    }
}
