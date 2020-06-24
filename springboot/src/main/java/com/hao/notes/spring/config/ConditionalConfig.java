package com.hao.notes.spring.config;

import com.hao.notes.spring.components.Person;
import com.hao.notes.spring.components.Student;
import com.hao.notes.spring.conditions.MacOsCondition;
import com.hao.notes.spring.conditions.WindowsCondition;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(value = "com.hao.notes.spring")
public class ConditionalConfig {


    @Bean("bill")
    @Conditional(WindowsCondition.class)
    public Person person() {
        return new Person("Bill", 62);
    }

    @Bean("jobs")
    @Conditional(MacOsCondition.class)
    public Person jobs() {
        return new Person("Jobs", 50);
    }


}
