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

package com.bka.ssi.controller.accreditation.company.api.common.rest.exceptions.handlers;

import com.bka.ssi.controller.accreditation.company.aop.logging.LoggingUtility;
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

/**
 * The type Admin exceptions handler.
 */
@Priority(1)
@RestControllerAdvice(basePackageClasses = AdminController.class)
public class AdminExceptionsHandler {

    private final RestErrorResponseFactory restErrorResponseFactory;
    private final Logger logger;

    /**
     * Instantiates a new Admin exceptions handler.
     *
     * @param restErrorResponseFactory the rest error response factory
     * @param logger                   the logger
     */
    public AdminExceptionsHandler(
        RestErrorResponseFactory restErrorResponseFactory,
        Logger logger) {

        this.restErrorResponseFactory = restErrorResponseFactory;
        this.logger = logger;
    }

    /**
     * Handle json parse exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    /* ToDo - Make it to a endpoint/method specific exception handler */
    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<RestErrorResponse> handleJsonParseException(JsonParseException ex,
        HttpServletRequest request) {

        RestErrorResponse response = restErrorResponseFactory.create(
            "message.common.rest.error.json_parse_exception_placeholder",
            HttpStatus.INTERNAL_SERVER_ERROR, request);

        LoggingUtility.logRestErrorResponse(logger, response, ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
