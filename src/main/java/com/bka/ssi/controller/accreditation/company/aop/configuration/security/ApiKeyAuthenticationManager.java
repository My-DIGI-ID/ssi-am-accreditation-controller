package com.bka.ssi.controller.accreditation.company.aop.configuration.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class ApiKeyAuthenticationManager implements AuthenticationManager {

    private final String apiKey;

    public ApiKeyAuthenticationManager(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws
        AuthenticationException {

        String principal = (String) authentication.getPrincipal();

        if (principal.equals(this.apiKey)) {
            authentication.setAuthenticated(true);
            return authentication;
        } else {
            /* ToDo - Handle exceptions and corresponding responses similar to exceptions in
                application/api layer */
            throw new BadCredentialsException("API key not found or invalid");
        }
    }
}
