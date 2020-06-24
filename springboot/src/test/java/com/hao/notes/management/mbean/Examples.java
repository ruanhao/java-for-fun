package com.hao.notes.management.mbean;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.SneakyThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = MyConfiguration.class)
@TestPropertySource(properties={
        "spring.jmx.enabled=true",
        "management.endpoints.jmx.exposure.exclude=auditevents"
})
public class Examples {

    @Test
    @SneakyThrows
    public void testMbean() {
        /*
         * Run jconsole to see mbean
         */
        TimeUnit.DAYS.sleep(1L);
    }



}
