package com.bka.ssi.controller.accreditation.company.application.security.authorization;

import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthorizedException;
import com.bka.ssi.controller.accreditation.company.application.security.SSOClient;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final SSOClient ssoClient;
    private final Logger logger;

    public AuthorizationService(SSOClient ssoClient, Logger logger) {
        this.ssoClient = ssoClient;
        this.logger = logger;
    }

    public boolean verifySSOPermission(String token, String transaction) throws Exception {
        logger.debug("Verifying SSO token permission");

        if (token == null || token.equals("") || token.equals("null") ||
            transaction == null || transaction.equals("")) {
            logger.debug("SSO token is null or empty or transaction is null or empty");
            throw new UnauthorizedException();
        }

        Boolean isValid = ssoClient.verifyPermission(token, transaction);

        if (!isValid) {
            logger.debug("Operation not permitted for SSO token");
            throw new UnauthorizedException();
        }

        return true;
    }
}
