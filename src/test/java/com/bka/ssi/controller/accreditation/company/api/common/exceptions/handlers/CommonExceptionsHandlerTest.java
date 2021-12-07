package com.bka.ssi.controller.accreditation.company.api.common.exceptions.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.factories.RestErrorResponseFactory;
import com.bka.ssi.controller.accreditation.company.application.exceptions.AlreadyExistsException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthenticatedException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthorizedException;
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

class CommonExceptionsHandlerTest {

    private final Logger logger = LoggerFactory.getLogger(CommonExceptionsHandlerTest.class);
    private MockHttpServletRequest request;
    private ResponseEntity<RestErrorResponse> response;
    private MessageSource messageSource;
    private CommonExceptionsHandler handler;

    @BeforeEach
    void setUp() {
        messageSource = Mockito.mock(MessageSource.class);
        RestErrorResponseFactory factory = new RestErrorResponseFactory(messageSource);
        handler = new CommonExceptionsHandler(factory, logger);
        request = new MockHttpServletRequest(null, "/test/api/endpoint");
    }

    @Test
    void handleUnsupportedOperationException() {
        // given
        UnsupportedOperationException exception = new UnsupportedOperationException();
        Mockito.when(messageSource.getMessage(
            "message.common.rest.error.not_implemented_exception_placeholder", null,
            Locale.ENGLISH))
            .thenReturn("Public error message");

        // when
        response = handler.handleUnsupportedOperationException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.NOT_IMPLEMENTED);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 501);
        assertEquals(response.getBody().getMessage(), "Public error message");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleUnsupportedOperationExceptionWithoutMessage() {
        // given
        UnsupportedOperationException exception = new UnsupportedOperationException();
        Mockito.when(messageSource.getMessage(
            "message.common.rest.error.not_implemented_exception_placeholder", null,
            Locale.ENGLISH))
            .thenThrow(new NoSuchMessageException("No message"));

        // when
        response = handler.handleUnsupportedOperationException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.NOT_IMPLEMENTED);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 501);
        assertEquals(response.getBody().getMessage(), "No message available");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleUnauthorizedException() {
        // given
        UnauthorizedException exception = new UnauthorizedException();
        Mockito
            .when(messageSource.getMessage(
                "message.common.rest.error.unauthorized_exception_placeholder", null,
                Locale.ENGLISH))
            .thenReturn("Public error message");

        // when
        response = handler.handleUnauthorizedException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 403);
        assertEquals(response.getBody().getMessage(), "Public error message");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleUnauthorizedExceptionWithoutMessage() {
        // given
        UnauthorizedException exception = new UnauthorizedException();
        Mockito
            .when(messageSource.getMessage(
                "message.common.rest.error.unauthorized_exception_placeholder", null,
                Locale.ENGLISH))
            .thenThrow(new NoSuchMessageException("No message"));

        // when
        response = handler.handleUnauthorizedException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 403);
        assertEquals(response.getBody().getMessage(), "No message available");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleUnauthenticatedException() {
        // given
        UnauthenticatedException exception = new UnauthenticatedException();
        Mockito
            .when(messageSource.getMessage(
                "message.common.rest.error.unauthenticated_exception_placeholder", null,
                Locale.ENGLISH))
            .thenReturn("Public error message");

        // when
        response = handler.handleUnauthenticatedException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 401);
        assertEquals(response.getBody().getMessage(), "Public error message");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleUnauthenticatedExceptionWithoutMessage() {
        // given
        UnauthenticatedException exception = new UnauthenticatedException();
        Mockito
            .when(messageSource.getMessage(
                "message.common.rest.error.unauthenticated_exception_placeholder", null,
                Locale.ENGLISH))
            .thenThrow(new NoSuchMessageException("No message"));

        // when
        response = handler.handleUnauthenticatedException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 401);
        assertEquals(response.getBody().getMessage(), "No message available");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleUnknownException() {
        // given
        Exception exception = new Exception();
        Mockito.when(
            messageSource.getMessage("message.common.rest.error.unknown_exception_placeholder",
                null, Locale.ENGLISH)).thenReturn("Public error message");

        // when
        response = handler.handleUnknownException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 500);
        assertEquals(response.getBody().getMessage(), "Public error message");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleUnknownExceptionWithoutMessage() {
        // given
        Exception exception = new Exception();
        Mockito.when(
            messageSource.getMessage("message.common.rest.error.unknown_exception_placeholder",
                null, Locale.ENGLISH)).thenThrow(new NoSuchMessageException("No message"));

        // when
        response = handler.handleUnknownException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 500);
        assertEquals(response.getBody().getMessage(), "No message available");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleNotFoundException() {
        // given
        NotFoundException exception = new NotFoundException();
        Mockito.when(
            messageSource
                .getMessage("message.common.rest.error.not_found_exception_placeholder",
                    null, Locale.ENGLISH)).thenReturn("Public error message");

        // when
        response = handler.handleNotFoundException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 404);
        assertEquals(response.getBody().getMessage(), "Public error message");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleNotFoundExceptionWithoutMessage() {
        // given
        NotFoundException exception = new NotFoundException();
        Mockito.when(
            messageSource
                .getMessage("message.common.rest.error.not_found_exception_placeholder",
                    null, Locale.ENGLISH)).thenThrow(new NoSuchMessageException("No message"));

        // when
        response = handler.handleNotFoundException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 404);
        assertEquals(response.getBody().getMessage(), "No message available");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleAlreadyExistsException() {
        // given
        AlreadyExistsException exception = new AlreadyExistsException();
        Mockito.when(
            messageSource
                .getMessage("message.common.rest.error.already_exists_exception_placeholder",
                    null, Locale.ENGLISH)).thenReturn("Public error message");

        // when
        response = handler.handleAlreadyExistsException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 409);
        assertEquals(response.getBody().getMessage(), "Public error message");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleAlreadyExistsExceptionWithoutMessage() {
        // given
        AlreadyExistsException exception = new AlreadyExistsException();
        Mockito.when(
            messageSource
                .getMessage("message.common.rest.error.already_exists_exception_placeholder",
                    null, Locale.ENGLISH)).thenThrow(new NoSuchMessageException("No message"));

        // when
        response = handler.handleAlreadyExistsException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 409);
        assertEquals(response.getBody().getMessage(), "No message available");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleIllegalArgumentExceptionException() {
        // given
        IllegalArgumentException exception = new IllegalArgumentException();
        Mockito.when(
            messageSource
                .getMessage("message.common.rest.error.illegal_fallback_exception_placeholder",
                    null, Locale.ENGLISH)).thenReturn("Public error message");

        // when
        response = handler.handleIllegalFallbackException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 409);
        assertEquals(response.getBody().getMessage(), "Public error message");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleIllegalArgumentExceptionWithoutMessage() {
        // given
        IllegalArgumentException exception = new IllegalArgumentException();
        Mockito.when(
            messageSource
                .getMessage("message.common.rest.error.illegal_fallback_exception_placeholder",
                    null, Locale.ENGLISH)).thenThrow(new NoSuchMessageException("No message"));

        // when
        response = handler.handleIllegalFallbackException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 409);
        assertEquals(response.getBody().getMessage(), "No message available");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }
}
