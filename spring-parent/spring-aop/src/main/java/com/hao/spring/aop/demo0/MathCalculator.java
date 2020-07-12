package com.hao.spring.aop.demo0;

import org.springframework.stereotype.Component;

@Component
public class MathCalculator {
    public int div(int a, int b) {
        return a / b;
    }
}
