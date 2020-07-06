package com.hao.springboot.web.static_;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

/**
 * @see WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
 * 静态资源访问路径默认配置如下：
 * 1. classpath:/META-INF/resources/webjars/
 * 2. classpath:/META-INF/resources/
 * 3. classpath:/resources/
 * 4. classpath:/static/
 * 5. classpath:/public/
 * <p>
 * spring.resources.add-mappings=false 可以禁用默认 ResourceHandlers 的注册过程
 */
public class StaticResourceLocations {
    /**
     * 访问 /webjars/jquery/3.3.1/jquery.js 可以获取 webjars 静态资源
     * 访问 /static.txt 可以定位到 classpath:/static/static.txt 文件
     */
}
