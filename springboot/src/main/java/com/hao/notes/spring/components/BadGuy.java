package com.hao.notes.spring.components;

import com.hao.notes.spring.MyAnnotation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
@MyAnnotation
public class BadGuy {
    String name;
    int age;
}
