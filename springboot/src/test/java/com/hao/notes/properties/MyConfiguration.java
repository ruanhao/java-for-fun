package com.hao.notes.properties;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = "classpath:test-properties-config/mybeans.xml")
class MyConfiguration {



}
