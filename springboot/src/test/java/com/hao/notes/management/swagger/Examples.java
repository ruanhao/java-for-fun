package com.hao.notes.management.swagger;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.SneakyThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties={
        "server.port=40839"
})
public class Examples {

    @Test
    @SneakyThrows
    public void enableSwagger() {
        /*
         * Check API doc: http://localhost:40839/v2/api-docs
         * Check API web GUI: http://localhost:40839/swagger-ui.html
         */
        TimeUnit.DAYS.sleep(1L);
    }



}
