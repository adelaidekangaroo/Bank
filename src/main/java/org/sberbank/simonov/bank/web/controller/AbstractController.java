package org.sberbank.simonov.bank.web.controller;

import com.sun.net.httpserver.HttpExchange;
import org.sberbank.simonov.bank.model.Role;

import java.util.List;
import java.util.Map;

import static org.sberbank.simonov.bank.web.util.RequestParser.getIds;
import static org.sberbank.simonov.bank.web.util.RequestParser.getUrlPattern;

public abstract class AbstractController {

    public boolean match(HttpExchange exchange,
                         Role role,
                         String relativePath,
                         String method,
                         Map<String, String> queries) {
        if (relativePath.matches(getUrlPattern(getUrl()))) {
            List<Integer> ids = getIds(relativePath);
            switchMethod(exchange, role, method, queries, ids);
            return true;
        } else {
            return false;
        }
    }

    protected abstract String getUrl();

    protected abstract void switchMethod(HttpExchange exchange, Role role, String method, Map<String, String> queries, List<Integer> ids);
}