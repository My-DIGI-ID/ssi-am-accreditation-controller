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

package com.bka.ssi.controller.accreditation.company.aop.configuration.cors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The type Cors configuration.
 */
@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {

    @Value("${accreditation.ui.hostA}")
    private String allowedOriginHostA;

    @Value("${accreditation.ui.hostB}")
    private String allowedOriginHostB;

    @Value("${accreditation.ui.hostC}")
    private String allowedOriginHostC;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins(
                allowedOriginHostA,
                allowedOriginHostB,
                allowedOriginHostC
            )
            .allowedMethods("POST", "GET", "DELETE", "PUT", "PATCH", "OPTIONS");

        registry.addMapping("/admin/**")
            .allowedOrigins(
                allowedOriginHostA,
                allowedOriginHostB,
                allowedOriginHostC
            )
            .allowedMethods("POST", "GET", "DELETE", "PUT", "PATCH", "OPTIONS");
    }
}
