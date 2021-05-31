package org.sberbank.simonov.bank.web.util;

import com.sun.net.httpserver.HttpExchange;
import org.sberbank.simonov.bank.exception.ImpossibleToCreateEntityException;
import org.sberbank.simonov.bank.exception.ImpossibleToUpdateEntityException;
import org.sberbank.simonov.bank.exception.NotFoundException;
import org.sberbank.simonov.bank.exception.StorageException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static org.sberbank.simonov.bank.web.util.RequestParser.getGson;
import static org.sberbank.simonov.bank.web.util.ResponseCode.BAD_REQUEST_CODE;
import static org.sberbank.simonov.bank.web.util.ResponseCode.INTERNAL_SERVER_ERROR_CODE;

public class ResponseWrapper {

    public static <T> void sendWithBody(T object, HttpExchange exchange, int code) {
        String json = getGson().toJson(object);
        try {
            exchange.sendResponseHeaders(code, json.getBytes().length);
            exchange.getResponseHeaders().set("Content-Type", "charset=UTF-8");
            OutputStream output = exchange.getResponseBody();
            output.write(json.getBytes(StandardCharsets.UTF_8));
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    public static <T> void sendWithOutBody(HttpExchange exchange, int code) {
        try {
            exchange.sendResponseHeaders(code, -1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    public static void handleErrors(HttpExchange exchange, Runnable runnable) {
        try {
            runnable.run();
        } catch (StorageException e) {
            sendWithOutBody(exchange, INTERNAL_SERVER_ERROR_CODE);
        } catch (NotFoundException |
                ImpossibleToCreateEntityException |
                ImpossibleToUpdateEntityException |
                IllegalArgumentException e) {
            sendWithOutBody(exchange, BAD_REQUEST_CODE);
        }
    }
}