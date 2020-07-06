package com.hao.spring.dependency.post;

import com.hao.spring.dependency.components.DbService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ClassUtils;

public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean != null && ClassUtils.getUserClass(bean) == DbService.class) {
            return new DbService() {
                @Override
                public void sayHello() {
                    System.err.println("fake hello!!!");
                }
            };
        }
        return bean;
    }
}
