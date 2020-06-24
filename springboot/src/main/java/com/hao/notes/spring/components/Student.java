package com.hao.notes.spring.components;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Student {
    String name;
    int age;
}
