package com.hao.notes.async;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.SneakyThrows;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class AsnycExamples {
    
    @Autowired
    MyService myService;
    
    @Test
    @SneakyThrows
    public void testAsnyc() {
        long start = System.currentTimeMillis();
        myService.sayHello();
        long end = System.currentTimeMillis();
        assertThat(end - start).isLessThan(100);
        TimeUnit.SECONDS.sleep(5L);
    }

}
