package com.assignment.cache.configuration;

import com.assignment.cache.model.Bond;
import com.assignment.cache.utils.LazyCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

    @Value("${cache.expiry.millis}")
    private long cacheExpiryMillis;


    @Bean
    LazyCache<String, Bond> bondLazyCache() {
        return new LazyCache<>(cacheExpiryMillis);
    }
}
