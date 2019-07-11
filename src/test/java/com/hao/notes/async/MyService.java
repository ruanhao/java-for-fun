package com.hao.notes.async;

import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;

@Service
class MyService {

    @Async
    @SneakyThrows
    public void sayHello() {
        System.out.println("Hello");
        TimeUnit.SECONDS.sleep(3L);
        throw new RuntimeException("test"); 
    }
    
}
