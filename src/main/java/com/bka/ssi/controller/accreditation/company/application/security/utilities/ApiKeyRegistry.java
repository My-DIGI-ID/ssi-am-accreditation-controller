package com.bka.ssi.controller.accreditation.company.application.security.utilities;

import com.bka.ssi.controller.accreditation.company.aop.configuration.agents.ACAPYConfiguration;
import com.bka.ssi.controller.accreditation.company.aop.configuration.security.ApiKeyConfiguration;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApiKeyRegistry {

    private Map<String, Pair<String, String>> registry;
    private final ACAPYConfiguration acapyConfiguration;
    private final ApiKeyConfiguration apiKeyConfiguration;

    public ApiKeyRegistry(
        ACAPYConfiguration acapyConfiguration,
        ApiKeyConfiguration apiKeyConfiguration) {
        this.acapyConfiguration = acapyConfiguration;
        this.apiKeyConfiguration = apiKeyConfiguration;

        this.init();
    }

    private void init() {
        this.registry = new HashMap<>();

        this.registry.put(
            ACAPYConfiguration.API_KEY_ID,
            Pair.of(this.acapyConfiguration.getApiKeyHeaderName(),
                this.acapyConfiguration.getWebhookApiKey())
        );
        this.registry.put(
            ApiKeyConfiguration.API_KEY_ID,
            Pair.of(this.apiKeyConfiguration.getApiKeyHeaderName(),
                this.apiKeyConfiguration.getApiKey())
        );
    }

    public Pair<String, String> getEntryById(String id) {
        return this.registry.get(id);
    }

    public Map<String, Pair<String, String>> getRegistry() {
        return registry;
    }
}
