/*
 * Copyright 2021 Bundesrepublik Deutschland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bka.ssi.controller.accreditation.company.api.common.exceptions.handlers;

import com.bka.ssi.controller.accreditation.company.aop.logging.LoggingUtility;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.factories.RestErrorResponseFactory;
import com.bka.ssi.controller.accreditation.company.application.exceptions.BundleConstraintViolationExceptionsException;
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

/**
 * The type Dto exceptions handler.
 */
@RestControllerAdvice(basePackages = {"com.bka.ssi.controller.accreditation.company.api"})
@Priority(0)
public class DTOExceptionsHandler {

    private final RestErrorResponseFactory restErrorResponseFactory;
    private final Logger logger;

    /**
     * Instantiates a new Dto exceptions handler.
     *
     * @param restErrorResponseFactory the rest error response factory
     * @param logger                   the logger
     */
    public DTOExceptionsHandler(RestErrorResponseFactory restErrorResponseFactory, Logger logger) {
        this.restErrorResponseFactory = restErrorResponseFactory;
        this.logger = logger;
    }

    /**
     * Handle method argument not valid exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException ex, HttpServletRequest request) {

        /* ToDo - care about ex.getBindingResult().getFieldErrors() */
        RestErrorResponse response = restErrorResponseFactory
            .create(ex.getBindingResult().getFieldErrors(), HttpStatus.BAD_REQUEST, request);

        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle constraint violation exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({ConstraintViolationException.class,
        BundleConstraintViolationExceptionsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponse> handleConstraintViolationException(
        Exception ex, HttpServletRequest request) {

        /* ToDo - care about ex.getMessage, validation might contain sensible information? for
            now ex.getMessage consists of constraintViolation.getMessage() */
        /* ToDo - make use of restErrorResponseFactory */
        RestErrorResponse response = new RestErrorResponse(HttpStatus.BAD_REQUEST,
            ex.getMessage(), request.getRequestURI());

        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
