package com.hao.notes.spring.config;

import com.hao.notes.spring.components.Person;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.hao.notes.spring.components.circledep")
@EnableAspectJAutoProxy
public class LearnCircleDepConfig {

}
