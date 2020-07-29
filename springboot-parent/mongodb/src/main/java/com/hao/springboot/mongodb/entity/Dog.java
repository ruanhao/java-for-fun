package com.hao.springboot.mongodb.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;

@Setter
@Getter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TypeAlias("Dog")
public class Dog extends Animal {
    Boolean canBark;
    Boolean canSwim;
}
