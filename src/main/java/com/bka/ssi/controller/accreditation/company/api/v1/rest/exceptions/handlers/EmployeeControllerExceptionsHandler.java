package com.bka.ssi.controller.accreditation.company.api.v1.rest.exceptions.handlers;

import com.bka.ssi.controller.accreditation.company.api.common.exceptions.LogOutput;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.factories.RestErrorResponseFactory;
import com.bka.ssi.controller.accreditation.company.api.v1.rest.controllers.EmployeeController;
import com.bka.ssi.controller.accreditation.company.application.exceptions.ApplicationException;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.DomainException;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.exceptions.InfrastructureException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;

@Component("employeeControllerExceptionsHandlerV1")
@Priority(1)
@RestControllerAdvice(basePackageClasses = EmployeeController.class)
public class EmployeeControllerExceptionsHandler {

    private final RestErrorResponseFactory restErrorResponseFactory;
    private final Logger logger;

    public EmployeeControllerExceptionsHandler(RestErrorResponseFactory restErrorResponseFactory,
        Logger logger) {
        this.restErrorResponseFactory = restErrorResponseFactory;
        this.logger = logger;
    }

    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    /*
     * TODO - This handler is a placeholder - remove, once first specific exception defined in
     * application layer
     */
    public ResponseEntity<RestErrorResponse> handleApplicationException(ApplicationException ex,
        HttpServletRequest request) {

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.application_exception_placeholder",
            HttpStatus.BAD_REQUEST,
            request);
        logger.error(new LogOutput(response).toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InfrastructureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    /*
     * TODO - This handler is a placeholder - remove, once first specific exception defined in
     * infrastructure layer
     */
    public ResponseEntity<RestErrorResponse> handleInfrastructureException(
        InfrastructureException ex,
        HttpServletRequest request) {

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.infrastructure_exception_placeholder",
            HttpStatus.BAD_REQUEST,
            request);
        logger.error(new LogOutput(response).toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    /*
     * TODO - This handler is a placeholder - remove it, once first specific exception defined in
     * domain layer
     */
    public ResponseEntity<RestErrorResponse> handleDomainException(DomainException ex,
        HttpServletRequest request) {

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.domain_exception_placeholder",
            HttpStatus.BAD_REQUEST,
            request);
        logger.error(new LogOutput(response).toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
