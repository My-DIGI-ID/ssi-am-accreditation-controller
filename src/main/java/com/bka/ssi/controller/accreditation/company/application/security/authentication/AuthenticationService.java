package com.bka.ssi.controller.accreditation.company.application.security.authentication;

import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthenticatedException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthorizedException;
import com.bka.ssi.controller.accreditation.company.application.security.GuestAccessTokenRepository;
import com.bka.ssi.controller.accreditation.company.application.security.SSOClient;
import com.bka.ssi.controller.accreditation.company.application.security.authentication.dto.GuestToken;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthenticationService {

    private final SSOClient ssoClient;
    private final GuestAccessTokenRepository guestAccessTokenRepository;
    private final Logger logger;

    @Value("${accreditation.guest.token.lifetime}")
    private Integer guestTokenLifetime;

    public AuthenticationService(
        SSOClient ssoClient,
        @Qualifier("guestAccessTokenMongoDbFacade")
            GuestAccessTokenRepository guestAccessTokenRepository,
        Logger logger
    ) {
        this.ssoClient = ssoClient;
        this.guestAccessTokenRepository = guestAccessTokenRepository;
        this.logger = logger;
    }

    public boolean verifySSOToken(String token) throws Exception {
        Boolean isValid = ssoClient.verifyToken(token);

        if (!isValid) {
            logger.debug("SSO Token not valid");
            throw new UnauthenticatedException();
        }

        return true;
    }

    public GuestToken issueGuestAccessToken(String accreditationId) throws Exception {
        logger.debug("Issuing Guest access token");

        UUID uuid = UUID.randomUUID();
        Date expire = new Date(System.currentTimeMillis() + guestTokenLifetime);

        GuestToken inputToken = new GuestToken(uuid.toString(), accreditationId, expire);
        GuestToken savedToken = guestAccessTokenRepository.save(inputToken);

        return savedToken;
    }

    public void verifyGuestAccessToken(String id) throws UnauthorizedException {
        logger.debug("Verifying Guest access token");

        if (id == null || id.equals("") || id.equals("null")) {
            throw new UnauthorizedException();
        }

        GuestToken token = guestAccessTokenRepository.findById(id)
            .orElseThrow(UnauthorizedException::new);

        if (new Date().after(token.getExpiring())) {
            guestAccessTokenRepository.deleteById(id);
            throw new UnauthorizedException();
        }
    }

    public void invalidateGuestAccessToken(String accreditationId) throws Exception {
        logger.debug("Invalidating Guest access token");
        guestAccessTokenRepository.deleteByAccreditationId(accreditationId);
    }
}
