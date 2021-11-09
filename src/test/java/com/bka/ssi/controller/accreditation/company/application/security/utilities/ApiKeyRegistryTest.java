package com.bka.ssi.controller.accreditation.company.application.security.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.bka.ssi.controller.accreditation.company.aop.configuration.agents.ACAPYConfiguration;
import com.bka.ssi.controller.accreditation.company.aop.configuration.security.ApiKeyConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApiKeyRegistryTest {

    private ApiKeyRegistry registry;
    private ACAPYConfiguration acapyConfiguration;
    private ApiKeyConfiguration apiKeyConfiguration;

    @BeforeEach
    public void init() {
        acapyConfiguration =
            new ACAPYConfiguration(null, null, null, "123", "X-API-Key");
        apiKeyConfiguration = new ApiKeyConfiguration("X-API-Key", "123");

        this.registry = new ApiKeyRegistry(acapyConfiguration, apiKeyConfiguration);
    }

    @Test
    public void getRegistry() {
        assertNotNull(this.registry.getRegistry());
    }

    @Test
    public void getEntryForACAPY() {
        assertEquals(this.acapyConfiguration.getApiKeyHeaderName(),
            this.registry.getEntryById(ACAPYConfiguration.API_KEY_ID).getFirst());
        assertEquals(this.acapyConfiguration.getWebhookApiKey(),
            this.registry.getEntryById(ACAPYConfiguration.API_KEY_ID).getSecond());
    }

    @Test
    public void getEntryForAccrVeri() {
        assertEquals(this.apiKeyConfiguration.getApiKeyHeaderName(),
            this.registry.getEntryById(ApiKeyConfiguration.API_KEY_ID).getFirst());
        assertEquals(this.apiKeyConfiguration.getApiKey(),
            this.registry.getEntryById(ApiKeyConfiguration.API_KEY_ID).getSecond());
    }
}
