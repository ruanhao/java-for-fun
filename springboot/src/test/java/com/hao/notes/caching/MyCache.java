package com.hao.notes.caching;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyCache {

    @Cacheable(cacheNames="defaultKey")
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

    @CacheEvict(cacheNames="defaultKey", allEntries=true)
    public void delete0AllEntries() {
        return;
    }

    @CacheEvict(cacheNames="defaultKey", key="#seed", beforeInvocation=true)
    public void delete0BeforeInvocation(int seed) {
        throw new RuntimeException();
    }


    @Cacheable(cacheNames="SpEL", key="#root.methodName + '[' + #seed + ']'")
    public int generate1(int seed) {
        int result = seed * (int) System.currentTimeMillis();
        log.info("Return integer {} for seed {}", result, seed);
        return result;
    }

    @Cacheable(cacheNames="keyGenerator", keyGenerator="myKeyGenerator")
    public int generate2(int seed1, int seed2) {
        int result = (seed1 + seed2) + (int) System.currentTimeMillis();
        log.info("Return integer {} for seed [{}, {}]", result, seed1, seed2);
        return result;
    }

    @Cacheable(cacheNames="condition", condition="#a0>1", unless="#seed==2")
    public int generate3(int seed) {
        int result = seed * (int) System.currentTimeMillis();
        log.info("Return integer {} for seed {}", result, seed);
        return result;
    }

    @Caching(
            cacheable = {
                    @Cacheable(value="defaultKey", key="#seed")
            },
            put = {
                    @CachePut(value="defaultKey", key="#seed + 100"),
                    @CachePut(value="defaultKey", key="#seed + 1000")
            }
            )
    public int generate4(int seed) {
        int result = seed * (int) System.currentTimeMillis();
        log.info("Return integer {} for seed {}", result, seed);
        return result;
    }



}
