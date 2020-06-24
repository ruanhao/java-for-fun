package com.hao.notes.mvc;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Setter
@Getter
class User {
    @Builder.Default
    String id = UUID.randomUUID().toString();
    @Builder.Default
    long createdTime = System.currentTimeMillis();
}
