package com.hao.notes.mvc;

import java.io.Reader;
import java.io.Writer;
import java.text.ParseException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.format.Formatter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.SneakyThrows;

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
    
    @GetMapping(path = "/testServletApi")
    @SneakyThrows
    public void testServletApi(HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            // InputStream is,
            // OutputStream os,
            Reader reader,
            Writer writer,
            Locale locale
            ) {
        System.out.println(request);
        System.out.println(response);
        //System.out.println(is);
        //System.out.println(os);
        System.out.println(reader);
        System.out.println(writer);  
        System.out.println(locale);  
        writer.write("hello world");
    }
    
    @ModelAttribute
    public User user(@RequestParam(required = false) String id) {
        System.err.println("Providing model attribute");
        if (id == null) {
            return User.builder().build();
        } else {
            return User.builder().id(id).build();
        }
    }
    
    @GetMapping(path = "/testModelAttribute")
    public User testModelAttribute(@ModelAttribute User user) {
        return user;
    }
    
    @GetMapping(path = "/testConverter")
    public Employee testConverter(@RequestParam Employee employee) {
        System.out.println(employee);
        return employee;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public void handleBindingError(MethodArgumentTypeMismatchException e) {
        e.printStackTrace();
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        binder.addCustomFormatter(new Formatter<String>() {

            @Override
            public String print(String object, Locale locale) {
                return object.toUpperCase();
            }

            @Override
            public String parse(String text, Locale locale) throws ParseException {
                return text.toUpperCase();
            }
        }, "name");
    }
    
    @GetMapping(path = "/testBinder")
    public Employee testBinder(Employee employee) {
        System.out.println(employee);
        return employee;
    }
    
    @GetMapping(path = "/testDataFormatter")
    public Employee testDataFormatter(Employee employee) {
        System.out.println(employee);
        return employee;
    }
    
    @GetMapping(path = "/testDataFormatter2")
    public Employee testDataFormatter2(Employee employee, BindingResult bindingResult) {
        if (bindingResult.getErrorCount() > 0) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.err.println(error.getField() + ": " + error.getDefaultMessage());        
            }
        }       
        return employee;
    }
}
