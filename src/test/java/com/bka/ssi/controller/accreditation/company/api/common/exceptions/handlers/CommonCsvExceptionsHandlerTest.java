package com.bka.ssi.controller.accreditation.company.api.common.exceptions.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.factories.RestErrorResponseFactory;
import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidCsvFileException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidCsvFileFormatException;
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

class CommonCsvExceptionsHandlerTest {

    private final Logger logger = LoggerFactory.getLogger(CommonCsvExceptionsHandlerTest.class);
    private MockHttpServletRequest request;
    private ResponseEntity<RestErrorResponse> response;
    private MessageSource messageSource;
    private CommonCsvExceptionsHandler handler;

    @BeforeEach
    void setUp() {
        messageSource = Mockito.mock(MessageSource.class);
        RestErrorResponseFactory factory = new RestErrorResponseFactory(messageSource);
        handler = new CommonCsvExceptionsHandler(factory, logger);
        request = new MockHttpServletRequest(null, "/test/api/endpoint");
    }

    @Test
    void handleInvalidCsvFileException() {
        // given
        InvalidCsvFileException exception = new InvalidCsvFileException();
        Mockito.when(messageSource.getMessage(
            "message.common.rest.error.invalid_csv_file_exception_placeholder", null,
            Locale.ENGLISH))
            .thenReturn("Public error message");

        // when
        response = handler.handleInvalidCsvFileException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 400);
        assertEquals(response.getBody().getMessage(), "Public error message");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleInvalidCsvFileExceptionWithoutMessage() {
        // given
        InvalidCsvFileException exception = new InvalidCsvFileException();
        Mockito.when(messageSource.getMessage(
            "message.common.rest.error.invalid_csv_file_exception_placeholder", null,
            Locale.ENGLISH))
            .thenThrow(new NoSuchMessageException("No message"));

        // when
        response = handler.handleInvalidCsvFileException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 400);
        assertEquals(response.getBody().getMessage(), "No message available");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleInvalidCsvFileFormatException() {
        // given
        InvalidCsvFileFormatException exception = new InvalidCsvFileFormatException();
        Mockito.when(messageSource.getMessage(
            "message.common.rest.error.invalid_csv_file_format_exception_placeholder", null,
            Locale.ENGLISH))
            .thenReturn("Public error message");

        // when
        response = handler.handleInvalidCsvFileFormatException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 400);
        assertEquals(response.getBody().getMessage(), "Public error message");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }

    @Test
    void handleInvalidCsvFileFormatExceptionWithoutMessage() {
        // given
        InvalidCsvFileFormatException exception = new InvalidCsvFileFormatException();
        Mockito.when(messageSource.getMessage(
            "message.common.rest.error.invalid_csv_file_format_exception_placeholder", null,
            Locale.ENGLISH))
            .thenThrow(new NoSuchMessageException("No message"));

        // when
        response = handler.handleInvalidCsvFileFormatException(exception, request);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getPath(), "/test/api/endpoint");
        assertEquals(response.getBody().getStatus(), 400);
        assertEquals(response.getBody().getMessage(), "No message available");
        assertNotEquals(response.getBody().getTimestamp(), null);
    }
}
