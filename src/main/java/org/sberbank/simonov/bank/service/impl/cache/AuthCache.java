package org.sberbank.simonov.bank.service.impl.cache;

import org.sberbank.simonov.bank.model.User;

import java.util.Map;

public class AuthCache {

    private static final Map<String, User> LRU_CACHE_MAP = new LRUCacheMap<>(
            1000,
            100,
            0.75f,
            true
    );

    private AuthCache() {
    }

    public static void putToMap(String login, User user) {
        synchronized (LRU_CACHE_MAP) {
            LRU_CACHE_MAP.put(login, user);
        }
    }

    public static User get(String login) {
        return LRU_CACHE_MAP.get(login);
    }
}
