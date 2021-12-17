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

package com.bka.ssi.controller.accreditation.company.aop.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * The type Sso configuration.
 */
@Configuration
public class SSOConfiguration {

    @Value("${id_provider.host}")
    private String host;

    @Value("${id_provider.permissions.path}")
    private String permissionPath;

    @Value("${id_provider.token.path}")
    private String tokenPath;

    @Value("${id_provider.port}")
    private String port;

    @Value("${id_provider.realm}")
    private String realm;

    @Value("${id_provider.client_id}")
    private String clientId;

    @Value("${id_provider.client_secret}")
    private String clientSecret;

    @Value("${id_provider.ssl.trust_all}")
    private boolean isSslTrustAll;

    @Value("${id_provider.ssl.verify_hostname}")
    private boolean isSslVerifyHostname;

    /**
     * Gets host.
     *
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * Gets verify token path.
     *
     * @return the verify token path
     */
    public String getVerifyTokenPath() {
        return tokenPath;
    }

    /**
     * Gets verify permissions path.
     *
     * @return the verify permissions path
     */
    public String getVerifyPermissionsPath() {
        return permissionPath;
    }

    /**
     * Gets port.
     *
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * Gets realm.
     *
     * @return the realm
     */
    public String getRealm() {
        return realm;
    }

    /**
     * Gets client id.
     *
     * @return the client id
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Gets client secret.
     *
     * @return the client secret
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Is ssl trust all boolean.
     *
     * @return the boolean
     */
    public boolean isSslTrustAll() {
        return isSslTrustAll;
    }

    /**
     * Is ssl verify hostname boolean.
     *
     * @return the boolean
     */
    public boolean isSslVerifyHostname() {
        return isSslVerifyHostname;
    }
}
