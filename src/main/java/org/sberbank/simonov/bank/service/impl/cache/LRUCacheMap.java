package org.sberbank.simonov.bank.service.impl.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheMap<K, V> extends LinkedHashMap<K, V> {
    private final int MAX_ENTRIES;

    public LRUCacheMap(int maxEntries, int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
        MAX_ENTRIES = maxEntries;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > MAX_ENTRIES;
    }
}