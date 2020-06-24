package com.hao.notes.mvc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
class MyConfiguration implements WebMvcConfigurer {
    
    @Bean
    public Converter<String, Employee> employeeConverter() {
        return new Converter<String, Employee>() {

            @Override
            public Employee convert(String source) {
                System.err.println("source: " + source);
                String[] tokens = source.split("-");
                String id = tokens[0];
                String name = tokens[1];
                return Employee.builder().id(id).name(name).build();
            }
            
        };
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();     
    }
}
