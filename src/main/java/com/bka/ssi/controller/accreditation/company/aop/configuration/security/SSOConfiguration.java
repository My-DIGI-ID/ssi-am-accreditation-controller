package com.bka.ssi.controller.accreditation.company.aop.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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

    public String getHost() {
        return host;
    }

    public String getVerifyTokenPath() {
        return tokenPath;
    }

    public String getVerifyPermissionsPath() {
        return permissionPath;
    }

    public String getPort() {
        return port;
    }

    public String getRealm() {
        return realm;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public boolean isSslTrustAll() {
        return isSslTrustAll;
    }

    public boolean isSslVerifyHostname() {
        return isSslVerifyHostname;
    }
}
