package com.hao.spring.dependency.components;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class DbService {

    @Autowired
    User user;

    public void sayHello() {
        System.err.println("hello");
    }

}
