package com.bka.ssi.controller.accreditation.company.application.security.authentication;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.bka.ssi.controller.accreditation.company.aop.configuration.security.ApiKeyConfiguration;
import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthenticatedException;
import com.bka.ssi.controller.accreditation.company.application.security.authentication.dto.GuestToken;
import com.bka.ssi.controller.accreditation.company.application.security.utilities.ApiKeyRegistry;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.security.GuestAccessTokenMongoDbFacade;
import com.bka.ssi.controller.accreditation.company.infra.sso.keycloak.KeycloakHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.data.util.Pair;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class AuthenticationServiceTest {

    private static final String validToken = "token";
    private static final String invalidToken = "invalidToken";
    private static final int guestTokenLifetime = 1000;

    @Mock
    private Logger logger;

    @Mock
    private KeycloakHttpClient client;

    @Mock
    private GuestAccessTokenMongoDbFacade accessTokenRepository;

    @Mock
    private ApiKeyRegistry registry;

    private AuthenticationService authenticationService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        this.authenticationService = new AuthenticationService(client,
            accessTokenRepository, registry, logger);

        ReflectionTestUtils.setField(authenticationService, "guestTokenLifetime",
            guestTokenLifetime);
    }


    @Test
    public void shouldThrowUnauthenticatedExceptionForInvalidToken() throws Exception {
        // given
        Mockito
            .when(client.verifyToken(invalidToken))
            .thenReturn(false);

        // then
        assertThrows(UnauthenticatedException.class, () -> {
            authenticationService.verifySSOToken(invalidToken);
        });
    }

    @Test
    void shouldThrowUnauthenticatedExceptionForEmptyToken() throws Exception {

        assertThrows(UnauthenticatedException.class, () -> {
            authenticationService.verifySSOToken("");
        });
    }

    @Test
    public void shouldReturnTrueForValidToken() throws Exception {
        // given
        Mockito
            .when(client.verifyToken(validToken))
            .thenReturn(true);

        // then
        assertTrue(authenticationService.verifySSOToken(validToken));
    }

    @Test
    public void issueGuestAccessToken() throws Exception {
        // given
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expectedExpire =
            now.plusNanos(TimeUnit.MILLISECONDS.toNanos(guestTokenLifetime));

        Mockito
            .when(accessTokenRepository.save(Mockito.any(GuestToken.class)))
            .thenAnswer((args) -> {
                return args.getArgument(0);
            });

        AuthenticationService authenticationServiceSpy = Mockito.spy(authenticationService);
        Mockito.doReturn(now).when(authenticationServiceSpy).currentTime();

        GuestToken guestToken = authenticationServiceSpy
            .issueGuestAccessToken("12345");

        // then
        assertTrue(expectedExpire.isEqual(guestToken.getExpiring()));
    }

    @Test
    public void verifyGuestAccessTokenWithValidToken() throws Exception {
        // given
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expireDate = now.plusHours(1);

        String tokenId = "12345";
        GuestToken validToken = new GuestToken(tokenId, "23456", expireDate);

        Mockito
            .when(accessTokenRepository.findById(tokenId))
            .thenReturn(Optional.of(validToken));

        AuthenticationService authenticationServiceSpy = Mockito.spy(authenticationService);
        Mockito.doReturn(now).when(authenticationServiceSpy).currentTime();

        // then
        assertTrue(authenticationServiceSpy.verifyGuestAccessToken(tokenId));
    }

    @Test
    public void verifyGuestAccessTokenWithEmptyId() {

        assertThrows(UnauthenticatedException.class, () -> {
            authenticationService.verifyGuestAccessToken("");
        });
    }

    @Test
    public void verifyGuestAccessTokenWithExpiredToken() throws Exception {
        // given
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expireDate = now.minusHours(1);

        String tokenId = "12345";
        GuestToken expiredToken = new GuestToken(tokenId, "23456", expireDate);

        Mockito
            .when(accessTokenRepository.findById(tokenId))
            .thenReturn(Optional.of(expiredToken));

        AuthenticationService authenticationServiceSpy = Mockito.spy(authenticationService);
        Mockito.doReturn(now).when(authenticationServiceSpy).currentTime();

        // then
        assertThrows(UnauthenticatedException.class, () -> {
            authenticationServiceSpy.verifyGuestAccessToken(tokenId);
        });
        Mockito.verify(accessTokenRepository, Mockito.times(1))
            .deleteById(tokenId);
    }

    @Test
    public void invalidateGuestAccessToken() {
        String accreditationId = "123";
        authenticationService.invalidateGuestAccessToken(accreditationId);

        Mockito.verify(accessTokenRepository, Mockito.times(1))
            .deleteByAccreditationId(accreditationId);
    }

    @Test
    public void verifyApiKeyWithValidApiKey() {
        // given
        Mockito
            .when(registry.getEntryById(ApiKeyConfiguration.API_KEY_ID))
            .thenReturn(Pair.of("X-API-Key", "123"));

        // then
        try {
            this.authenticationService.verifyApiKey(ApiKeyConfiguration.API_KEY_ID, "123");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void verifyApiKeyWithInvalidApiKey() {
        // given
        Mockito
            .when(registry.getEntryById(ApiKeyConfiguration.API_KEY_ID))
            .thenReturn(Pair.of("X-API-Key", "123"));

        // then
        assertThrows(UnauthenticatedException.class, () -> {
            this.authenticationService.verifyApiKey(ApiKeyConfiguration.API_KEY_ID, "456");
        });
    }

    @Test
    public void verifyApiKeyWithNoEntryInRegistry() {
        // given
        Mockito
            .when(registry.getEntryById(ApiKeyConfiguration.API_KEY_ID))
            .thenReturn(null);

        // then
        assertThrows(UnauthenticatedException.class, () -> {
            this.authenticationService.verifyApiKey(ApiKeyConfiguration.API_KEY_ID, "123");
        });
    }

    @Test
    public void verifyApiKeyWithEmptyApiKey() {

        assertThrows(UnauthenticatedException.class, () -> {
            this.authenticationService.verifyApiKey(ApiKeyConfiguration.API_KEY_ID, "");
        });
    }
}
