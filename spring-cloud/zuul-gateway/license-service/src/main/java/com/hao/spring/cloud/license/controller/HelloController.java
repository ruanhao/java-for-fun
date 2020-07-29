package com.hao.spring.cloud.license.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping({"/hello", "/v1/hello"})
@Slf4j
public class HelloController {

    @Autowired
    RestTemplate rt;

    @GetMapping
    public void helloV1() {
        log.info("hello v1");
        HttpHeaders headers = new HttpHeaders();
        headers.add("LICENSE-VERSION", "v1");
        HttpEntity<?> entity = new HttpEntity<>(headers);
        rt.postForEntity("http://localhost:9999", entity, String.class);
        log.info("hello v1 END");
    }

}
