package org.sberbank.simonov.bank.web;

import com.sun.net.httpserver.HttpExchange;
import org.sberbank.simonov.bank.Context;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseWrapper {

    public static <T> void sendWithBody(T object, HttpExchange exchange, int successCode) throws IOException {
        String json = Context.getGson().toJson(object);
        exchange.sendResponseHeaders(successCode, json.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(json.getBytes());
        output.flush();
        exchange.close();
    }
}
