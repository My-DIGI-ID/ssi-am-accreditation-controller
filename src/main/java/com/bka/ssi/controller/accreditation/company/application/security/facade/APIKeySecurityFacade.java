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

package com.bka.ssi.controller.accreditation.company.application.security.facade;

import com.bka.ssi.controller.accreditation.company.application.security.authentication.AuthenticationService;
import com.bka.ssi.controller.accreditation.company.application.security.utilities.ApiKeyParser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * The type Api key security facade.
 */
@Aspect
@Component
public class APIKeySecurityFacade {

    private final AuthenticationService authenticationService;
    private final ApiKeyParser apiKeyParser;
    private final Logger logger;

    /**
     * Instantiates a new Api key security facade.
     *
     * @param authenticationService the authentication service
     * @param apiKeyParser          the api key parser
     * @param logger                the logger
     */
    public APIKeySecurityFacade(
        AuthenticationService authenticationService,
        ApiKeyParser apiKeyParser,
        Logger logger) {
        this.authenticationService = authenticationService;
        this.apiKeyParser = apiKeyParser;
        this.logger = logger;
    }

    /**
     * Protected transaction.
     *
     * @param joinPoint the join point
     * @throws Exception the exception
     */
    @Before("@annotation(com.bka.ssi.controller.accreditation.company.application.security.facade.APIKeyProtectedTransaction)")
    public void protectedTransaction(JoinPoint joinPoint) throws Exception {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String id = signature
            .getMethod()
            .getAnnotation(APIKeyProtectedTransaction.class)
            .id();

        String apiKey = this.apiKeyParser.getApiKeyById(id);
        this.authenticationService.verifyApiKey(id, apiKey);
    }
}
