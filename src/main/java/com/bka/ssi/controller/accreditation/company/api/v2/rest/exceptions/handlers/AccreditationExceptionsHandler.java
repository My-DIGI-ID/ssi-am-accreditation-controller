package com.bka.ssi.controller.accreditation.company.api.v2.rest.exceptions.handlers;

import com.bka.ssi.controller.accreditation.company.aop.logging.LoggingUtility;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.factories.RestErrorResponseFactory;
import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidAccreditationStateChangeException;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidAccreditationInitialStateException;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
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
    "com.bka.ssi.controller.accreditation.company.api.v2.rest.controllers.accreditations"})
public class AccreditationExceptionsHandler {

    private final RestErrorResponseFactory restErrorResponseFactory;
    private final Logger logger;

    public AccreditationExceptionsHandler(
        RestErrorResponseFactory restErrorResponseFactory,
        Logger logger) {

        this.restErrorResponseFactory = restErrorResponseFactory;
        this.logger = logger;
    }

    @ExceptionHandler({InvalidAccreditationInitialStateException.class,
        InvalidAccreditationStateChangeException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<RestErrorResponse> handleInvalidAccreditationStateException(Exception ex,
        HttpServletRequest request) {

        /* ToDo - this handler should handle any kind of accreditation state exceptions, refactor
         *    accreditation state exceptions to give a meaningful response and logging output
         */
        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.accreditation_state_exception_placeholder",
            HttpStatus.CONFLICT, request);

        /* ToDo - keep default fallback debug log until accreditation state exceptions are
         *   refactored for meaningful logging output
         */
        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({InvalidValidityTimeframeException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<RestErrorResponse> handleInvalidValidityTimeframeException(Exception ex,
        HttpServletRequest request) {

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.validity_timeframe_exception_placeholder",
            HttpStatus.CONFLICT, request
        );

        /* ToDo - keep default fallback debug log until validity timeframe exceptions are
         *   refactored for meaningful logging output
         */
        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
