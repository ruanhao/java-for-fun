package com.hao.notes.mvc;

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
}

