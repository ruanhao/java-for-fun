package com.hao.springboot.mongodb.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;

@ToString
@Builder
@NoArgsConstructor
@TypeAlias("AdAuth")
public class AdAuth extends Auth {

    String clientId;
    String secret;

    @PersistenceConstructor
    public AdAuth(String clientId, String secret) {
        this.clientId = clientId;
        this.secret = secret;
    }
}
