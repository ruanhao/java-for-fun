package com.hao.springboot.mongodb.entity;

import lombok.*;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@TypeAlias("LocalAuth")
public class LocalAuth extends Auth {

    String username;
    String password;

    @PersistenceConstructor
    public LocalAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
