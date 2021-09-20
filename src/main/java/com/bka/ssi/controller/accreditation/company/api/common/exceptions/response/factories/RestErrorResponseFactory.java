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

@Component
public class RestErrorResponseFactory {

    private final MessageSource messageSource;

    public RestErrorResponseFactory(
        MessageSource messageSource) {
        this.messageSource = messageSource;
    }

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
