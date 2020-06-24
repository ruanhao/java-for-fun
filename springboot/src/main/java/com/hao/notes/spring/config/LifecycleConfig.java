package com.hao.notes.spring.config;

import com.hao.notes.spring.components.Car;
import com.hao.notes.spring.components.Person;
import com.hao.notes.spring.components.Student;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class LifecycleConfig {


    @Bean(initMethod = "init", destroyMethod = "destroy0")
    public Car car() {
        return new Car();
    }

    @Bean
    public BeanPostProcessor myBeanPostProcess() {
        return new MyBeanPostProcessor();
    }
}

class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("car")) {
            System.err.println("BeanPostProcessor.postProcessBeforeInitialization");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("car")) {
            System.err.println("BeanPostProcessor.postProcessAfterInitialization");
        }
        return bean;
    }
}