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

@Component
public class RestTemplateFactory {

    private final Logger logger;

    public RestTemplateFactory(Logger logger) {
        this.logger = logger;
    }

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
