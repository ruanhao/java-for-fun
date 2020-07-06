package com.hao.spring.dependency.components;

import org.springframework.beans.factory.annotation.Autowired;

public class DbService {

    @Autowired
    User user;

    public void sayHello() {
        System.err.println("hello");
    }

}
