package com.hao.notes.spring.conditions;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WindowsCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String platform = context.getEnvironment().getProperty("os.name");
        System.err.println("platform: " + platform);
        return platform.toLowerCase().contains("windows");
    }
}
