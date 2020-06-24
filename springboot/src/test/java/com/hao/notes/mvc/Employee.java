package com.hao.notes.mvc;

import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
class Employee {
    @Builder.Default
    String id = UUID.randomUUID().toString();
    String name;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date birthday;
    @NumberFormat(pattern = "#,###,###.#")
    Float salary;
}
