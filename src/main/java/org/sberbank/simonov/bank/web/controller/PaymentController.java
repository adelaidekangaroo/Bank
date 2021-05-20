package org.sberbank.simonov.bank.web.controller;

import com.sun.net.httpserver.HttpExchange;
import org.sberbank.simonov.bank.Context;
import org.sberbank.simonov.bank.model.Payment;
import org.sberbank.simonov.bank.service.PaymentService;
import org.sberbank.simonov.bank.service.impl.PaymentServiceImpl;
import org.sberbank.simonov.bank.web.ResponseWrapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class PaymentController {

    public static final String PAYMENT_CONTROLLER_PATH = "payments";

    private final PaymentService service = new PaymentServiceImpl();

    public void getById(int id, HttpExchange exchange) throws IOException {
        Payment payment = service.getById(id);
        ResponseWrapper.sendWithBody(payment, exchange, 200);
    }

    public void getAllUnconfirmed(boolean confirmed, HttpExchange exchange) throws IOException {
        if (confirmed) {
            List<Payment> payments = service.getAllUnconfirmed();
            ResponseWrapper.sendWithBody(payments, exchange, 200);
        }
    }

    public void create(int userId, HttpExchange exchange) throws IOException {
        Payment payment = Context.getGson()
                .fromJson(new InputStreamReader(exchange.getRequestBody()), Payment.class);
        service.create(payment, userId);
        exchange.sendResponseHeaders(201, -1);
        exchange.close();
    }

    public void confirm(int id, HttpExchange exchange) throws IOException {
        Payment payment = Context.getGson()
                .fromJson(new InputStreamReader(exchange.getRequestBody()), Payment.class);
        service.confirm(payment, id);
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
    }
}