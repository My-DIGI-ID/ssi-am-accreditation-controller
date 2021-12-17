/*
 * Copyright 2021 Bundesrepublik Deutschland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bka.ssi.controller.accreditation.company.aop.configuration.agents;

import com.fasterxml.jackson.databind.DeserializationFeature;
import io.github.my_digi_id.acapy_client.invoker.ApiClient;
import io.github.my_digi_id.acapy_client.invoker.auth.ApiKeyAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuarion for the ACAPY client
 */
@Configuration
public class ACAPYConfiguration {


    /**
     * The constant API_KEY_ID.
     */
    public final static String API_KEY_ID = "ACAPY_WEBHOOK_API_KEY";

    private String host;
    private String port;
    private String apiKey;
    private String webhookApiKey;
    private String apiKeyHeaderName;

    /**
     * Api client api client.
     *
     * @return the api client
     */
    @Bean
    public ApiClient apiClient() {
        ApiClient apiClient =
            io.github.my_digi_id.acapy_client.invoker.Configuration.getDefaultApiClient();

        apiClient.setBasePath(this.host + ":" + this.port);

        ((ApiKeyAuth) apiClient.getAuthentication("ApiKeyHeader")).setApiKey(this.apiKey);

        apiClient.getJSON().getMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return apiClient;
    }

    /**
     * Instantiates a new Acapy configuration.
     *
     * @param host             the host
     * @param port             the port
     * @param apiKey           the api key
     * @param webhookApiKey    the webhook api key
     * @param apiKeyHeaderName the api key header name
     */
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

    /**
     * Gets host.
     *
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * Gets port.
     *
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * Gets api key.
     *
     * @return the api key
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Gets webhook api key.
     *
     * @return the webhook api key
     */
    public String getWebhookApiKey() {
        return webhookApiKey;
    }

    /**
     * Gets api key header name.
     *
     * @return the api key header name
     */
    public String getApiKeyHeaderName() {
        return apiKeyHeaderName;
    }

    /**
     * Gets api client.
     *
     * @return the api client
     */
    public ApiClient getApiClient() {
        return apiClient();
    }
}
