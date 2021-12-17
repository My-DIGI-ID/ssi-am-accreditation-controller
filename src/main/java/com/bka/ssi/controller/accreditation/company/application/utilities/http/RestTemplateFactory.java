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

package com.bka.ssi.controller.accreditation.company.application.utilities.http;

import com.bka.ssi.controller.accreditation.company.application.exceptions.RestTemplateFactorySSLException;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;

/**
 * The type Rest template factory.
 */
@Component
public class RestTemplateFactory {

    private final Logger logger;

    /**
     * Instantiates a new Rest template factory.
     *
     * @param logger the logger
     */
    public RestTemplateFactory(Logger logger) {
        this.logger = logger;
    }

    /**
     * Create rest template.
     *
     * @param trustAll       the trust all
     * @param verifyHostname the verify hostname
     * @return the rest template
     * @throws RestTemplateFactorySSLException the rest template factory ssl exception
     */
    public RestTemplate create(boolean trustAll, boolean verifyHostname)
        throws RestTemplateFactorySSLException {

        HttpClientBuilder builder = HttpClients.custom();

        if (trustAll) {
            try {
                TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
                SSLContext sslContext =
                    SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();

                builder = builder.setSSLContext(sslContext);
            } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
                logger.error(e.getMessage());
                throw new RestTemplateFactorySSLException();
            }
        }

        if (!verifyHostname) {
            builder.setSSLHostnameVerifier(new NoopHostnameVerifier());
        }

        CloseableHttpClient httpClient = builder.build();

        HttpComponentsClientHttpRequestFactory requestFactory =
            new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        return new RestTemplate(requestFactory);
    }
}
