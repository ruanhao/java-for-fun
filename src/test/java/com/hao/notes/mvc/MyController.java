package com.hao.notes.mvc;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MyController {
    
    @GetMapping(path="/testMatchingWithParams", params = {"username", "age!=10"})
    public void testMatchingWithParams() {
    }
    
    @GetMapping(path="/testMatchingWithHeaders", headers = {"Accept-Language=en-US"})
    public void testMatchingWithHeaders() {
    }
    
    @GetMapping(path="/testAntPath/*/test")
    public void testAntPath1() {
        System.out.println("ant path 1");
    }
    
    @GetMapping(path="/testAntPath/**/test")
    public void testAntPath2() {
        System.out.println("ant path 2");
    }
    
    @GetMapping(path="/testAntPath/ab?/test")
    public void testAntPath3() {
        System.out.println("ant path 3");
    }
    
    @DeleteMapping(path = "/testHiddenHttpMethodFilterDelete")
    public void testHiddenHttpMethodFilterDelete() {
        
    }
    
    @PutMapping(path = "/testHiddenHttpMethodFilterPut")
    public void testHiddenHttpMethodFilterPut() {
        
    }
    
    @GetMapping(path = "/testCookieValue")
    public String testCookieValue(@CookieValue("randomStr") String str) {
        return str;
    }

}
