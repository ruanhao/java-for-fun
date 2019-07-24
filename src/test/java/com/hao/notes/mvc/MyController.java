package com.hao.notes.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MyController {
    
    @GetMapping(path="/testMatchingWithParams", params = {"username", "age!=10"})
    public void testMatchingWithParams() {
    }
    
    @GetMapping(path="/testMatchingWithHeaders", headers = {"Accept-Language=en-US"})
    public void testMatchingWithHeaders() {
    }

}
