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
}

