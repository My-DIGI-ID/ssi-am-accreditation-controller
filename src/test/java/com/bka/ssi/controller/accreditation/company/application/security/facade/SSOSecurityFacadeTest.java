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

package com.bka.ssi.controller.accreditation.company.application.security.facade;

import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthorizedException;
import com.bka.ssi.controller.accreditation.company.application.security.authentication.AuthenticationService;
import com.bka.ssi.controller.accreditation.company.application.security.authorization.AuthorizationService;
import com.bka.ssi.controller.accreditation.company.application.security.utilities.BearerTokenParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

public class SSOSecurityFacadeTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private AuthorizationService authorizationService;

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
            new SSOSecurityFacade(authenticationService, authorizationService, bearerTokenParser,
                logger));

        proxy = factory.getProxy();
    }

    @Test
    public void protectedTransactionSuccess() throws Exception {

        proxy.ssoProtectedMethod();

        Mockito.verify(bearerTokenParser, Mockito.times(1)).getToken();
        Mockito.verify(authenticationService, Mockito.times(1))
            .verifySSOToken(Mockito.any());
        Mockito.verify(authorizationService, Mockito.times(1))
            .verifySSOPermission(Mockito.any(), Mockito.any());
    }

    @Test
    public void protectedTransactionFail() throws Exception {

        Mockito
            .when(authorizationService.verifySSOPermission(Mockito.any(), Mockito.any()))
            .thenThrow(new UnauthorizedException());

        Assertions.assertThrows(UnauthorizedException.class, () -> {
            proxy.ssoProtectedMethod();
        });
    }


    public class TestService {
        @SSOProtectedTransaction(scope = "scope", resource = "resource")
        public void ssoProtectedMethod() throws UnauthorizedException {
            return;
        }
    }
}
