package com.bka.ssi.controller.accreditation.company.application.security.facade;

import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthenticatedException;
import com.bka.ssi.controller.accreditation.company.application.security.authentication.AuthenticationService;
import com.bka.ssi.controller.accreditation.company.application.security.utilities.BearerTokenParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

public class GuestSecurityFacadeTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private BearerTokenParser bearerTokenParser;

    @Mock
    private Logger logger;

    private TestService proxy;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        AspectJProxyFactory
            factory = new AspectJProxyFactory(new TestService());

        factory.addAspect(
            new GuestSecurityFacade(authenticationService, bearerTokenParser, logger));

        proxy = factory.getProxy();
    }

    @Test
    public void protectedTransactionSuccess() throws Exception {
        String token = "token";

        Mockito
            .when(bearerTokenParser.getToken())
            .thenReturn(token);

        proxy.guestProtectedMethod();

        Mockito.verify(authenticationService, Mockito.times(1))
            .verifyGuestAccessToken(token);
    }

    @Test
    public void protectedTransactionFail() throws Exception {
        String token = "token";

        Mockito
            .when(bearerTokenParser.getToken())
            .thenReturn(token);
        Mockito
            .when(authenticationService.verifyGuestAccessToken(token))
            .thenThrow(new UnauthenticatedException());

        Assertions.assertThrows(UnauthenticatedException.class, () -> {
            proxy.guestProtectedMethod();
        });
    }

    public class TestService {
        @GuestProtectedTransaction
        public void guestProtectedMethod() throws UnauthenticatedException {
            return;
        }
    }
}
