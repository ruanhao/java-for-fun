package com.hao.notes.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Configuration
@Validated
@PropertySource("classpath:test-properties-config/placeholder.properties")
@ConfigurationProperties(prefix = "user")
@Component
public class User {
    String id;
    int age;
    String name;
    String password;
    String info;
}