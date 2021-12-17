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

/**
 * The type Guest accreditation controller exceptions handler.
 */
@Priority(1)
@RestControllerAdvice(basePackageClasses = GuestAccreditationController.class)
public class GuestAccreditationControllerExceptionsHandler {

    private final RestErrorResponseFactory restErrorResponseFactory;
    private final Logger logger;

    /**
     * Instantiates a new Guest accreditation controller exceptions handler.
     *
     * @param restErrorResponseFactory the rest error response factory
     * @param logger                   the logger
     */
    public GuestAccreditationControllerExceptionsHandler(
        RestErrorResponseFactory restErrorResponseFactory,
        Logger logger) {

        this.restErrorResponseFactory = restErrorResponseFactory;
        this.logger = logger;
    }

    /**
     * Handle invalid guest accreditation state exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
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
