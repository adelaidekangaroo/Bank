package org.sberbank.simonov.bank.web.controller;

import com.sun.net.httpserver.HttpExchange;
import org.sberbank.simonov.bank.Context;
import org.sberbank.simonov.bank.model.User;
import org.sberbank.simonov.bank.service.UserService;
import org.sberbank.simonov.bank.service.impl.UserServiceImpl;
import org.sberbank.simonov.bank.to.UserTo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.sberbank.simonov.bank.web.ResponseWrapper.sendWithBody;

public class UserController {

    public static final String USER_CONTROLLER_PATH = "users";

    private final UserService service = new UserServiceImpl();

    public void getAllCounterparties(HttpExchange exchange) throws IOException {
        List<UserTo> users = service.getAllCounterparties(1);
        sendWithBody(users, exchange, 200);
    }

    public void getById(int id, HttpExchange exchange) throws IOException {
        UserTo user = service.getById(id);
        sendWithBody(user, exchange, 200);
    }

    public void create(HttpExchange exchange) throws IOException {
        User user = Context.getGson()
                .fromJson(new InputStreamReader(exchange.getRequestBody()), User.class);
        service.create(user);
        exchange.sendResponseHeaders(201, -1);
        exchange.close();
    }
}