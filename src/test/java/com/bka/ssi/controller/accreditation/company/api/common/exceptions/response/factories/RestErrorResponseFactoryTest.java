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
