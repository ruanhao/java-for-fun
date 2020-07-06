package com.hao.spring.pojo;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    String name;

    List<String> alias;

    Integer age;

    Boolean vip;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date birthday;

    String birthdayStr;

    Country country;

    Map<String, Object> bodyInfo;
}
