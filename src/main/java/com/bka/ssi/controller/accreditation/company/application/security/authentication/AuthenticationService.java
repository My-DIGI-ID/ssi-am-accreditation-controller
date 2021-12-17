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

package com.bka.ssi.controller.accreditation.company.application.security.authentication;

import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthenticatedException;
import com.bka.ssi.controller.accreditation.company.application.security.SSOClient;
import com.bka.ssi.controller.accreditation.company.application.security.authentication.dto.GuestToken;
import com.bka.ssi.controller.accreditation.company.application.security.repositories.GuestAccessTokenRepository;
import com.bka.ssi.controller.accreditation.company.application.security.utilities.ApiKeyRegistry;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * The type Authentication service.
 */
@Service
public class AuthenticationService {

    private final SSOClient ssoClient;
    private final GuestAccessTokenRepository repository;
    private final ApiKeyRegistry registry;
    private final Logger logger;
    @Value("${accreditation.guest.token.lifetime}")
    private Integer guestTokenLifetime;

    /**
     * Instantiates a new Authentication service.
     *
     * @param ssoClient                  the sso client
     * @param guestAccessTokenRepository the guest access token repository
     * @param registry                   the registry
     * @param logger                     the logger
     */
    public AuthenticationService(
        SSOClient ssoClient,
        @Qualifier("guestAccessTokenMongoDbFacade")
            GuestAccessTokenRepository guestAccessTokenRepository,
        ApiKeyRegistry registry,
        Logger logger
    ) {
        this.ssoClient = ssoClient;
        this.repository = guestAccessTokenRepository;
        this.registry = registry;
        this.logger = logger;
    }

    /**
     * Verify sso token boolean.
     *
     * @param token the token
     * @return the boolean
     * @throws UnauthenticatedException the unauthenticated exception
     */
    public boolean verifySSOToken(String token) throws UnauthenticatedException {
        logger.info("Verifying SSO token");

        if (token == null || token.equals("") || token.equals("null")) {
            logger.debug("SSO token is null or empty");
            throw new UnauthenticatedException();
        }

        boolean isValid = false;
        try {
            isValid = ssoClient.verifyToken(token);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw new UnauthenticatedException("Could not verify SSO token by SSO Client");
        }

        if (!isValid) {
            logger.debug("SSO token not valid");
            throw new UnauthenticatedException();
        }

        return true;
    }

    /**
     * Issue guest access token guest token.
     *
     * @param accreditationId the accreditation id
     * @return the guest token
     * @throws UnauthenticatedException the unauthenticated exception
     */
    public GuestToken issueGuestAccessToken(String accreditationId)
        throws UnauthenticatedException {
        logger.info("Issuing guest access token");

        UUID uuid = UUID.randomUUID();
        ZonedDateTime expire =
            currentTime().plusNanos(TimeUnit.MILLISECONDS.toNanos(guestTokenLifetime));

        GuestToken inputToken = new GuestToken(uuid.toString(), accreditationId, expire);

        GuestToken savedToken;
        try {
            savedToken = repository.save(inputToken);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw new UnauthenticatedException("Could not save guest access token");
        }

        return savedToken;
    }

    /**
     * Verify guest access token boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws UnauthenticatedException the unauthenticated exception
     */
    public boolean verifyGuestAccessToken(String id) throws UnauthenticatedException {
        logger.info("Verifying guest access token");

        if (id == null || id.equals("") || id.equals("null")) {
            logger.debug("Guest access token is null or empty");
            throw new UnauthenticatedException();
        }

        GuestToken token = null;
        try {
            token = repository.findById(id)
                .orElseThrow(NotFoundException::new);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw new UnauthenticatedException(
                "Could not find guest access token for verification");
        }

        if (ZonedDateTime.now().isAfter(token.getExpiring())) {
            logger.debug("Guest access token expired");
            try {
                repository.deleteById(id);
            } catch (Exception e) {
                logger.debug(e.getMessage());
                throw new UnauthenticatedException("Could not delete guest access token");
            }
            throw new UnauthenticatedException();
        }

        return true;
    }

    /**
     * Invalidate guest access token.
     *
     * @param accreditationId the accreditation id
     */
    public void invalidateGuestAccessToken(String accreditationId) {
        logger.info("Invalidating guest access token");
        repository.deleteByAccreditationId(accreditationId);
    }

    /**
     * Verify api key boolean.
     *
     * @param id     the id
     * @param apiKey the api key
     * @return the boolean
     * @throws UnauthenticatedException the unauthenticated exception
     */
    public boolean verifyApiKey(String id, String apiKey) throws UnauthenticatedException {
        logger.info("Verifying api key");

        if (apiKey == null || apiKey.equals("") || apiKey.equals("null") ||
            id == null || id.equals("")) {
            logger.debug("API key is null or empty or id is null or empty");
            throw new UnauthenticatedException();
        }

        if (this.registry.getEntryById(id) == null) {
            logger.debug("API key id not found in registry");
            throw new UnauthenticatedException();
        }

        if (!this.registry.getEntryById(id).getSecond().equals(apiKey)) {
            logger.debug("API key not valid");
            throw new UnauthenticatedException();
        }

        return true;
    }

    /**
     * Current time zoned date time.
     *
     * @return the zoned date time
     */
    public ZonedDateTime currentTime() {
        return ZonedDateTime.now();
    }
}
