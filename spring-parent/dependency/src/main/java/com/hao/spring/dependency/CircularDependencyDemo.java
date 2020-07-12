package com.hao.spring.dependency;

import com.hao.spring.dependency.components.DbService;
import com.hao.spring.dependency.components.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CircularDependencyDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext appCtx = new AnnotationConfigApplicationContext();
        appCtx.refresh();

        appCtx.registerBeanDefinition("user",
                BeanDefinitionBuilder.rootBeanDefinition(User.class).getBeanDefinition());

        appCtx.registerBeanDefinition("dbService",
                BeanDefinitionBuilder.rootBeanDefinition(DbService.class).getBeanDefinition());

        User user = (User) appCtx.getBean("user");
        assert user.getDbService() == appCtx.getBean(DbService.class);
    }
}
