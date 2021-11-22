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

@Service
public class AuthenticationService {

    private final SSOClient ssoClient;
    private final GuestAccessTokenRepository repository;
    private final ApiKeyRegistry registry;
    private final Logger logger;
    @Value("${accreditation.guest.token.lifetime}")
    private Integer guestTokenLifetime;

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

    public void invalidateGuestAccessToken(String accreditationId) {
        logger.info("Invalidating guest access token");
        repository.deleteByAccreditationId(accreditationId);
    }

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

    public ZonedDateTime currentTime() {
        return ZonedDateTime.now();
    }
}
