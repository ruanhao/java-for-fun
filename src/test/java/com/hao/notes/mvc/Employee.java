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
class Employee {
    @Builder.Default
    String id = UUID.randomUUID().toString();
    String name;
}
