package com.hao.notes.monitor.actuator;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.SneakyThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = Configuration.class)
public class IntegratedWithSwaggerExample {

    @Test
    @SneakyThrows
    public void integratedWithSwagger() {
        /*
         * Check API doc: http://localhost:8080/v2/api-docs
         * Check API web GUI: http://localhost:8080/swagger-ui.html
         */
        TimeUnit.DAYS.sleep(1L);
    }



}
