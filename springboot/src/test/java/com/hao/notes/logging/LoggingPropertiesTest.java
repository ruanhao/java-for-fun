package com.hao.notes.logging;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest

@TestPropertySource(properties = {

        "logging.level.root=INFO",
        "logging.level.com.hao=WARN",

        "logging.file=/tmp/java-for-fun.log",

        "logging.pattern.console=%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n",
        "logging.pattern.file=%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} => %msg%n",

})
@Slf4j
public class LoggingPropertiesTest {

    @Test
    public void test() {
        log.debug(LocalDateTime.now().toString());
        log.info(LocalDateTime.now().toString());
        log.warn(LocalDateTime.now().toString());
    }
}
