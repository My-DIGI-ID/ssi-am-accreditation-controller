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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Slf4j mdc filter configuration.
 */
@Configuration
@ConfigurationProperties(prefix = "config.slf4jfilter")
public class Slf4jMDCFilterConfiguration {


    /**
     * The constant DEFAULT_RESPONSE_TOKEN_HEADER.
     */
    public static final String DEFAULT_RESPONSE_TOKEN_HEADER = "x-operation-id";

    /**
     * The constant DEFAULT_REQUEST_TOKEN_HEADER.
     */
    public static final String DEFAULT_REQUEST_TOKEN_HEADER = "x-operation-id";

    /**
     * The constant DEFAULT_MDC_UUID_TOKEN_KEY.
     */
    public static final String DEFAULT_MDC_UUID_TOKEN_KEY = "operationID";

    private String responseHeader = DEFAULT_RESPONSE_TOKEN_HEADER;
    private String mdcTokenKey = DEFAULT_MDC_UUID_TOKEN_KEY;
    private String requestHeader = DEFAULT_REQUEST_TOKEN_HEADER;

    /**
     * Servlet registration bean filter registration bean.
     *
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean servletRegistrationBean() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        final Slf4jMDCFilter log4jMDCFilterFilter =
            new Slf4jMDCFilter(responseHeader, mdcTokenKey, requestHeader);
        registrationBean.setFilter(log4jMDCFilterFilter);
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
