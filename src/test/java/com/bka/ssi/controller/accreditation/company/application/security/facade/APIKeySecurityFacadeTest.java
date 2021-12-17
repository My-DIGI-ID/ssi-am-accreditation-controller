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

import com.bka.ssi.controller.accreditation.company.application.exceptions.UnauthenticatedException;
import com.bka.ssi.controller.accreditation.company.application.security.authentication.AuthenticationService;
import com.bka.ssi.controller.accreditation.company.application.security.utilities.ApiKeyParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

public class APIKeySecurityFacadeTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private ApiKeyParser apiKeyParser;

    @Mock
    private Logger logger;

    private String apiKeyId = "apiKeyId";

    private TestService proxy;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        AspectJProxyFactory factory = new AspectJProxyFactory(new TestService());

        factory.addAspect(new APIKeySecurityFacade(authenticationService, apiKeyParser, logger));

        proxy = factory.getProxy();
    }

    @Test
    public void protectedTransactionSuccess() throws UnauthenticatedException {

        String apiKey = "apiKey";

        Mockito
            .when(apiKeyParser.getApiKeyById(apiKeyId))
            .thenReturn(apiKey);

        proxy.apiKeyProtectedMethod();

        Mockito.verify(apiKeyParser, Mockito.times(1))
            .getApiKeyById(apiKeyId);
        Mockito.verify(authenticationService, Mockito.times(1))
            .verifyApiKey(apiKeyId, apiKey);
    }

    @Test
    public void protectedTransactionFail() throws UnauthenticatedException {
        String apiKey = "apiKey";

        Mockito
            .when(apiKeyParser.getApiKeyById(apiKeyId))
            .thenReturn(apiKey);
        Mockito
            .when(authenticationService.verifyApiKey(apiKeyId, apiKey))
            .thenThrow(new UnauthenticatedException());

        Assertions.assertThrows(UnauthenticatedException.class, () -> {
            proxy.apiKeyProtectedMethod();
        });
    }

    public class TestService {

        @APIKeyProtectedTransaction(id = "apiKeyId")
        public void apiKeyProtectedMethod() throws UnauthenticatedException {
            return;
        }
    }
}
