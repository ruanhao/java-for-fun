package com.hao.notes.spring.components.circledep;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class Y {

    @Autowired
    X x;

    public Y() {
        System.err.println("in Y constructor");
    }
}
