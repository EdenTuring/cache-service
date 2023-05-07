package com.assignment.cache.utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * This is a lazy cache. meaning when we look for an expired item it would clean it from cache.
 * For expiration time see {@link com.assignment.cache.configuration.CacheConfiguration}
 * @author edengesser
 *
 * @param <K> the key of the item type
 * @param <V> the item type
 */
public class LazyCache<K, V> implements Map<K, V> {

    private final java.util.Map<K, LazyCacheValue<V>> map;
    private final long expiryInMillis;


    public LazyCache(long expiryInMillis) {
        this.map = new ConcurrentHashMap<>();
        this.expiryInMillis = expiryInMillis;
    }

    public LazyCache() {
        this.map = new ConcurrentHashMap<>();
        //0 = never
        this.expiryInMillis = 0;
    }

    @Override
    public void put(K key, V value) {
        map.put(key, new LazyCacheValue<>(value));

    }

    @Override
    public V get(K key) {
        final var lazyCacheValue = map.getOrDefault(key, null);

        if (lazyCacheValue == null) {
            return null;
        }

        if (shouldCheckExpiry()
                && (System.currentTimeMillis() > lazyCacheValue.getTimestamp() + expiryInMillis)) {
            map.remove(key);
            return null;
        }

        return lazyCacheValue.getValue();
    }

    private boolean shouldCheckExpiry() {
        return expiryInMillis > 0;
    }

}
