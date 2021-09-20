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

@Component
public class Slf4jMDCFilter extends OncePerRequestFilter {

    private final String responseHeader;
    private final String mdcTokenKey;
    private final String requestHeader;

    public Slf4jMDCFilter() {
        responseHeader = Slf4jMDCFilterConfiguration.DEFAULT_RESPONSE_TOKEN_HEADER;
        mdcTokenKey = Slf4jMDCFilterConfiguration.DEFAULT_MDC_UUID_TOKEN_KEY;
        requestHeader = Slf4jMDCFilterConfiguration.DEFAULT_REQUEST_TOKEN_HEADER;
    }

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
