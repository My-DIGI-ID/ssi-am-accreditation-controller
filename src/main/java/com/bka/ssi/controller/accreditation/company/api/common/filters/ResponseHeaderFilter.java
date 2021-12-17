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

/**
 * The type Response header filter.
 */
@Component
@WebFilter("/*")
public class ResponseHeaderFilter implements Filter {

    private final Slf4jMDCFilterConfiguration slf4jMDCFilterConfiguration;

    /**
     * Instantiates a new Response header filter.
     *
     * @param slf4jMDCFilterConfiguration the slf 4 j mdc filter configuration
     */
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
