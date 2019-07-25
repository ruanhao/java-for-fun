package com.hao.notes.mvc;

import java.util.UUID;

import javax.servlet.http.Cookie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import lombok.SneakyThrows;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ControllerExamples {

    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void testMatchingControllerWithParams() {
        mockMvc.perform(MockMvcRequestBuilders.get("/testMatchingWithParams")
                .param("username", "Peter")
                .param("age", "20"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk());
        
        mockMvc.perform(MockMvcRequestBuilders.get("/testMatchingWithParams")
                .param("username", "Peter")
                .param("age", "10"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
        
        mockMvc.perform(MockMvcRequestBuilders.get("/testMatchingWithParams")
                .param("age", "20"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
   
    @Test
    @SneakyThrows
    public void testMatchingControllerWithHeaders() {
        mockMvc.perform(MockMvcRequestBuilders.get("/testMatchingWithHeaders")
                .header("Accept-Language", "en-US"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk());
        
        mockMvc.perform(MockMvcRequestBuilders.get("/testMatchingWithHeaders")
                .header("Accept-Language", "zh-CN"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
    
    @Test
    @SneakyThrows
    public void testAntPath() {
        
        /*
         * ?: 匹配一个字符
         * *: 匹配多个任意字符
         *  **: 匹配多层路径
         */
        
        mockMvc.perform(MockMvcRequestBuilders.get("/testAntPath/a/b/c/test"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.handler().methodName("testAntPath2"))
        .andExpect(MockMvcResultMatchers.status().isOk());
                
        mockMvc.perform(MockMvcRequestBuilders.get("/testAntPath/a/test"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.handler().methodName("testAntPath1"))
        .andExpect(MockMvcResultMatchers.status().isOk());
        
        mockMvc.perform(MockMvcRequestBuilders.get("/testAntPath/abc/test"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.handler().methodName("testAntPath3"))
        .andExpect(MockMvcResultMatchers.status().isOk());

    }
    
    @Test
    @SneakyThrows
    public void testHiddenHttpMethodFilter() {
        /*
         *  浏览器表单只支持 GET 与 POST 请求，而 DELETE，PUT 等方法并不支持，
         *  Spring3.0 添加了一个过滤器，可以将这些请求转换为标准的 HTTP 方法，
         *  使得支持 GET，POST，PUT 与 DELETE 请求。  
         */
        mockMvc.perform(MockMvcRequestBuilders.post("/testHiddenHttpMethodFilterDelete")
                .param("_method", "DELETE"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.handler().methodName("testHiddenHttpMethodFilterDelete"))
        .andExpect(MockMvcResultMatchers.status().isOk());
        
        mockMvc.perform(MockMvcRequestBuilders.post("/testHiddenHttpMethodFilterPut")
                .param("_method", "PUT"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.handler().methodName("testHiddenHttpMethodFilterPut"))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    @SneakyThrows
    public void testCookieValue() {
        String randomStr = UUID.randomUUID().toString();
        mockMvc.perform(MockMvcRequestBuilders.get("/testCookieValue")
                .cookie(new Cookie("randomStr", randomStr)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.content().string(randomStr))
        .andExpect(MockMvcResultMatchers.status().isOk());


    }
    
    @Test
    @SneakyThrows
    public void testServletApi() {
        /*
         * MVC 的 Handler 方法可以接受的 ServletAPI 类型的参数，具体可参考：
         * org.springframework.web.servlet.mvc.method.annotation.ServletRequestMethodArgumentResolver.resolveArgument/4
         */
        mockMvc.perform(MockMvcRequestBuilders.get("/testServletApi"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.content().string("hello world"))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    @SneakyThrows
    public void testModelAttribute() {
        mockMvc.perform(MockMvcRequestBuilders.get("/testModelAttribute?id=1"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
        
        mockMvc.perform(MockMvcRequestBuilders.get("/testModelAttribute"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(org.hamcrest.Matchers.not("1")));
    }
    
    @Test
    @SneakyThrows
    public void testStaticResources() {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello.html"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk());
     }
}

