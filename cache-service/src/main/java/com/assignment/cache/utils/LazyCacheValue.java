package com.assignment.cache.utils;

/**
 *  Immutable Wrapper For Cache Value
 * @param <V> the value to store
 */
public class LazyCacheValue<V> {

    private final V value;
    private final long timestamp;

    public LazyCacheValue(V value) {
        this.value = value;
        this.timestamp = System.currentTimeMillis();
    }

    public V getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
