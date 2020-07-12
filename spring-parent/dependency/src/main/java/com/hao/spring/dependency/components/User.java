package com.hao.spring.dependency.components;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class User {

    @Autowired
    DbService dbService;
}
