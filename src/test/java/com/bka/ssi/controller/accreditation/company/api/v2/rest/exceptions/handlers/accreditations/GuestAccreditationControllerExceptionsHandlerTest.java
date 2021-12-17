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

package com.bka.ssi.controller.accreditation.company.api.v2.rest.exceptions.handlers.accreditations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.factories.RestErrorResponseFactory;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidGuestInitialStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Locale;

public class GuestAccreditationControllerExceptionsHandlerTest {

    private final Logger logger =
        LoggerFactory.getLogger(GuestAccreditationControllerExceptionsHandlerTest.class);
    private MockHttpServletRequest request;
    private ResponseEntity<RestErrorResponse> response;
    private MessageSource messageSource;
    private RestErrorResponseFactory factory;
    private GuestAccreditationControllerExceptionsHandler handler;

    @BeforeEach
    void setUp() {
        messageSource = Mockito.mock(MessageSource.class);
        factory = new RestErrorResponseFactory(messageSource);
        handler = new GuestAccreditationControllerExceptionsHandler(factory, logger);
        request = new MockHttpServletRequest(null, "/test/api/endpoint");
    }

    @Test
    void handleInvalidGuestInitialStateException() {
        // given
        InvalidGuestInitialStateException exception =
            new InvalidGuestInitialStateException();
        Mockito
            .when(messageSource.getMessage(
                "message.common.rest.error.guest_accreditation_state_exception_placeholder", null,
                Locale.ENGLISH))
            .thenReturn("Public error message");

        // when
        response = handler.handleInvalidGuestAccreditationStateException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 409);
        assertEquals(response.getBody().getMessage(), "Public error message");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleInvalidGuestInitialStateWithoutMessage() {
        // given
        InvalidGuestInitialStateException exception =
            new InvalidGuestInitialStateException();
        Mockito
            .when(messageSource.getMessage(
                "message.common.rest.error.guest_accreditation_state_exception_placeholder", null,
                Locale.ENGLISH))
            .thenThrow(new NoSuchMessageException("No message"));

        // when
        response = handler.handleInvalidGuestAccreditationStateException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 409);
        assertEquals(response.getBody().getMessage(), "No message available");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }
}
