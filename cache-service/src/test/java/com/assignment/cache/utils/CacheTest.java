package com.assignment.cache.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CacheTest {

    private final Cache<Integer, Integer> cache = new Cache<>();


    @Nested
    class Valid {

        @Test
        void putAndGet() {
            final var integers = IntStream.range(0, 100)
                    .boxed()
                    .peek(i -> cache.put(i, i))
                    .toList();

            integers.forEach(i -> assertEquals(i, cache.get(i)));
        }
    }

    @Nested
    class Invalid {

        @Test
        void getUnknown() {
            Assertions.assertNull(cache.get(0));
        }

    }
}
