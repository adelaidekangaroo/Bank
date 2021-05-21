package org.sberbank.simonov.bank.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.sberbank.simonov.bank.model.Role;
import org.sberbank.simonov.bank.service.impl.auth.AuthUserService;
import org.sberbank.simonov.bank.service.impl.UserServiceImpl;
import org.sberbank.simonov.bank.util.RequestParser;
import org.sberbank.simonov.bank.web.controller.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.sberbank.simonov.bank.model.Role.EMPLOYEE;
import static org.sberbank.simonov.bank.model.Role.USER;

public class Dispatcher {

    private static final String userContext = "/bank/rest/profile/";
    private static final String employeeContext = "/bank/rest/admin/";

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";

    public static final int OK_CODE = 200;
    public static final int CREATED_CODE = 201;
    public static final int NO_CONTENT_CODE = 204;
    public static final int BAD_REQUEST_CODE = 400;
    public static final int INTERNAL_SERVER_ERROR_CODE = 500;

    private final List<AbstractController> controllers = Arrays.asList(
            new UserController(),
            new CardController(),
            new AccountController(),
            new PaymentController()
    );

    public Dispatcher(HttpServer server) {
        AuthUserService authService = new UserServiceImpl();
        server.createContext(userContext, exchange -> dispatch(exchange, userContext, USER));
        //   .setAuthenticator(authService.getAuthByRole(USER));
        server.createContext(employeeContext, exchange -> dispatch(exchange, employeeContext, EMPLOYEE));
        //     .setAuthenticator(authService.getAuthByRole(EMPLOYEE));
    }

    private void dispatch(HttpExchange exchange, String context, Role role) {
        String method = exchange.getRequestMethod();
        String relativePath = RequestParser.getRelativePath(context, exchange);
        Map<String, String> queries = RequestParser.queryToMap(exchange.getRequestURI().getRawQuery());

        controllers.forEach(it -> it.match(exchange, role, relativePath, method, queries));
    }
}