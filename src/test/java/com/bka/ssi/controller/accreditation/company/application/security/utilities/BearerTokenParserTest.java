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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class BearerTokenParserTest {

    private String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIiLCJpYXQiOjE2M" +
        "zY1NjA3NTAsImV4cCI6MTYzNjU2MDY1NiwiYXVkIjoiIiwic3ViIjoiIiwicHJlZmVycmVkX3VzZXJuYW1lIjoid" +
        "GVzdCJ9.tvzD1HhMllyEFhzujKkRcKr1lBsqRACLbXm-b6-UOEk";

    private String invalidToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.e3Rlc3QiaXNzIjoiIi" +
        "wiaWF0IjoxNjM2NTYwNzUwLCJleHAiOjE2MzY1NjA2NTYsImF1ZCI6IiIsInN1YiI6IiIsInByZWZlcnJlZF91c2" +
        "VybmFtZSI6InRlc3QifQ==";

    private BearerTokenParser bearerTokenParser;

    @BeforeEach
    void init() {
        bearerTokenParser = new BearerTokenParser();
        ReflectionTestUtils.setField(bearerTokenParser, "userIdentifierEntry",
            "preferred_username");
    }

    @Test
    public void parseUserFromJWTToken() {
        try (MockedStatic<HttpHeaderUtility> httpHeaderUtilityMockedStatic =
                 Mockito.mockStatic(HttpHeaderUtility.class)) {
            httpHeaderUtilityMockedStatic.when(
                    () -> HttpHeaderUtility.getHttpHeader("authorization"))
                .thenReturn(token);

            Assertions.assertEquals("test", bearerTokenParser.parseUserFromJWTToken());
        }
    }

    @Test
    void parseUserFromInvalidToken() {
        try (MockedStatic<HttpHeaderUtility> httpHeaderUtilityMockedStatic =
                 Mockito.mockStatic(HttpHeaderUtility.class)) {
            httpHeaderUtilityMockedStatic.when(
                    () -> HttpHeaderUtility.getHttpHeader("authorization"))
                .thenReturn(invalidToken);

            Assertions.assertEquals("undefined", bearerTokenParser.parseUserFromJWTToken());
        }
    }
}
