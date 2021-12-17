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

package com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.factories;

import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * The type Rest error response factory.
 */
@Component
public class RestErrorResponseFactory {

    private final MessageSource messageSource;

    /**
     * Instantiates a new Rest error response factory.
     *
     * @param messageSource the message source
     */
    public RestErrorResponseFactory(
        MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Create rest error response.
     *
     * @param messageKey the message key
     * @param httpStatus the http status
     * @param request    the request
     * @return the rest error response
     */
    public RestErrorResponse create(String messageKey, HttpStatus httpStatus,
        HttpServletRequest request) {
        String errorMessage;

        //Todo: Use i18nMessageBuilder in BKAACMGT-172
        try {
            errorMessage = messageSource.getMessage(messageKey, null, request.getLocale());
        } catch (Exception e) {
            errorMessage = "No message available";
        }

        RestErrorResponse response =
            new RestErrorResponse(httpStatus, errorMessage, request.getRequestURI());

        return response;
    }

    /**
     * Create rest error response.
     *
     * @param fieldErrors the field errors
     * @param httpStatus  the http status
     * @param request     the request
     * @return the rest error response
     */
    public RestErrorResponse create(List<FieldError> fieldErrors, HttpStatus httpStatus,
        HttpServletRequest request) {
        String errorMessage;

        try {
            Map<String, String> errors = new HashMap<>();
            fieldErrors.forEach((error) -> {
                String fieldName = error.getField();
                String message = error.getDefaultMessage();
                errors.put(fieldName, message);
            });

            /*
             * Warning! This case is the only exception where we pass error messages directly to the
             * client Currently messages are taken in english, TODO - consider changing approach in
             * BKAACMGT-114
             */
            errorMessage = errors.toString();
        } catch (Exception e) {
            errorMessage = "No message available";
        }

        RestErrorResponse response =
            new RestErrorResponse(httpStatus, errorMessage, request.getRequestURI());

        return response;
    }
}
