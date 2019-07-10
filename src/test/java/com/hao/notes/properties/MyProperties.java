package com.hao.notes.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.ToString;

@Component
@ToString
public class MyProperties {

    @Value("I love you")
    String msg;

    @Value("#{systemProperties['os.name']}")
    String osName;

    @Value("#{ T(java.lang.Math).random() * 100.0 }")
    double randomNumber;

    @Value("#{mail.host}") // 其他 bean 属性
    String mailHost;

    @Value("classpath:test-properties-config/test.txt") // 文件资源
    Resource testFile;

    @Value("http://www.google.com")
    Resource url;





}
