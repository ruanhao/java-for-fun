package com.hao.springboot.web.controller;

import com.hao.springboot.web.dto.CityDto;
import com.hao.springboot.web.dto.PersonDto;
import java.text.ParseException;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.format.Formatter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    // curl 'localhost:8080/hello?name=ryan&age=35'
    @RequestMapping("/hello")
    public PersonDto hello(HttpSession session, PersonDto personDto) {
        System.err.println("session: " + session);
        System.err.println("personDto: " + personDto);
        PersonDto personDto1 = new PersonDto();
        return personDto;
    }

    // curl 'localhost:8080/city?name=shanghai&num-of-district=8'
    // 通过自定义 HandlerMethodArgumentResolver 实现烤串=>骆驼的绑定
    @GetMapping("/city")
    public CityDto city(CityDto cityDto) {
        System.err.println("cityDto: " + cityDto);
        return cityDto;
    }

    //  echo '{"name": "ryan", "age": 30}' | curl -X POST -H 'Content-Type: application/json' 'localhost:8080/hello' -d@-
    @PostMapping("/hello")
    public PersonDto hello(@RequestBody PersonDto personDto) {
        System.err.println("receive personDto: " + personDto);
        return personDto;
    }


    /**
     * DataBinder 对于 HttpMessageConverter 的作用不大，即对 @RequestBody 的处理不产生影响，
     * 主要还是针对 fields 。
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("age");
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

}
