package com.bka.ssi.controller.accreditation.company.api.common.filters;

import com.bka.ssi.controller.accreditation.company.aop.logging.Slf4jMDCFilterConfiguration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@Component
@WebFilter("/*")
public class ResponseHeaderFilter implements Filter {

    private final Slf4jMDCFilterConfiguration slf4jMDCFilterConfiguration;

    public ResponseHeaderFilter(Slf4jMDCFilterConfiguration slf4jMDCFilterConfiguration) {
        this.slf4jMDCFilterConfiguration = slf4jMDCFilterConfiguration;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
        ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setHeader("Access-Control-Expose-Headers",
            slf4jMDCFilterConfiguration.DEFAULT_RESPONSE_TOKEN_HEADER);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
