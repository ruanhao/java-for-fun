package com.hao.spring.dependency;

import com.hao.spring.dependency.components.DbService;
import com.hao.spring.dependency.components.User;
import com.hao.spring.dependency.configurations.PostProcessorConfiguration;
import org.junit.Assert;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PostProcessorDemo {

    /**
     * Demo 效果是：
     * 在 initializeBean 的时候 ({@link AbstractAutowireCapableBeanFactory AbstractAutowireCapableBeanFactory:595}) ，
     * 使用自定义的 beanPostProcessor 返回一个对象 dbService1 ，而二级缓存中存在一个提前被引用的对象 dbService0，
     * 如果不加阻止，则存入 singletonObjects 的将是 dbService1 ，而 user 中引用的是 dbService0 ，将造成不一致，
     * 所以 Spring 必须报错。
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(PostProcessorConfiguration.class);

        applicationContext.registerBeanDefinition("user",
                BeanDefinitionBuilder.rootBeanDefinition(User.class).getBeanDefinition());
        applicationContext.registerBeanDefinition("dbService",
                BeanDefinitionBuilder.rootBeanDefinition(DbService.class).getBeanDefinition());

        try {
            applicationContext.getBean(DbService.class);
            Assert.fail("Can not be here");
        } catch (BeanCurrentlyInCreationException e) {

        }
    }



}
