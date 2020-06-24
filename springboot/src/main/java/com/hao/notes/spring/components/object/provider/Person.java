package com.hao.notes.spring.components.object.provider;

import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Person {

    @PostConstruct
    public void postConstruct() {
        System.err.println("Person postConstruct() ... " + this);
    }
}
