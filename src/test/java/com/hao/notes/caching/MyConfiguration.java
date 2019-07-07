package com.hao.notes.caching;

import java.lang.reflect.Method;

import org.assertj.core.util.Arrays;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableCaching
@Slf4j
class MyConfiguration {

    @Bean
    public KeyGenerator myKeyGenerator() {
        return new KeyGenerator() {

            @Override
            public Object generate(Object target, Method method, Object... params) {
                log.info("Generating key from {}, method: {}, params: {}", target, method, params);
                return method.getName() + Arrays.asList(params).toString();
            }

        };
    }

}
