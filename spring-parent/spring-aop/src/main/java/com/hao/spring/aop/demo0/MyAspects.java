package com.hao.spring.aop.demo0;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspects {

    // 抽取公共的切入点表达式
    @Pointcut("execution(public int com.hao.spring.aop.demo0.MathCalculator.*(..))")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        System.err.println(String.format("%s 开始运行，参数为: %s",
                joinPoint.getSignature().getName(),
                Arrays.asList(joinPoint.getArgs())
        ));
    }

    /*
     * 包括正常返回或异常返回
     */
    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint) {
        System.err.println(joinPoint.getSignature().getName() + " 运行结束");
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result) {
        System.err.println(joinPoint.getSignature().getName() + " 正常返回，返回值: " + result);
    }

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        System.err.println(joinPoint.getSignature().getName() + " 运行异常：" + exception);
    }

}
