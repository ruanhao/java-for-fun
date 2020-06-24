package com.hao.notes.spring.conditions;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.Annotation;

public class MacOsCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String platform = context.getEnvironment().getProperty("os.name");
        System.err.println("platform: " + platform);
        return platform.toLowerCase().contains("mac");
    }
}
