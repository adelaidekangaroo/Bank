package org.sberbank.simonov.bank.web.controller;

import com.sun.net.httpserver.HttpExchange;
import org.sberbank.simonov.bank.model.Payment;
import org.sberbank.simonov.bank.model.Role;
import org.sberbank.simonov.bank.service.PaymentService;
import org.sberbank.simonov.bank.service.impl.PaymentServiceImpl;
import org.sberbank.simonov.bank.util.ResponseWrapper;

import java.util.List;
import java.util.Map;

import static org.sberbank.simonov.bank.util.RequestParser.parseJsonBodyFromExchange;
import static org.sberbank.simonov.bank.util.ResponseWrapper.handleErrors;
import static org.sberbank.simonov.bank.util.ResponseWrapper.sendWithOutBody;
import static org.sberbank.simonov.bank.web.RequestMethod.*;
import static org.sberbank.simonov.bank.web.ResponseCode.*;

public class PaymentController extends AbstractController {

    public static final String REST_URL = "users/{userId}/payments/{id}";

    private final PaymentService service = new PaymentServiceImpl();

    public void getById(int id, HttpExchange exchange) {
        handleErrors(exchange, () -> {
            Payment payment = service.getById(id);
            ResponseWrapper.sendWithBody(payment, exchange, OK_CODE);
        });
    }

    public void getAllUnconfirmed(HttpExchange exchange) {
        handleErrors(exchange, () -> {
            List<Payment> payments = service.getAllUnconfirmed();
            ResponseWrapper.sendWithBody(payments, exchange, OK_CODE);
        });
    }

    public void create(int userId, HttpExchange exchange) {
        handleErrors(exchange, () -> {
            Payment payment = parseJsonBodyFromExchange(exchange, Payment.class);
            service.create(payment, userId);
            sendWithOutBody(exchange, CREATED_CODE);
        });
    }

    public void confirm(int id, HttpExchange exchange) {
        handleErrors(exchange, () -> {
            Payment payment = parseJsonBodyFromExchange(exchange, Payment.class);
            service.confirm(payment, id);
            sendWithOutBody(exchange, NO_CONTENT_CODE);
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
                switch (method) {
                    case GET:
                        switch (ids.size()) {
                            case 0:
                                if (queries.containsKey("notconfirmed")) {
                                    if (Boolean.parseBoolean(queries.get("notconfirmed"))) {
                                        getAllUnconfirmed(exchange);
                                    }
                                }
                                break;
                            case 2:
                                int id = ids.get(1);
                                getById(id, exchange);
                        }
                        break;
                    case POST:
                        if (ids.size() == 1) create(ids.get(0), exchange);
                        break;
                }
                break;
            case EMPLOYEE:
                if (PUT.equals(method)) {
                    if (ids.size() == 2) confirm(ids.get(1), exchange);
                }
                break;
            default:
                sendWithOutBody(exchange, BAD_REQUEST_CODE);
        }
    }
}