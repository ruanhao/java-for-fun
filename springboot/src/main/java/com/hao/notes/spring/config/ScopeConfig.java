package com.hao.notes.spring.config;

import com.hao.notes.spring.MyAnnotation;
import com.hao.notes.spring.components.Person;
import com.hao.notes.spring.components.Student;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(value = "com.hao.notes.spring")
public class ScopeConfig {

    @Bean
    public Person person() {
        return new Person("HR", 30);
    }


    @Bean
    @Scope("prototype")
    public Student student() {
        return new Student("Student", 30);
    }
}
