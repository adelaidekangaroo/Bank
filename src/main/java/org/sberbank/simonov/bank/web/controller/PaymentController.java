package org.sberbank.simonov.bank.web.controller;

import com.sun.net.httpserver.HttpExchange;
import org.sberbank.simonov.bank.Context;
import org.sberbank.simonov.bank.model.Payment;
import org.sberbank.simonov.bank.repository.PaymentRepository;
import org.sberbank.simonov.bank.repository.jdbc.PaymentRepositoryImpl;

import java.io.IOException;
import java.io.InputStreamReader;

public class PaymentController {

    private final PaymentRepository repository = new PaymentRepositoryImpl();

    public void getById(int id, HttpExchange exchange) throws IOException {
        Payment payment = repository.getById(id);
        ResponseWrapper.wrapWithBody(payment, exchange, 200);
    }

    public void create(int userId, HttpExchange exchange) throws IOException {
        Payment payment = Context.getGson()
                .fromJson(new InputStreamReader(exchange.getRequestBody()), Payment.class);
        repository.create(payment, userId);
        exchange.sendResponseHeaders(201, -1);
        exchange.close();
    }

    public void confirm(int id, HttpExchange exchange) throws IOException {
        Payment payment = Context.getGson()
                .fromJson(new InputStreamReader(exchange.getRequestBody()), Payment.class);
        repository.confirm(payment);
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
    }
}