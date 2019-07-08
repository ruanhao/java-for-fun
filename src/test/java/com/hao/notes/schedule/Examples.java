package com.hao.notes.schedule;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.SneakyThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Examples {

    @Test
    @SneakyThrows
    public void testSchedule() {
        TimeUnit.DAYS.sleep(1L);
    }

}

