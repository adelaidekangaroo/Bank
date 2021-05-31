package org.sberbank.simonov.bank.web.util;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStreamReader;
import java.util.*;

public class RequestParser {

    private RequestParser() {
    }

    private static final Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }

    public static <T> T parseJsonBodyFromExchange(HttpExchange exchange, Class<T> clazz) {
        return gson.fromJson(new InputStreamReader(exchange.getRequestBody()), clazz);
    }

    public static Map<String, String> queryToMap(String query) {
        if (query == null) {
            return Collections.emptyMap();
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length == 2) {
                result.put(entry[0].toLowerCase(), entry[1]);
            }
        }
        return result;
    }

    public static String getRelativePath(String context, HttpExchange exchange) {
        return exchange.getRequestURI().getRawPath().substring(context.length());
    }

    public static List<Integer> getIds(String url) {
        String[] tokens = url.split("\\/");
        List<Integer> ids = new ArrayList<>();
        for (String token : tokens) {
            if (token.matches("\\d+")) {
                ids.add(Integer.parseInt(token));
            }
        }
        return ids;
    }

    public static String getUrlPattern(String url) {
        String[] tokens = url.split("/\\{\\w+}");
        String patternId = "\\/?[0-9]*";
        StringBuilder pattern = new StringBuilder();
        for (String token : tokens) {
            pattern.append(token)
                    .append(patternId);
        }
        return pattern.toString();
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