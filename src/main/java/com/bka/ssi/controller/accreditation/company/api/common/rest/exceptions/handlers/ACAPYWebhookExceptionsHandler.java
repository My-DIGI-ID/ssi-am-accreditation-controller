package com.bka.ssi.controller.accreditation.company.api.common.rest.exceptions.handlers;

import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.factories.RestErrorResponseFactory;
import com.bka.ssi.controller.accreditation.company.api.common.rest.controllers.ACAPYWebhookController;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Priority;

@Priority(1)
@RestControllerAdvice(basePackageClasses = ACAPYWebhookController.class)
public class ACAPYWebhookExceptionsHandler {

    private final RestErrorResponseFactory restErrorResponseFactory;
    private final Logger logger;

    public ACAPYWebhookExceptionsHandler(
        RestErrorResponseFactory restErrorResponseFactory,
        Logger logger) {

        this.restErrorResponseFactory = restErrorResponseFactory;
        this.logger = logger;
    }
}
