package com.hao.notes.caching.redis;

import java.util.Random;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyCache {

    @Cacheable(cacheNames="defaultKey")
    public Student generate0(String name) {
        log.info("generating student {}", name);
        return Student.builder().name(name).age(new Random().nextInt(100)).build();
    }

    @CachePut(cacheNames="defaultKey", key="#result.name")
    public Student update0(String name, int age) {
        log.info("update student {}'s age: {}", name, age);
        return Student.builder().name(name).age(age).build();
    }

    @CacheEvict(cacheNames="defaultKey", key="#name")
    public void delete0(String name) {
        log.info("evict student {}", name);
        return;
    }


}
