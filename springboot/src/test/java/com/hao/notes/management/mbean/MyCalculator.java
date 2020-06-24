package com.hao.notes.management.mbean;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@ManagedResource(
        objectName = "MyExamples:type=MyJMX,name=MyCalculator",
        description = "A calculator to demonstrate JMX in the SpringFramework")
@Component
public class MyCalculator {
    private String name;
    private int lastCalculation;

    @ManagedAttribute(description = "Calculator name")
    public String getName() {
        return name;
    }

    @ManagedAttribute(description = "Calculator name")
    public void setName(String name) {
        this.name = name;
    }

    @ManagedAttribute(description = "The last calculation")
    public int getLastCalculation() {
        return lastCalculation;
    }

    @ManagedOperation(description = "Calculate two numbers")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "x", description = "The first number"),
            @ManagedOperationParameter(name = "y", description = "The second number")
    })
    public void calculate(int x, int y) {
        lastCalculation = x + y;
    }
}