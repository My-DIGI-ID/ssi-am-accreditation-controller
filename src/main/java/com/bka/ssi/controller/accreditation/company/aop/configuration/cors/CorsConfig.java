package com.bka.ssi.controller.accreditation.company.aop.configuration.cors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Value("${accreditation.ui.hostA}")
    private String hostAllowedOriginA;

    @Value("${accreditation.ui.portA}")
    private String portAllowedOriginA;

    @Value("${accreditation.ui.hostB}")
    private String hostAllowedOriginB;

    @Value("${accreditation.ui.portB}")
    private String portAllowedOriginB;

    @Value("${accreditation.ui.hostC}")
    private String hostAllowedOriginC;

    @Value("${accreditation.ui.portC}")
    private String portAllowedOriginC;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins(
                hostAllowedOriginA + ":" + portAllowedOriginA,
                hostAllowedOriginB + ":" + portAllowedOriginB,
                hostAllowedOriginC + ":" + portAllowedOriginC
            )
            .allowedMethods("POST", "GET", "DELETE", "PUT", "PATCH");
    }
}
