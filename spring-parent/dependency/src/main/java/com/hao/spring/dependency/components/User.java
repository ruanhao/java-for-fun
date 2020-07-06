package com.hao.spring.dependency.components;

import org.springframework.beans.factory.annotation.Autowired;

public class User {

    @Autowired
    DbService dbService;
}
