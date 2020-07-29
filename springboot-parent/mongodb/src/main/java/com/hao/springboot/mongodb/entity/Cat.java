package com.hao.springboot.mongodb.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;

@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TypeAlias("Cat")
public class Cat extends Animal {
    Boolean canClimb;
}
