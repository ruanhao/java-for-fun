package com.hao.spring.cloud.jwt.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/v1/user")
public class UserRoleController {

    @Autowired
    RestTemplate rt;

    @GetMapping("/hello")
    public String hello() {
        rt.getForEntity("http://localhost:9999", String.class);
        return "user hello";
    }
}
