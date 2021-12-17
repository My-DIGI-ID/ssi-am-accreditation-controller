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
import com.bka.ssi.controller.accreditation.company.application.exceptions.AlreadyExistsException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthenticatedException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthorizedException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;

/**
 * The type Common exceptions handler.
 */
@RestControllerAdvice(basePackages = {"com.bka.ssi.controller.accreditation.company.api"})
@Priority(2)
public class CommonExceptionsHandler {

    private final RestErrorResponseFactory restErrorResponseFactory;
    private final Logger logger;

    /**
     * Instantiates a new Common exceptions handler.
     *
     * @param restErrorResponseFactory the rest error response factory
     * @param logger                   the logger
     */
    public CommonExceptionsHandler(RestErrorResponseFactory restErrorResponseFactory,
        Logger logger) {
        this.restErrorResponseFactory = restErrorResponseFactory;
        this.logger = logger;
    }

    /**
     * Handle unsupported operation exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ResponseEntity<RestErrorResponse> handleUnsupportedOperationException(
        UnsupportedOperationException ex, HttpServletRequest request) {

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.not_implemented_exception_placeholder",
            HttpStatus.NOT_IMPLEMENTED, request);

        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * Handle unauthorized exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<RestErrorResponse> handleUnauthorizedException(UnauthorizedException ex,
        HttpServletRequest request) {

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.unauthorized_exception_placeholder", HttpStatus.FORBIDDEN,
            request);

        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    /**
     * Handle unauthenticated exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<RestErrorResponse> handleUnauthenticatedException(
        UnauthenticatedException ex, HttpServletRequest request) {

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.unauthenticated_exception_placeholder",
            HttpStatus.UNAUTHORIZED, request);

        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle unknown exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<RestErrorResponse> handleUnknownException(Exception ex,
        HttpServletRequest request) {

        RestErrorResponse response =
            restErrorResponseFactory
                .create("message.common.rest.error.unknown_exception_placeholder",
                    HttpStatus.INTERNAL_SERVER_ERROR, request);

        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle not found exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorResponse> handleNotFoundException(NotFoundException ex,
        HttpServletRequest request) {

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.not_found_exception_placeholder",
            HttpStatus.NOT_FOUND, request);

        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle already exists exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<RestErrorResponse> handleAlreadyExistsException(AlreadyExistsException ex,
        HttpServletRequest request) {

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.already_exists_exception_placeholder",
            HttpStatus.CONFLICT, request);

        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Handle illegal fallback exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class,
        IllegalAccessException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<RestErrorResponse> handleIllegalFallbackException(Exception ex,
        HttpServletRequest request) {

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.illegal_fallback_exception_placeholder",
            HttpStatus.CONFLICT, request);

        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
