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
import com.bka.ssi.controller.accreditation.company.application.security.authorization.AuthorizationService;
import com.bka.ssi.controller.accreditation.company.application.security.utilities.BearerTokenParser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * The type Sso security facade.
 */
@Aspect
@Component
public class SSOSecurityFacade {

    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;
    private final BearerTokenParser bearerTokenParser;
    private final Logger logger;

    /**
     * Instantiates a new Sso security facade.
     *
     * @param authenticationService the authentication service
     * @param authorizationService  the authorization service
     * @param bearerTokenParser     the bearer token parser
     * @param logger                the logger
     */
    public SSOSecurityFacade(
        AuthenticationService authenticationService,
        AuthorizationService authorizationService,
        BearerTokenParser bearerTokenParser, Logger logger) {
        this.authenticationService = authenticationService;
        this.authorizationService = authorizationService;
        this.bearerTokenParser = bearerTokenParser;
        this.logger = logger;
    }

    /**
     * Protected transaction.
     *
     * @param joinPoint the join point
     * @throws Exception the exception
     */
    @Before("@annotation(com.bka.ssi.controller.accreditation.company.application.security.facade.SSOProtectedTransaction)")
    public void protectedTransaction(JoinPoint joinPoint) throws Exception {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String scope = signature
            .getMethod()
            .getAnnotation(SSOProtectedTransaction.class)
            .scope();

        String resource = signature
            .getMethod()
            .getAnnotation(SSOProtectedTransaction.class)
            .resource();

        String conditionName = resource + "#" + scope;

        String token = this.bearerTokenParser.getToken();
        this.authenticationService.verifySSOToken(token);
        this.authorizationService.verifySSOPermission(token, conditionName);
    }
}
