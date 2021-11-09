package com.bka.ssi.controller.accreditation.company.aop.configuration.agents;

import com.bka.ssi.controller.accreditation.acapy_client.invoker.ApiClient;
import com.bka.ssi.controller.accreditation.acapy_client.invoker.auth.ApiKeyAuth;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ACAPYConfiguration {

    public final static String API_KEY_ID = "ACAPY_WEBHOOK_API_KEY";

    private String host;
    private String port;
    private String apiKey;
    private String webhookApiKey;
    private String apiKeyHeaderName;

    @Bean
    public ApiClient apiClient() {
        ApiClient apiClient =
            com.bka.ssi.controller.accreditation.acapy_client.invoker.Configuration
                .getDefaultApiClient();

        apiClient.setBasePath(this.host + ":" + this.port);

        ((ApiKeyAuth) apiClient.getAuthentication("ApiKeyHeader")).setApiKey(this.apiKey);

        /* ToDo - if this configuration works out as expected, there is no need for a
            accreditation-acapy-openapi.v2.custom.json and accreditation-acapy-openapi.v2.json
            can be used to generate ACAPY client lib again */
        apiClient.getJSON().getMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return apiClient;
    }

    public ACAPYConfiguration(@Value("${accreditation.agent.host}") String host,
        @Value("${accreditation.agent.port}") String port,
        @Value("${accreditation.agent.api_key}") String apiKey,
        @Value("${accreditation.agent.webhook.api_key}") String webhookApiKey,
        @Value("${accreditation.agent.api_key_header_name}") String apiKeyHeaderName) {
        this.host = host;
        this.port = port;
        this.apiKey = apiKey;
        this.webhookApiKey = webhookApiKey;
        this.apiKeyHeaderName = apiKeyHeaderName;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getWebhookApiKey() {
        return webhookApiKey;
    }

    public String getApiKeyHeaderName() {
        return apiKeyHeaderName;
    }

    public ApiClient getApiClient() {
        return apiClient();
    }
}
