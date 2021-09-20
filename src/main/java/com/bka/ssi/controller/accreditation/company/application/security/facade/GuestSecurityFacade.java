package com.bka.ssi.controller.accreditation.company.application.security.facade;

import com.bka.ssi.controller.accreditation.company.application.security.authentication.AuthenticationService;
import com.bka.ssi.controller.accreditation.company.application.security.utilities.BearerTokenParser;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GuestSecurityFacade {

    private final AuthenticationService authenticationService;
    private final BearerTokenParser bearerTokenParser;
    private final Logger logger;

    public GuestSecurityFacade(
        AuthenticationService authenticationService,
        BearerTokenParser bearerTokenParser, Logger logger) {
        this.authenticationService = authenticationService;
        this.bearerTokenParser = bearerTokenParser;
        this.logger = logger;
    }

    @Before("@annotation(com.bka.ssi.controller.accreditation.company.application.security.facade.GuestProtectedTransaction)")
    public void protectedTransaction() throws Exception {
        String passedToken = bearerTokenParser.getToken();
        authenticationService.verifyGuestAccessToken(passedToken);
    }

}
