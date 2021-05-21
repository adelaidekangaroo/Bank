package org.sberbank.simonov.bank.util;

import com.sun.net.httpserver.HttpExchange;
import org.sberbank.simonov.bank.exception.ImpossibleToCreateEntityException;
import org.sberbank.simonov.bank.exception.ImpossibleToUpdateEntityException;
import org.sberbank.simonov.bank.exception.NotFoundException;
import org.sberbank.simonov.bank.exception.StorageException;

import java.io.IOException;
import java.io.OutputStream;

import static org.sberbank.simonov.bank.util.RequestParser.getGson;
import static org.sberbank.simonov.bank.web.Dispatcher.BAD_REQUEST_CODE;
import static org.sberbank.simonov.bank.web.Dispatcher.INTERNAL_SERVER_ERROR_CODE;

public class ResponseWrapper {

    public static <T> void sendWithBody(T object, HttpExchange exchange, int code) {
        String json = getGson().toJson(object);
        try {
            exchange.sendResponseHeaders(code, json.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(json.getBytes());
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
        } catch (NotFoundException | ImpossibleToCreateEntityException | ImpossibleToUpdateEntityException e) {
            sendWithOutBody(exchange, BAD_REQUEST_CODE);
        }
    }
}