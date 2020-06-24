package com.hao.notes.spring.config;

import com.hao.notes.spring.components.Student;
import com.hao.notes.spring.components.object.provider.Person;
import com.hao.notes.spring.components.object.provider.User;
import java.util.Optional;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan("com.hao.notes.spring.components.object.provider")
public class ObjectProviderConfig {

    @Autowired
    ObjectProvider<User> userObjectProvider;

    @Autowired
    Optional<Person> personOpt;

    public ObjectProvider<User> getUserObjectProvider() {
        return userObjectProvider;
    }

    public Optional<Person> getPersonOpt() {
        return personOpt;
    }
}

