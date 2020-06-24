package com.hao.notes.spring.config;

import com.hao.notes.spring.components.Car;
import com.hao.notes.spring.components.Person;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfig {

    @Bean
    @Profile("test")
    public Person tester() {
        return new Person("tester", 30);
    }

    @Bean
    @Profile("dev")
    public Person developer() {
        return new Person("developer", 30);
    }

    @Bean
    @Profile("prod")
    public Person productor() {
        return new Person("productor", 30);
    }

}
