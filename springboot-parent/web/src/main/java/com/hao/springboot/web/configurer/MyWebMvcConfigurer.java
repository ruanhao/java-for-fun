package com.hao.springboot.web.configurer;

import com.google.common.base.CaseFormat;
import com.hao.springboot.web.dto.CityDto;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 添加自定义的视图解析跳转，这种方式可以避免仅仅为了页面跳转而写一个专门的 controller
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/home").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new HandlerMethodArgumentResolver() {
            @Override
            public boolean supportsParameter(MethodParameter parameter) {
                return CityDto.class.equals(parameter.getParameterType());
            }

            @Override
            public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
                // Object obj = parameter.getParameterType().newInstance();
                Object obj = BeanUtils.instantiateClass(parameter.getParameterType());
                BeanWrapperImpl beanWrapper = new BeanWrapperImpl(obj);
                beanWrapper.setAutoGrowNestedPaths(true);
                webRequest.getParameterMap().forEach((name, vs) -> {
                    String camelPropertyName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, name);
                    String value = vs[0];
                    beanWrapper.setPropertyValue(camelPropertyName, value);
                });
                return obj;
            }
        });
    }


}
