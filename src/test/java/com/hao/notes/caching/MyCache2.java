package com.hao.notes.caching;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@CacheConfig(cacheNames="defaultKey")
public class MyCache2 {

    @Cacheable(key="#seed")
    public int generate0(int seed) {
        int result = seed * (int) System.currentTimeMillis();
        log.info("Return integer {} for seed {}", result, seed);
        return result;
    }

    @CachePut(cacheNames="defaultKey", key="#seed")
    public int update0(int seed, int value) {
        log.info("Update value of {} for seed {}", value, seed);
        return value;
    }

    @CacheEvict(cacheNames="defaultKey", key="#seed")
    public void delete0(int seed) {
        return;
    }

}
