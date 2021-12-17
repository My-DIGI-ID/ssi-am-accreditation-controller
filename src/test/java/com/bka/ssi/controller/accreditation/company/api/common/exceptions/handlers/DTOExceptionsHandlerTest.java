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

package com.bka.ssi.controller.accreditation.company.api.common.exceptions.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.factories.RestErrorResponseFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import javax.validation.ConstraintViolationException;

class DTOExceptionsHandlerTest {

    private final Logger logger = LoggerFactory.getLogger(DTOExceptionsHandlerTest.class);
    private MockHttpServletRequest request;
    private ResponseEntity<RestErrorResponse> response;
    private MessageSource messageSource;
    private RestErrorResponseFactory factory;
    private DTOExceptionsHandler handler;

    @BeforeEach
    void setUp() {
        messageSource = Mockito.mock(MessageSource.class);
        factory = new RestErrorResponseFactory(messageSource);
        handler = new DTOExceptionsHandler(factory, logger);
        request = new MockHttpServletRequest(null, "/test/api/endpoint");
    }

    @Test
    void handleMethodArgumentNotValidException() {
        // given
        MethodArgumentNotValidException exception =
            Mockito.mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = Mockito.mock(BindingResult.class);

        Mockito.when(bindingResult.getFieldErrors()).thenReturn(
            Arrays.asList(new FieldError(
                "errors", "name", "error message")));
        Mockito.when(exception.getBindingResult()).thenReturn(bindingResult);

        // when
        response = handler.handleMethodArgumentNotValidException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 400);
        assertEquals(response.getBody().getMessage(), "{name=error message}");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleMethodArgumentNotValidExceptionWithMessageGenerationFailed() {
        // given
        MethodArgumentNotValidException exception =
            Mockito.mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = Mockito.mock(BindingResult.class);

        Mockito.when(bindingResult.getFieldErrors()).thenReturn(null);
        Mockito.when(exception.getBindingResult()).thenReturn(bindingResult);

        // when
        response = handler.handleMethodArgumentNotValidException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 400);
        assertEquals(response.getBody().getMessage(), "No message available");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleConstraintViolationException() {
        // given
        ConstraintViolationException exception =
            new ConstraintViolationException("Validation failed", null);

        // when
        response = handler.handleConstraintViolationException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 400);
        assertEquals(response.getBody().getMessage(), "Validation failed");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleConstraintViolationExceptionWithoutMessage() {
        // given
        ConstraintViolationException exception =
            new ConstraintViolationException(null);

        // when
        response = handler.handleConstraintViolationException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 400);
        assertEquals(response.getBody().getMessage(), null);
        assertNotEquals(response.getBody().getTimestamp(), null);
    }
}