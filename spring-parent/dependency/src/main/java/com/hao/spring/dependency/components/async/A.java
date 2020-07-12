package com.hao.spring.dependency.components.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Async
public class A {
    @Autowired
    B b;
}
