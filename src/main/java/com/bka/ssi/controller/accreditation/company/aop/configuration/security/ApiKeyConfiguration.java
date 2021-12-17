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

package com.bka.ssi.controller.accreditation.company.aop.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * The type Api key configuration.
 */
@Configuration
public class ApiKeyConfiguration {

    /**
     * The constant API_KEY_ID.
     */
    public final static String API_KEY_ID = "ACCR_API_KEY";

    private String apiKeyHeaderName;
    private String apiKey;

    /**
     * Instantiates a new Api key configuration.
     *
     * @param apiKeyHeaderName the api key header name
     * @param apiKey           the api key
     */
    public ApiKeyConfiguration(
        @Value("${accreditation.api.api_key_header_name}") String apiKeyHeaderName,
        @Value("${accreditation.api.api_key}") String apiKey) {
        this.apiKeyHeaderName = apiKeyHeaderName;
        this.apiKey = apiKey;
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
     * Gets api key.
     *
     * @return the api key
     */
    public String getApiKey() {
        return apiKey;
    }
}
