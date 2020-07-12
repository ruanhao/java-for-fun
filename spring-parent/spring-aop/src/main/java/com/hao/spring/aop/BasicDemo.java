package com.hao.spring.aop;

import com.hao.spring.aop.demo0.Demo0Configuration;
import com.hao.spring.aop.demo0.MathCalculator;
import org.junit.Assert;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BasicDemo {

    /**
     * 最终调用的地方在于 CglibAopProxy:691
     */
    public static void main(String[] args) {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(Demo0Configuration.class);

        MathCalculator x = ctx.getBean(MathCalculator.class);

        x.div(1, 2);
        System.err.println("================");
        try {
            x.div(2, 0);
            Assert.fail("Can not be here");
        } catch (Exception e) {

        }

    }
}
