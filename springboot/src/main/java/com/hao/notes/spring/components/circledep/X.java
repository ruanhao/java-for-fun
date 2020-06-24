package com.hao.notes.spring.components.circledep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class X {

    // @Autowired
//    Y y;


    public X() {
        System.err.println("in X constructor");
    }

    public int div(int a, int b) {
        return a / b;
    }
}
