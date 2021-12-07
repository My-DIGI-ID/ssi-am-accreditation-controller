package com.bka.ssi.controller.accreditation.company.api.v2.rest.exceptions.handlers.accreditations;

import com.bka.ssi.controller.accreditation.company.aop.logging.LoggingUtility;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.factories.RestErrorResponseFactory;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.controllers.accreditations.GuestAccreditationController;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidGuestInitialStateException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;

@Priority(1)
@RestControllerAdvice(basePackageClasses = GuestAccreditationController.class)
public class GuestAccreditationControllerExceptionsHandler {

    private final RestErrorResponseFactory restErrorResponseFactory;
    private final Logger logger;

    public GuestAccreditationControllerExceptionsHandler(
        RestErrorResponseFactory restErrorResponseFactory,
        Logger logger) {

        this.restErrorResponseFactory = restErrorResponseFactory;
        this.logger = logger;
    }

    @ExceptionHandler(InvalidGuestInitialStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<RestErrorResponse> handleInvalidGuestAccreditationStateException(
        Exception ex, HttpServletRequest request) {

        /* ToDo - this handler should handle any kind of guest accreditation state exceptions,
         *   refactor guest accreditation state exceptions to give a meaningful response and
         *      logging output, otherwise messageKey needs to fine-granular
         */
        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.guest_accreditation_state_exception_placeholder",
            HttpStatus.CONFLICT, request);

        /* ToDo - keep default fallback debug log until guest accreditation state exceptions are
         *   refactored for meaningful logging output
         */
        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
