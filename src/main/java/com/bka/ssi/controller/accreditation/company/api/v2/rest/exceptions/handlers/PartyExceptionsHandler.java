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

package com.bka.ssi.controller.accreditation.company.api.v2.rest.exceptions.handlers;

import com.bka.ssi.controller.accreditation.company.aop.logging.LoggingUtility;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.factories.RestErrorResponseFactory;
import com.bka.ssi.controller.accreditation.company.application.exceptions.EmptyFileException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidAccreditationStatusForPartyException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidCsvFileException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidCsvFileFormatException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidPartyOperationException;
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

/**
 * The type Party exceptions handler.
 */
@Priority(1)
@RestControllerAdvice(basePackages = {
    "com.bka.ssi.controller.accreditation.company.api.v2.rest.controllers.parties"})
public class PartyExceptionsHandler {

    private final RestErrorResponseFactory restErrorResponseFactory;
    private final Logger logger;

    /**
     * Instantiates a new Party exceptions handler.
     *
     * @param restErrorResponseFactory the rest error response factory
     * @param logger                   the logger
     */
    public PartyExceptionsHandler(
        RestErrorResponseFactory restErrorResponseFactory, Logger logger) {
        this.restErrorResponseFactory = restErrorResponseFactory;
        this.logger = logger;
    }

    /**
     * Handle invalid party state exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
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
        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Handle invalid csv exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
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
        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle invalid guest validity timeframe exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
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
        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle invalid accreditation status for party exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(InvalidAccreditationStatusForPartyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<RestErrorResponse> handleInvalidAccreditationStatusForPartyException(
        Exception ex, HttpServletRequest request) {

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.invalid_accreditation_status_for_party_placeholder",
            HttpStatus.CONFLICT, request);

        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Handle invalid party operation response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(InvalidPartyOperationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<RestErrorResponse> handleInvalidPartyOperation(Exception ex,
        HttpServletRequest request) {

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.invalid_operation_for_party_placeholder",
            HttpStatus.FORBIDDEN, request
        );

        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    /**
     * Handle empty file exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(EmptyFileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponse> handleEmptyFileException(Exception ex,
        HttpServletRequest request) {

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.empty_file_exception_placeholder",
            HttpStatus.BAD_REQUEST, request);

        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
