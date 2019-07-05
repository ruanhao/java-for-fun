package com.hao.notes.management.actuator;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties={
        "server.port=40839",
        "management.endpoint.health.show-details=always",
        "management.endpoints.web.exposure.include=*"
})
public class Examples {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    RestTemplate rt;

    @Test
    @SneakyThrows
    public void testActuator() {

        mockMvc.perform(MockMvcRequestBuilders.get("/actuator/health").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/actuator/myfeatures/a")
                .contentType(MediaType.APPLICATION_JSON)
                .param("enabled", "true")  // Endpoint operations don't support complex input
                ).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        mockMvc.perform(MockMvcRequestBuilders.get("/actuator/myfeatures")
                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/actuator/myrestfeatures/b")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"enabled\": false}")
                ).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        mockMvc.perform(MockMvcRequestBuilders.get("/actuator/myrestfeatures")
                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());

        TimeUnit.DAYS.sleep(1L);
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown=true)
    @NoArgsConstructor
    @AllArgsConstructor
    static class Health {
        private Status status;

        private Map<String, Object> details;

        static enum Status {
            DOWN, UP;
        }
    }

}
