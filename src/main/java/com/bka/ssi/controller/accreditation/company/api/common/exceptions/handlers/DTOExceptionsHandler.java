package com.bka.ssi.controller.accreditation.company.api.common.exceptions.handlers;

import com.bka.ssi.controller.accreditation.company.api.common.exceptions.LogOutput;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.factories.RestErrorResponseFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice(basePackages = {"com.bka.ssi.controller.accreditation.company.api"})
@Priority(0)
public class DTOExceptionsHandler {

    private final RestErrorResponseFactory restErrorResponseFactory;
    private final Logger logger;

    public DTOExceptionsHandler(RestErrorResponseFactory restErrorResponseFactory, Logger logger) {
        this.restErrorResponseFactory = restErrorResponseFactory;
        this.logger = logger;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException ex, HttpServletRequest request) {
        
        RestErrorResponse response = restErrorResponseFactory
            .create(ex.getBindingResult().getFieldErrors(), HttpStatus.BAD_REQUEST, request);
        logger.error(new LogOutput(response).toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponse> handleConstraintViolationException(
        ConstraintViolationException ex, HttpServletRequest request) {

        /* ToDo - care about ex.getMessage, validation might contain sensible information? for
            now ex.getMessage consists of constraintViolation.getMessage() */
        /* ToDo - make use of restErrorResponseFactory */
        RestErrorResponse response = new RestErrorResponse(HttpStatus.BAD_REQUEST,
            ex.getMessage(), request.getRequestURI());
        logger.error(new LogOutput(response).toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
