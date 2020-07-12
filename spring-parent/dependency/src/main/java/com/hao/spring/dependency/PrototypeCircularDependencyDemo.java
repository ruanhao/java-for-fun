package com.hao.spring.dependency;

import com.hao.spring.dependency.configurations.PrototypeConfiguration;
import org.junit.Assert;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 */
public class PrototypeCircularDependencyDemo {
    public static void main(String[] args) {
        /**
         * prototype 循环依赖的情况下，创建 context 不会报错
         */
        AnnotationConfigApplicationContext appCtx =
                new AnnotationConfigApplicationContext(PrototypeConfiguration.class);
        /**
         * 这步会在 {@link AbstractBeanFactory AbstractBeanFactory:267} 处抛出异常
         */

        try {
            appCtx.getBean("prototypeB");
            Assert.fail("Can not be here");
        } catch (UnsatisfiedDependencyException e) {

        }


    }
}
