package org.sberbank.simonov.bank.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.sberbank.simonov.bank.model.Role;
import org.sberbank.simonov.bank.service.impl.UserServiceImpl;
import org.sberbank.simonov.bank.service.impl.auth.AuthService;
import org.sberbank.simonov.bank.web.util.RequestParser;
import org.sberbank.simonov.bank.web.util.ResponseWrapper;
import org.sberbank.simonov.bank.config.WebConfig;
import org.sberbank.simonov.bank.web.controller.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.sberbank.simonov.bank.model.Role.EMPLOYEE;
import static org.sberbank.simonov.bank.model.Role.USER;
import static org.sberbank.simonov.bank.web.util.ResponseCode.BAD_REQUEST_CODE;

public class Dispatcher {

    private final List<AbstractController> controllers = Arrays.asList(
            new UserController(),
            new CardController(),
            new AccountController(),
            new PaymentController()
    );

    public Dispatcher(HttpServer server) {
        AuthService authService = new UserServiceImpl();
        server.createContext(WebConfig.get().SERVER_USER_CONTEXT, exchange -> dispatch(exchange, WebConfig.get().SERVER_USER_CONTEXT, USER))
                .setAuthenticator(authService.getAuthByRole(USER));
        server.createContext(WebConfig.get().SERVER_ADMIN_CONTEXT, exchange -> dispatch(exchange, WebConfig.get().SERVER_ADMIN_CONTEXT, EMPLOYEE))
                .setAuthenticator(authService.getAuthByRole(EMPLOYEE));
    }

    private void dispatch(HttpExchange exchange, String context, Role role) {
        String method = exchange.getRequestMethod();
        String relativePath = RequestParser.getRelativePath(context, exchange);
        Map<String, String> queries = RequestParser.queryToMap(exchange.getRequestURI().getRawQuery());

        List<Boolean> match = new ArrayList<>();

        controllers.forEach(it -> match.add(it.match(exchange, role, relativePath, method, queries)));

        if (!match.contains(true)) {
            ResponseWrapper.sendWithOutBody(exchange, BAD_REQUEST_CODE);
        }
    }
}