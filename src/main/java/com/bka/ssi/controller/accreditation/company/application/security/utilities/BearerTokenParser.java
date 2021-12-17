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
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * The type Bearer token parser.
 */
@Component
public class BearerTokenParser {

    @Value("${accreditation.jwt.userIdentifierEntryName}")
    private String userIdentifierEntry;

    private Base64.Decoder decoder;

    /**
     * Instantiates a new Bearer token parser.
     */
    public BearerTokenParser() {
        this.decoder = Base64.getDecoder();
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        String token = HttpHeaderUtility.getHttpHeader("authorization");

        if (token != null) {
            return token.replace("Bearer ", "");
        }

        return null;
    }

    /**
     * Parse user from jwt token string.
     *
     * @return the string
     */
    public String parseUserFromJWTToken() {
        String token = this.getToken();

        String[] chunks = token.split("\\.");
        String payload = new String(decoder.decode(chunks[1]));

        Object username;
        try {
            username = new JSONParser(payload).object().get(this.userIdentifierEntry);
        } catch (Exception e) {
            username = "undefined";
        }

        return username.toString();
    }
}
