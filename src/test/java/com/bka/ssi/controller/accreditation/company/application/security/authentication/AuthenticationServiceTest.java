package com.bka.ssi.controller.accreditation.company.application.security.authentication;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.bka.ssi.controller.accreditation.company.aop.configuration.security.ApiKeyConfiguration;
import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthenticatedException;
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

public class AuthenticationServiceTest {

    private static final String validToken = "token";
    private static final String invalidToken = "invalidToken";

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
    public void shouldReturnTrueForValidToken() throws Exception {
        // given
        Mockito
            .when(client.verifyToken(validToken))
            .thenReturn(true);

        // then
        assertTrue(authenticationService.verifySSOToken(validToken));
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
}
