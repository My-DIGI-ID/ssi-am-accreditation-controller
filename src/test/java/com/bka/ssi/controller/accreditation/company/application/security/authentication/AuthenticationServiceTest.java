package com.bka.ssi.controller.accreditation.company.application.security.authentication;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthenticatedException;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.facade.security.GuestAccessTokenMongoDbFacade;
import com.bka.ssi.controller.accreditation.company.infra.sso.keycloak.KeycloakHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

public class AuthenticationServiceTest {

    private static final String validToken = "token";
    private static final String invalidToken = "";

    @Mock
    private Logger logger;

    @Mock
    private KeycloakHttpClient client;

    @Mock
    private GuestAccessTokenMongoDbFacade accessTokenRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void shouldThrowUnauthenticatedExceptionForInvalidToken() throws Exception {
        // Given rejected token response
        Mockito
            .when(client.verifyToken(invalidToken))
            .thenReturn(false);

        // when
        AuthenticationService authenticationService = new AuthenticationService(client,
            accessTokenRepository, logger);

        // then
        assertThrows(UnauthenticatedException.class, () -> {
            authenticationService.verifySSOToken(invalidToken);
        });
    }

    @Test
    public void shouldReturnTrueForValidToken() throws Exception {
        // Given accepted token response
        Mockito
            .when(client.verifyToken(validToken))
            .thenReturn(true);

        // when
        AuthenticationService authenticationService = new AuthenticationService(client,
            accessTokenRepository, logger);

        // then
        assertTrue(authenticationService.verifySSOToken(validToken));
    }

}
