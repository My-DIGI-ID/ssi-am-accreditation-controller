package com.bka.ssi.controller.accreditation.company.application.security.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.bka.ssi.controller.accreditation.company.aop.configuration.agents.ACAPYConfiguration;
import com.bka.ssi.controller.accreditation.company.aop.configuration.security.ApiKeyConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class ApiKeyParserTest {

    private ApiKeyParser parser;

    @BeforeEach
    public void init() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(request.getHeader("X-API-Key")).thenReturn("123");

        ACAPYConfiguration acapyConfiguration =
            new ACAPYConfiguration(null, null, null, "123", "X-API-Key");
        ApiKeyConfiguration apiKeyConfiguration = new ApiKeyConfiguration("X-API-Key", "123");
        ApiKeyRegistry registry = new ApiKeyRegistry(acapyConfiguration, apiKeyConfiguration);

        this.parser = new ApiKeyParser(registry);
    }

    @Test
    public void getApiKeyById() {
        String apiKey = this.parser.getApiKeyById(ACAPYConfiguration.API_KEY_ID);

        assertNotNull(apiKey);
        assertEquals("123", apiKey);
    }

    @Test
    public void getApiKeyByIdNoEntry() {
        String apiKey = this.parser.getApiKeyById("");

        assertNull(apiKey);
    }
}
