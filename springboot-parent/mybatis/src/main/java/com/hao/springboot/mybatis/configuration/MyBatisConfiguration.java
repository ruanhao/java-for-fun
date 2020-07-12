package com.hao.springboot.mybatis.configuration;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;

@org.springframework.context.annotation.Configuration
public class MyBatisConfiguration implements ConfigurationCustomizer {
    @Override
    public void customize(Configuration configuration) {
        /**
         *
         * 如不使用 xml 方式配置（不定义 mybatis.config-location），
         * 则可以将 MyBatis 的配置通过代码的方式写在这里。
         */
        System.err.println("自定义 MyBatis 配置");
    }
}
