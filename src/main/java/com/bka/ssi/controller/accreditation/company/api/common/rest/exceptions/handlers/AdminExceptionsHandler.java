package com.bka.ssi.controller.accreditation.company.api.common.rest.exceptions.handlers;

import com.bka.ssi.controller.accreditation.company.api.common.exceptions.LogOutput;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.factories.RestErrorResponseFactory;
import com.bka.ssi.controller.accreditation.company.api.common.rest.controllers.AdminController;
import com.bka.ssi.controller.accreditation.company.application.exceptions.JsonParseException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;

@Priority(1)
@RestControllerAdvice(basePackageClasses = AdminController.class)
public class AdminExceptionsHandler {

    private final RestErrorResponseFactory restErrorResponseFactory;
    private final Logger logger;

    public AdminExceptionsHandler(
        RestErrorResponseFactory restErrorResponseFactory,
        Logger logger) {

        this.restErrorResponseFactory = restErrorResponseFactory;
        this.logger = logger;
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<RestErrorResponse> handleJsonParseException(JsonParseException ex,
        HttpServletRequest request) {

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.json_parse_exception_placeholder",
            HttpStatus.INTERNAL_SERVER_ERROR, request);

        logger.debug(ex.getMessage());
        logger.error(new LogOutput(response).toString());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
