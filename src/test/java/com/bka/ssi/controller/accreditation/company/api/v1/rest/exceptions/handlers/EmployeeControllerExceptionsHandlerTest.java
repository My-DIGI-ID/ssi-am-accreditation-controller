package com.bka.ssi.controller.accreditation.company.api.v1.rest.exceptions.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.factories.RestErrorResponseFactory;
import com.bka.ssi.controller.accreditation.company.application.exceptions.ApplicationException;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.DomainException;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.exceptions.InfrastructureException;
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

class EmployeeControllerExceptionsHandlerTest {

    private final Logger logger =
        LoggerFactory.getLogger(EmployeeControllerExceptionsHandlerTest.class);
    private MockHttpServletRequest request;
    private ResponseEntity<RestErrorResponse> response;
    private MessageSource messageSource;
    private RestErrorResponseFactory factory;
    private EmployeeControllerExceptionsHandler handler;

    @BeforeEach
    void setUp() {
        messageSource = Mockito.mock(MessageSource.class);
        factory = new RestErrorResponseFactory(messageSource);
        handler = new EmployeeControllerExceptionsHandler(factory, logger);
        request = new MockHttpServletRequest(null, "/test/api/endpoint");
    }

    @Test
        /*
         * TODO - This test is a placeholder - remove it, once first specific exception defined in
         * application layer
         */
    void handleApplicationException() {
        // given
        ApplicationException exception = new ApplicationException();
        Mockito
            .when(messageSource.getMessage(
                "message.common.rest.error.application_exception_placeholder", null,
                Locale.ENGLISH))
            .thenReturn("Public error message");

        // when
        response = handler.handleApplicationException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 400);
        assertEquals(response.getBody().getMessage(), "Public error message");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
        /*
         * TODO - This test is a placeholder - remove it, once first specific exception defined in
         * application layer
         */
    void handleApplicationExceptionWithoutMessage() {
        // given
        ApplicationException exception = new ApplicationException();
        Mockito
            .when(messageSource.getMessage(
                "message.common.rest.error.application_exception_placeholder", null,
                Locale.ENGLISH))
            .thenThrow(new NoSuchMessageException("No message"));

        // when
        response = handler.handleApplicationException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 400);
        assertEquals(response.getBody().getMessage(), "No message available");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
        /*
         * TODO - This test is a placeholder - remove it, once first specific exception defined in
         * infrastructure layer
         */
    void handleInfrastructureException() {
        // given
        InfrastructureException exception = new InfrastructureException();
        Mockito
            .when(messageSource.getMessage(
                "message.common.rest.error.infrastructure_exception_placeholder", null,
                Locale.ENGLISH))
            .thenReturn("Public error message");

        // when
        response = handler.handleInfrastructureException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 400);
        assertEquals(response.getBody().getMessage(), "Public error message");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
        /*
         * TODO - This test is a placeholder - remove it, once first specific exception defined in
         * infrastructure layer
         */
    void handleInfrastructureExceptionWithoutMessage() {
        // given
        InfrastructureException exception = new InfrastructureException();
        Mockito
            .when(messageSource.getMessage(
                "message.common.rest.error.infrastructure_exception_placeholder", null,
                Locale.ENGLISH))
            .thenThrow(new NoSuchMessageException("No message"));

        // when
        response = handler.handleInfrastructureException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 400);
        assertEquals(response.getBody().getMessage(), "No message available");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
        /*
         * TODO - This test is a placeholder - remove it, once first specific exception defined in
         *  domain layer
         */
    void handleDomainException() {
        // given
        DomainException exception = new DomainException();
        Mockito
            .when(messageSource.getMessage("message.common.rest.error.domain_exception_placeholder",
                null, Locale.ENGLISH)).thenReturn("Public error message");

        // when
        response = handler.handleDomainException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 400);
        assertEquals(response.getBody().getMessage(), "Public error message");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
        /*
         * TODO - This test is a placeholder - remove it, once first specific exception defined in
         * domain layer
         */
    void handleDomainExceptionWithoutMessage() {
        // given
        DomainException exception = new DomainException();
        Mockito
            .when(messageSource.getMessage("message.common.rest.error.domain_exception_placeholder",
                null, Locale.ENGLISH)).thenThrow(new NoSuchMessageException("No message"));

        // when
        response = handler.handleDomainException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 400);
        assertEquals(response.getBody().getMessage(), "No message available");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }
}