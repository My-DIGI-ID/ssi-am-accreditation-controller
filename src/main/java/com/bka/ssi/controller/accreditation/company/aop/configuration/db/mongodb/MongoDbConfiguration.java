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

package com.bka.ssi.controller.accreditation.company.aop.configuration.db.mongodb;

import com.bka.ssi.controller.accreditation.company.aop.exceptions.MongoDbConfigurationException;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.converters.ZonedDateTimeReadConverter;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.converters.ZonedDateTimeWriteConverter;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * The type Mongo db configuration.
 */
@Configuration
public class MongoDbConfiguration extends AbstractMongoClientConfiguration {

    @Value("${mongodb.host}")
    private String host;
    @Value("${mongodb.port}")
    private int port;
    @Value("${mongodb.username}")
    private String username;
    @Value("${mongodb.password}")
    private String password;
    @Value("${mongodb.authentication-database}")
    private String authenticationDatabase;
    @Value("${mongodb.database}")
    private String database;
    @Value("${mongodb.ssl.enabled}")
    private boolean sslEnabled;
    @Value("${mongodb.ssl.key-store}")
    private String keystorePath;
    @Value("${mongodb.ssl.key-store-password}")
    private String keystorePassword;
    @Value("${mongodb.ssl.trust-store}")
    private String trustStorePath;
    @Value("${mongodb.ssl.trust-store-password}")
    private String trustStorePassword;
    @Value("${mongodb.ssl.invalid-hostname-allowed}")
    private boolean invalidHostnameAllowed;

    private final ResourceLoader resourceLoader;
    private final Logger logger;
    private final List<Converter<?, ?>> converters = new ArrayList<>();

    /**
     * Instantiates a new Mongo db configuration.
     *
     * @param resourceLoader the resource loader
     * @param logger         the logger
     */
    public MongoDbConfiguration(ResourceLoader resourceLoader,
        Logger logger) {
        this.resourceLoader = resourceLoader;
        this.logger = logger;
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    public MongoClient mongoClient() {

        ConnectionString connectionString = new ConnectionString("mongodb://"
            + username + ":" + password
            + "@" + host + ":" + port
            + "/" + authenticationDatabase);

        MongoClientSettings.Builder settingsBuilder = MongoClientSettings.builder()
            .applyConnectionString(connectionString);

        if (sslEnabled) {
            try {
                // Get Trust Store
                TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(resourceLoader.getResource(trustStorePath).getInputStream(),
                    trustStorePassword.toCharArray());

                trustManagerFactory.init(trustStore);

                KeyManagerFactory keyManagerFactory =
                    KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

                // Get Key Store
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(resourceLoader.getResource(keystorePath).getInputStream(),
                    keystorePassword.toCharArray());

                keyManagerFactory.init(keyStore, keystorePassword.toCharArray());

                // Init SSL Context
                SSLContext sslContext = SSLContext.getInstance("TLSv1.3");
                sslContext.init(keyManagerFactory.getKeyManagers(),
                    trustManagerFactory.getTrustManagers(), null);

                settingsBuilder.applyToSslSettings(builder -> {
                    builder.enabled(true);
                    builder.context(sslContext);
                    builder.invalidHostNameAllowed(invalidHostnameAllowed);
                }).build();

                return MongoClients.create(settingsBuilder.build());

            } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | KeyManagementException | UnrecoverableKeyException e) {
                logger.error(e.getMessage());
                throw new MongoDbConfigurationException();
            }
        }

        return MongoClients.create(settingsBuilder.build());
    }

    @Override
    public MongoCustomConversions customConversions() {
        converters.add(new ZonedDateTimeWriteConverter());
        converters.add(new ZonedDateTimeReadConverter());

        return new MongoCustomConversions(converters);
    }
}
