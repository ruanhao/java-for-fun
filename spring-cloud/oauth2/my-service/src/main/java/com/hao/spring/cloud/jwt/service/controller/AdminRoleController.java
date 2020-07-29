package com.hao.spring.cloud.jwt.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admin")
public class AdminRoleController {

    @GetMapping("/hello")
    public String hello() {
        return "admin hello";
    }
}
