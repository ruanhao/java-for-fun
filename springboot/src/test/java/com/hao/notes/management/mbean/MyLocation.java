package com.hao.notes.management.mbean;

import java.time.LocalDateTime;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@ManagedResource(objectName = "MyExamples:type=MyJMX,name=MyLocation")
@Component
public class MyLocation { // @ManagedResource class must be public
    String location = "Shanhai";

    @ManagedAttribute
    public String getLocation() {
        return location;
    }

    @ManagedAttribute
    public void setLocation(String location) {
        this.location = location;
    }

    @ManagedOperation
    public String withTimeInfo() {
        return location + "@" + LocalDateTime.now().toString();
    }

}
