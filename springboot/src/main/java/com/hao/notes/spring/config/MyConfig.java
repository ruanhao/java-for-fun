package com.hao.notes.spring.config;

import com.hao.notes.spring.MyAnnotation;
import com.hao.notes.spring.components.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(value = "com.hao.notes.spring.components", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {MyAnnotation.class}) // 按注解进行过滤
})
public class MyConfig {

    @Bean
    public Person person() {
        return new Person("HR", 30);
    }
}
