package com.bka.ssi.controller.accreditation.company.infra.sso.keycloak;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bka.ssi.controller.accreditation.company.aop.configuration.security.SSOConfiguration;
import com.bka.ssi.controller.accreditation.company.application.exceptions.RestTemplateFactorySSLException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthenticatedException;
import com.bka.ssi.controller.accreditation.company.application.utilities.http.RestTemplateFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@ActiveProfiles("unittest")
@TestPropertySource("classpath:application-unittest.properties")
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {
    SSOConfiguration.class})
@ExtendWith(SpringExtension.class)
public class KeyCloakHttpClientTest {

    private static final String validToken = "token";
    private static final String invalidToken = "";
    private static final String authorizedCondition = "validCondition";
    private static final String unauthorizedCondition = "invalidCondition";

    @Autowired
    private SSOConfiguration configuration;

    @Mock
    private Logger logger;

    @Mock
    private RestTemplate restTemplate;

    private KeycloakHttpClient httpClient;

    @BeforeEach
    public void init() throws RestTemplateFactorySSLException {
        MockitoAnnotations.openMocks(this);
        RestTemplateFactory restTemplateFactory = Mockito.mock(RestTemplateFactory.class);

        Mockito
            .when(restTemplateFactory.create(Mockito.anyBoolean(), Mockito.anyBoolean()))
            .thenReturn(restTemplate);

        httpClient = new KeycloakHttpClient(logger, configuration, restTemplateFactory);
    }

    @Test
    public void shouldVerifyValidToken() throws Exception {
        // Given positive response from keycloak
        ResponseEntity<String> response = new ResponseEntity<>("{\"active\":true}", HttpStatus.OK);
        Mockito
            .when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(),
                Mockito.eq(String.class)))
            .thenReturn(response);

        // when token is valid
        boolean isValid = httpClient.verifyToken(validToken);

        // then
        assertTrue(isValid);
    }

    @Test
    public void shouldRejectInvalidToken() throws Exception {
        // Given negative response from keycloak
        ResponseEntity<String> response = new ResponseEntity<>("{\"active\":false}", HttpStatus.OK);
        Mockito
            .when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(),
                Mockito.eq(String.class)))
            .thenReturn(response);

        // when token is valid
        boolean isValid = httpClient.verifyToken(invalidToken);

        // then
        assertFalse(isValid);
    }

    @Test
    public void shouldRejectUnknownResponse() throws Exception {
        // Given unknown response from keycloak (e.g. instance down)
        ResponseEntity<String> response = new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
        Mockito
            .when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(),
                Mockito.eq(String.class)))
            .thenReturn(response);

        // when token is valid
        boolean isValid = httpClient.verifyToken(validToken);

        // then
        assertFalse(isValid);
    }

    @Test
    public void shouldVerifyValidTransaction() throws Exception {
        // Given valid response from keycloak
        ResponseEntity<String> response = new ResponseEntity<>("{\"result\":true}", HttpStatus.OK);
        Mockito
            .when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(),
                Mockito.eq(String.class)))
            .thenReturn(response);

        // when Token is valid
        boolean isValid = httpClient.verifyPermission(validToken, authorizedCondition);

        // then
        assertTrue(isValid);

    }

    @Test
    public void shouldRejectInValidTransaction() throws Exception {
        // Given negative response from keycloak (user not allowed)
        ResponseEntity<String> response =
            new ResponseEntity<>("{\"error\":\"access_denied\"}", HttpStatus.FORBIDDEN);
        Mockito
            .when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(),
                Mockito.eq(String.class)))
            .thenReturn(response);

        // when Token is valid but user is trying an unauthorized transaction
        boolean isValid = httpClient.verifyPermission(validToken, unauthorizedCondition);

        // then
        assertFalse(isValid);
    }

    @Test
    public void shouldThrowWhenUnauthenticated() {
        // Given negative response from keycloak (user not authenticated)
        HttpClientErrorException error = HttpClientErrorException.create(HttpStatus.UNAUTHORIZED,
            null, null, null, null);
        Mockito
            .when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(),
                Mockito.eq(String.class)))
            .thenThrow(error);

        // when token is invalid
        String token = invalidToken;

        // then
        assertThrows(UnauthenticatedException.class, () -> {
            httpClient.verifyPermission(token, authorizedCondition);
        });
    }
}
