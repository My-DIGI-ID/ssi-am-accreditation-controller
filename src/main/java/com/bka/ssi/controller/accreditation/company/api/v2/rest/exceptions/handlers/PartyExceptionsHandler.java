package com.bka.ssi.controller.accreditation.company.api.v2.rest.exceptions.handlers;

import com.bka.ssi.controller.accreditation.company.api.common.exceptions.LogOutput;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.factories.RestErrorResponseFactory;
import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidCsvFileException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidCsvFileFormatException;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidGuestValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.UpdatingPartyWithoutIdentityException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;

@Priority(1)
@RestControllerAdvice(basePackages = {
    "com.bka.ssi.controller.accreditation.company.api.v2.rest.controllers.parties"})
public class PartyExceptionsHandler {

    private final RestErrorResponseFactory restErrorResponseFactory;
    private final Logger logger;

    public PartyExceptionsHandler(
        RestErrorResponseFactory restErrorResponseFactory, Logger logger) {
        this.restErrorResponseFactory = restErrorResponseFactory;
        this.logger = logger;
    }

    @ExceptionHandler(UpdatingPartyWithoutIdentityException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<RestErrorResponse> handleInvalidPartyStateException(Exception ex,
        HttpServletRequest request) {

        /* ToDo - this handler should handle any kind of party state exceptions, refactor
         *    party state exceptions to give a meaningful response and logging output
         */

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.party_state_exception_placeholder",
            HttpStatus.CONFLICT, request);

        /* ToDo - keep default fallback debug log until party state exceptions are
         *   refactored for meaningful logging output
         */
        logger.debug(ex.getMessage());
        logger.error(new LogOutput(response).toString());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /* ToDo - Make it to a endpoint/method specific exception handler */
    @ExceptionHandler({InvalidCsvFileFormatException.class, InvalidCsvFileException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponse> handleInvalidCsvException(
        Exception ex, HttpServletRequest request) {

        /* ToDo - this handler should handle any kind of party related csv exceptions,
         *   refactor csv exceptions to give a meaningful response and logging output
         */

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.invalid_csv_exception_placeholder",
            HttpStatus.BAD_REQUEST, request);

        /* ToDo - keep default fallback debug log until csv exceptions are
         *   refactored for meaningful logging output
         */
        logger.debug(ex.getMessage());
        logger.error(new LogOutput(response).toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidGuestValidityTimeframeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponse> handleInvalidGuestValidityTimeframeException(
        Exception ex, HttpServletRequest request) {

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.guest_validity_timeframe_exception_placeholder",
            HttpStatus.BAD_REQUEST, request);

        /* ToDo - keep default fallback debug log until guest validity exceptions are
         *   refactored for meaningful logging output
         */
        logger.debug(ex.getMessage());
        logger.error(new LogOutput(response).toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
