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

import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.factories.RestErrorResponseFactory;
import com.bka.ssi.controller.accreditation.company.api.common.rest.controllers.ACAPYWebhookController;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Priority;

/**
 * The type Acapy webhook exceptions handler.
 */
@Priority(1)
@RestControllerAdvice(basePackageClasses = ACAPYWebhookController.class)
public class ACAPYWebhookExceptionsHandler {

    private final RestErrorResponseFactory restErrorResponseFactory;
    private final Logger logger;

    /**
     * Instantiates a new Acapy webhook exceptions handler.
     *
     * @param restErrorResponseFactory the rest error response factory
     * @param logger                   the logger
     */
    public ACAPYWebhookExceptionsHandler(
        RestErrorResponseFactory restErrorResponseFactory,
        Logger logger) {

        this.restErrorResponseFactory = restErrorResponseFactory;
        this.logger = logger;
    }
}
