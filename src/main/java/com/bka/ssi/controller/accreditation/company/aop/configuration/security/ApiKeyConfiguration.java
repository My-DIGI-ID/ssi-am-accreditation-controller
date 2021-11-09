package com.bka.ssi.controller.accreditation.company.aop.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiKeyConfiguration {

    public final static String API_KEY_ID = "ACCR_API_KEY";

    private String apiKeyHeaderName;
    private String apiKey;

    public ApiKeyConfiguration(
        @Value("${accreditation.api.api_key_header_name}") String apiKeyHeaderName,
        @Value("${accreditation.api.api_key}") String apiKey) {
        this.apiKeyHeaderName = apiKeyHeaderName;
        this.apiKey = apiKey;
    }

    public String getApiKeyHeaderName() {
        return apiKeyHeaderName;
    }

    public String getApiKey() {
        return apiKey;
    }
}
