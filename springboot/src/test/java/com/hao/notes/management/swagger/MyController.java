package com.hao.notes.management.swagger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({ "/v1/my-controller", "/my-controller" })
class MyController {
    @GetMapping(path="/hello")
    public String hello() {
        return "Hello";
    }
}
