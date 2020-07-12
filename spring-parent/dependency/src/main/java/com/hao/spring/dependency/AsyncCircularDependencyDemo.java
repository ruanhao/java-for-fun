package com.hao.spring.dependency;

import com.hao.spring.dependency.configurations.AsyncConfiguration;
import org.junit.Assert;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 运行结果和 {@link PostProcessorDemo PostProcessorDemo} 类似，A 因为加了 @Async 注解，
 * 在 initializeBean 之后变为了代理对象，和注入进 B 中的引用发生了不一致性。
 */
public class AsyncCircularDependencyDemo {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext appCtx =
                     new AnnotationConfigApplicationContext(AsyncConfiguration.class)){
            Assert.fail("Can not be here");
        } catch (BeanCurrentlyInCreationException e) {

        }



    }
}
