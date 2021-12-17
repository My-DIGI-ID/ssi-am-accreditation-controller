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

package com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class RestErrorResponseFactoryTest {

    private MessageSource messageSource;
    private RestErrorResponseFactory factory;
    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        messageSource = Mockito.mock(MessageSource.class);
        factory = new RestErrorResponseFactory(messageSource);
        request = new MockHttpServletRequest(null, "/test/api/endpoint");
    }

    @Test
    void createWithStringMessage() {
        // given
        Mockito.when(messageSource.getMessage(Mockito.anyString(),
            Mockito.nullable(Object[].class), Mockito.any(Locale.class)))
            .thenReturn("Public error message");

        // when
        RestErrorResponse response = factory.create("any", HttpStatus.BAD_REQUEST, request);

        // then
        assertEquals(response.getPath(), "/test/api/endpoint");
        assertEquals(response.getStatus(), 400);
        assertEquals(response.getError(), "Bad Request");
        assertEquals(response.getMessage(), "Public error message");
        assertNotEquals(response.getTimestamp(), null);
    }

    @Test
    void createWithFieldErrors() {
        // given
        List<FieldError> errors = new ArrayList<>();
        errors.add(new FieldError("object", "firstName", "First name is required"));

        // when
        RestErrorResponse response = factory.create(errors, HttpStatus.BAD_REQUEST, request);

        // then
        assertEquals(response.getPath(), "/test/api/endpoint");
        assertEquals(response.getStatus(), 400);
        assertEquals(response.getError(), "Bad Request");
        assertEquals(response.getMessage(), "{firstName=First name is required}");
        assertNotEquals(response.getTimestamp(), null);
    }
}
