package com.hao.notes.spring.components.object.provider;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class User {

    @PostConstruct
    public void postConstruct() {
        System.err.println("User postConstruct() ... " + this);
    }
}
