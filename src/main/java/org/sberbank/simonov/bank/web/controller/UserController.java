package org.sberbank.simonov.bank.web.controller;

import com.sun.net.httpserver.HttpExchange;
import org.sberbank.simonov.bank.Context;
import org.sberbank.simonov.bank.model.User;
import org.sberbank.simonov.bank.repository.UserRepository;
import org.sberbank.simonov.bank.repository.jdbc.UserRepositoryImpl;
import org.sberbank.simonov.bank.web.SecurityUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.sberbank.simonov.bank.web.controller.ResponseWrapper.wrapWithBody;

public class UserController {

    public static final String CARD_CONTROLLER_PATH = "cards";

    private final UserRepository repository = new UserRepositoryImpl();

    public void getAllCounterparties(HttpExchange exchange) throws IOException {
        List<User> users = repository.getAllCounterparties(SecurityUtil.userId);
        wrapWithBody(users, exchange, 200);
    }

    public void getById(int id, HttpExchange exchange) throws IOException {
        User user = repository.getById(id);
        wrapWithBody(user, exchange, 200);
    }

    public void create(HttpExchange exchange) throws IOException {
        User user = Context.getGson()
                .fromJson(new InputStreamReader(exchange.getRequestBody()), User.class);
        repository.create(user);
        exchange.sendResponseHeaders(201, -1);
        exchange.close();
    }
}