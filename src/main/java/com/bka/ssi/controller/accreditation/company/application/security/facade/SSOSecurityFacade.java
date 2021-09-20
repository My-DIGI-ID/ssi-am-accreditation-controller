package com.bka.ssi.controller.accreditation.company.application.security.facade;

import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthenticatedException;
import com.bka.ssi.controller.accreditation.company.application.security.authentication.AuthenticationService;
import com.bka.ssi.controller.accreditation.company.application.security.authorization.AuthorizationService;
import com.bka.ssi.controller.accreditation.company.application.security.utilities.BearerTokenParser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SSOSecurityFacade {

    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;
    private final BearerTokenParser bearerTokenParser;
    private final Logger logger;

    public SSOSecurityFacade(
        AuthenticationService authenticationService,
        AuthorizationService authorizationService,
        BearerTokenParser bearerTokenParser, Logger logger) {
        this.authenticationService = authenticationService;
        this.authorizationService = authorizationService;
        this.bearerTokenParser = bearerTokenParser;
        this.logger = logger;
    }

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

        String conditionName = "res:" + resource + "#" + scope;

        String token = bearerTokenParser.getToken();

        if (token == null) {
            logger.debug("No token provided");
            throw new UnauthenticatedException();
        }

        this.authenticationService.verifySSOToken(token);
        this.authorizationService.verifySSOPermission(token, conditionName);
    }
}
