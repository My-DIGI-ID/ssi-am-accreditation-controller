package com.bka.ssi.controller.accreditation.company.aop.configuration.cors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
