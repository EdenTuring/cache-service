package com.assignment.cache.utils;

import java.util.*;

/**
 * Thread safe implementation of {@link Map}
 *
 * @param <K> the key of the item type
 * @param <V> the item type
 * @author edengesser
 */
public class Cache<K, V> implements Map<K, V> {

    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private LinkedList<Node<K, V>>[] buckets;
    private int size;

    public Cache() {
        buckets = new LinkedList[INITIAL_CAPACITY];
    }

    @Override
    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);

        initBucketIfNull(buckets, bucketIndex);


        synchronized (buckets[bucketIndex]) {

            for (Node<K, V> node : buckets[bucketIndex]) {
                if (node.getKey().equals(key)) {
                    node.value = value;
                    return;
                }
            }

            final var node = new Node<>(key, value);
            buckets[bucketIndex].add(node);
            size++;

            if (size > LOAD_FACTOR * buckets.length) {
                resize();
            }
        }
    }

    @Override
    public V get(K key) {
        int bucketIndex = getBucketIndex(key);

        if (buckets[bucketIndex] == null) {
            return null;
        }

        synchronized (buckets[bucketIndex]) {

            return buckets[bucketIndex]
                    .stream()
                    .filter(kvNode -> kvNode.getKey().equals(key))
                    .findFirst()
                    .map(Node::getValue)
                    .orElse(null);
        }


    }

    private int getBucketIndex(K key) {
        int hash = hash(key);
        final var bucketIndex = hash % buckets.length;
        return bucketIndex < 0 ? bucketIndex + buckets.length : bucketIndex;
    }

    private synchronized void resize() {
        LinkedList<Node<K, V>>[] newBuckets = new LinkedList[buckets.length * 2];

        Arrays.stream(buckets)
                .filter(Objects::nonNull)
                .forEach(bucket -> createNewBuckets(newBuckets, bucket));

        buckets = newBuckets;
    }

    private void createNewBuckets(LinkedList<Node<K, V>>[] newBuckets, LinkedList<Node<K, V>> bucket) {

        bucket.forEach(kvNode -> {
            final var hash = hash(kvNode.getKey());
            final var bucketIndex = hash % newBuckets.length;

            initBucketIfNull(newBuckets, bucketIndex);

            newBuckets[bucketIndex].add(kvNode);
        });

    }

    private void initBucketIfNull(LinkedList<Node<K, V>>[] newBuckets, int bucketIndex) {
        if (newBuckets[bucketIndex] == null) {
            newBuckets[bucketIndex] = new LinkedList<>();
        }
    }

    private static class Node<K, V> {
        K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
