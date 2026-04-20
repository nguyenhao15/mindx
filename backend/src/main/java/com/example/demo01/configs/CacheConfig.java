package com.example.demo01.configs;

import com.example.demo01.configs.Constants.CacheConstants;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {


    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        cacheManager.registerCustomCache(CacheConstants.PROVINCE_CACHE,
                buildCache(10080, 100));
        cacheManager.registerCustomCache(CacheConstants.CURRENCY_CACHE,
                buildCache(10080, 500));

        cacheManager.registerCustomCache(CacheConstants.AWS3_CACHE,
                Caffeine.newBuilder()
                        .expireAfterWrite(15, TimeUnit.MINUTES)
                        .maximumSize(1000)
                        .recordStats()
                        .build()
                );

        cacheManager.registerCustomCache(CacheConstants.STAFF_PROFILE,
                buildCache(10080, 100));

        cacheManager.registerCustomCache(CacheConstants.AUDIT_CACHE,
                Caffeine.newBuilder()
                        .expireAfterWrite(20, TimeUnit.MINUTES)
                        .maximumSize(1000)
                        .recordStats()
                        .build()
        );

        cacheManager.setCacheNames(Arrays.asList(
                CacheConstants.SYSTEM_CONFIG_CACHE,
                CacheConstants.USER_DETAIL_CACHE,
                CacheConstants.USER_SECURITY_CACHE,
                CacheConstants.ROOMS_CACHE,
                CacheConstants.CUSTOMER_CACHE,
                CacheConstants.SERVICE_CACHE,
                CacheConstants.BRANCH_CACHE
        ));
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    private Cache<Object, Object> buildCache(int ttlMinutes, long maxSize) {
        return Caffeine.newBuilder()
                .expireAfterWrite(ttlMinutes, TimeUnit.MINUTES)
                .maximumSize(maxSize)
                .recordStats()
                .build();
    }

    Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(500)
                .expireAfterWrite(43200, TimeUnit.MINUTES)
                .recordStats();
    }
}


