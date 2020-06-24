package com.hao.notes.spring.components;

import lombok.ToString;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@ToString
public class Car implements InitializingBean, DisposableBean {

    public void init() {
        System.err.println("Car.init-method"); // 4
    }

    public void destroy0() { // 7
        System.err.println("Car.destroy-method");
    }

    public Car() {
        System.err.println("Car()"); // 1
    }

    @PostConstruct
    public void postConstruct() {
        System.err.println("@PostConstruct"); // 2
    }

    @PreDestroy
    public void preDestroy() { // 5
        System.err.println("@preDestroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.err.println("InitializingBean.afterPropertiesSet"); // 3
    }

    @Override
    public void destroy() throws Exception { // 6
        System.err.println("DisposableBean.destroy");
    }



}
