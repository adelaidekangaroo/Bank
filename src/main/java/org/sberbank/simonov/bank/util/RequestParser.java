package org.sberbank.simonov.bank.util;

import com.sun.net.httpserver.HttpExchange;

import java.util.*;

public class RequestParser {

    private RequestParser() {
    }

    public static Map<String, String> queryToMap(String query) {
        if (query == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }

    public static String getRelativePath(String context, HttpExchange exchange) {
        return exchange.getRequestURI().getRawPath().substring(context.length());
    }

    public static AbstractMap.SimpleEntry<String, List<Integer>> getPathTokens(String relativePath) {
        String[] tokens = relativePath.split("\\/");
        String controllerMapper = "";
        List<Integer> ids = new ArrayList<>();
        for (String token : tokens) {
            if (token.matches("\\d+")) {
                ids.add(Integer.parseInt(token));
            } else {
                controllerMapper = token;
            }
        }
        return new AbstractMap.SimpleEntry<>(controllerMapper, ids);
    }
}
