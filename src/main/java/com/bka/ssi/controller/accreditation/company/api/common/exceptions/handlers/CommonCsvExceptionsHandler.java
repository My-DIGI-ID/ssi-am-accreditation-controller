package com.bka.ssi.controller.accreditation.company.api.common.exceptions.handlers;

import com.bka.ssi.controller.accreditation.company.api.common.exceptions.LogOutput;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.factories.RestErrorResponseFactory;
import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidCsvFileException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidCsvFileFormatException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(basePackages = {"com.bka.ssi.controller.accreditation.company.api"})
@Priority(2)
public class CommonCsvExceptionsHandler {

    private final RestErrorResponseFactory restErrorResponseFactory;
    private final Logger logger;

    /* ToDo - Make it to a endpoint/method specific exception handler */
    public CommonCsvExceptionsHandler(RestErrorResponseFactory restErrorResponseFactory,
        Logger logger) {
        this.restErrorResponseFactory = restErrorResponseFactory;
        this.logger = logger;
    }

    @ExceptionHandler(InvalidCsvFileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponse> handleInvalidCsvFileException(
        InvalidCsvFileException ex, HttpServletRequest request) {

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.invalid_csv_file_exception_placeholder",
            HttpStatus.BAD_REQUEST, request);
        logger.error(new LogOutput(response).toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCsvFileFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponse> handleInvalidCsvFileFormatException(
        InvalidCsvFileFormatException ex, HttpServletRequest request) {

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.invalid_csv_file_format_exception_placeholder",
            HttpStatus.BAD_REQUEST, request);
        logger.error(new LogOutput(response).toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
