package com.hao.spring.dependency.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@ComponentScan("com.hao.spring.dependency.components.async")
public class AsyncConfiguration {


}
