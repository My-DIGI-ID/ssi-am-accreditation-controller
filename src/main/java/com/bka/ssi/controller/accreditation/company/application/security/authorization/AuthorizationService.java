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

package com.bka.ssi.controller.accreditation.company.application.security.authorization;

import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthorizedException;
import com.bka.ssi.controller.accreditation.company.application.security.SSOClient;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * The type Authorization service.
 */
@Service
public class AuthorizationService {

    private final SSOClient ssoClient;
    private final Logger logger;

    /**
     * Instantiates a new Authorization service.
     *
     * @param ssoClient the sso client
     * @param logger    the logger
     */
    public AuthorizationService(SSOClient ssoClient, Logger logger) {
        this.ssoClient = ssoClient;
        this.logger = logger;
    }

    /**
     * Verify sso permission boolean.
     *
     * @param token       the token
     * @param transaction the transaction
     * @return the boolean
     * @throws Exception the exception
     */
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
