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

package com.bka.ssi.controller.accreditation.company.application.security.utilities;

import com.bka.ssi.controller.accreditation.company.application.utilities.http.HttpHeaderUtility;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

/**
 * The type Api key parser.
 */
@Component
public class ApiKeyParser {

    private final ApiKeyRegistry registry;

    /**
     * Instantiates a new Api key parser.
     *
     * @param registry the registry
     */
    public ApiKeyParser(
        ApiKeyRegistry registry) {
        this.registry = registry;
    }

    /**
     * Gets api key by header name.
     *
     * @param headerName the header name
     * @return the api key by header name
     */
    public String getApiKeyByHeaderName(String headerName) {
        return HttpHeaderUtility.getHttpHeader(headerName);
    }

    /**
     * Gets api key by id.
     *
     * @param id the id
     * @return the api key by id
     */
    public String getApiKeyById(String id) {
        Pair<String, String> headerNameAndKeyPair = this.registry.getEntryById(id);

        if (headerNameAndKeyPair == null) {
            return null;
        }

        String headerName = headerNameAndKeyPair.getFirst();
        return this.getApiKeyByHeaderName(headerName);
    }
}
