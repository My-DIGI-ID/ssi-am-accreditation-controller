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

package com.bka.ssi.controller.accreditation.company.aop.logging;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Slf 4 j mdc filter.
 */
@Component
public class Slf4jMDCFilter extends OncePerRequestFilter {

    private final String responseHeader;
    private final String mdcTokenKey;
    private final String requestHeader;

    /**
     * Instantiates a new Slf 4 j mdc filter.
     */
    public Slf4jMDCFilter() {
        responseHeader = Slf4jMDCFilterConfiguration.DEFAULT_RESPONSE_TOKEN_HEADER;
        mdcTokenKey = Slf4jMDCFilterConfiguration.DEFAULT_MDC_UUID_TOKEN_KEY;
        requestHeader = Slf4jMDCFilterConfiguration.DEFAULT_REQUEST_TOKEN_HEADER;
    }

    /**
     * Instantiates a new Slf4j mdc filter.
     *
     * @param responseHeader the response header
     * @param mdcTokenKey    the mdc token key
     * @param requestHeader  the request header
     */
    public Slf4jMDCFilter(final String responseHeader, final String mdcTokenKey,
        final String requestHeader) {
        this.responseHeader = responseHeader;
        this.mdcTokenKey = mdcTokenKey;
        this.requestHeader = requestHeader;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
        final HttpServletResponse response, final FilterChain chain)
        throws java.io.IOException, ServletException {
        try {
            final String token = extractToken(request);
            MDC.put(mdcTokenKey, token);
            if (StringUtils.hasText(responseHeader)) {
                response.addHeader(responseHeader, token);
            }
            chain.doFilter(request, response);
        } finally {
            MDC.remove(mdcTokenKey);
        }
    }

    private String extractToken(final HttpServletRequest request) {
        final String token;
        if (StringUtils.hasText(requestHeader)
            && StringUtils.hasText(request.getHeader(requestHeader))) {
            token = request.getHeader(requestHeader);
        } else {
            token = UUID.randomUUID().toString();
        }
        return token;
    }

    @Override
    protected boolean isAsyncDispatch(final HttpServletRequest request) {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}
