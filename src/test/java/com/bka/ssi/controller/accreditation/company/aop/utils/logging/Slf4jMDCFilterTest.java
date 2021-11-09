package com.bka.ssi.controller.accreditation.company.aop.utils.logging;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bka.ssi.controller.accreditation.company.aop.logging.Slf4jMDCFilter;
import com.bka.ssi.controller.accreditation.company.aop.logging.Slf4jMDCFilterConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

public class Slf4jMDCFilterTest {

    private MockMvc mockMvc;

    @RestController
    private class TestController {
        @GetMapping("/test")
        public Map<String, String> test() {
            return MDC.getCopyOfContextMap();
        }
    }

    @BeforeEach
    void setUp() {
        Slf4jMDCFilter filter = new Slf4jMDCFilter();
        this.mockMvc = MockMvcBuilders
            .standaloneSetup(new TestController())
            .addFilter(filter)
            .build();
    }

    @Test
    public void shouldContainCorrelationId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/test")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(header().exists(Slf4jMDCFilterConfiguration.DEFAULT_RESPONSE_TOKEN_HEADER))
            .andReturn();
    }

    @Test
    public void shouldForwardExistingCorrelationId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/test")
            .header("x-operation-id", "123")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(header().string("x-operation-id", "123"))
            .andReturn();
    }
}
