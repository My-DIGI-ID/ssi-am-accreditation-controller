package com.bka.ssi.controller.accreditation.company.application.security.facade;

import com.bka.ssi.controller.accreditation.company.application.security.authentication.AuthenticationService;
import com.bka.ssi.controller.accreditation.company.application.security.utilities.ApiKeyParser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class APIKeySecurityFacade {

    private final AuthenticationService authenticationService;
    private final ApiKeyParser apiKeyParser;
    private final Logger logger;

    public APIKeySecurityFacade(
        AuthenticationService authenticationService,
        ApiKeyParser apiKeyParser,
        Logger logger) {
        this.authenticationService = authenticationService;
        this.apiKeyParser = apiKeyParser;
        this.logger = logger;
    }

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
