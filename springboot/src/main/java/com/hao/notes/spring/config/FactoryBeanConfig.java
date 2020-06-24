package com.hao.notes.spring.config;

import com.hao.notes.spring.components.Person;
import com.hao.notes.spring.components.Student;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Configuration
@ComponentScan(value = "com.hao.notes.spring")
public class FactoryBeanConfig {

    @Bean
    public PersonFactory personFactory() {
        return new PersonFactory();
    }

}


class PersonFactory implements FactoryBean<Person> {

    @Override
    public Person getObject() throws Exception {
        return new Person(UUID.randomUUID().toString(), 30);
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
