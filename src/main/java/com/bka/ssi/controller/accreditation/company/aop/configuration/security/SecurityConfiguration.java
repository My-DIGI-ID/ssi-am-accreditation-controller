package com.bka.ssi.controller.accreditation.company.aop.configuration.security;

import com.bka.ssi.controller.accreditation.company.aop.configuration.agents.ACAPYConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ACAPYConfiguration acapyConfiguration;

    public SecurityConfiguration(ACAPYConfiguration acapyConfiguration) {
        this.acapyConfiguration = acapyConfiguration;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/swagger-ui.html")
            .antMatchers("/v3/api-docs")
            .antMatchers("/v3/api-docs.yaml");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ApiKeyAuthenticationFilter apiKeyAuthenticationFilter =
            new ApiKeyAuthenticationFilter(this.acapyConfiguration.getApiKeyHeaderName());
        apiKeyAuthenticationFilter.setAuthenticationManager(
            new ApiKeyAuthenticationManager(this.acapyConfiguration.getWebhookApiKey()));

        /* ToDo - Move to application layer; create annotation to guard controller */
        http
            .csrf().disable()
            .addFilter(apiKeyAuthenticationFilter)
            .exceptionHandling()
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/topic/*").authenticated();

        http
            .csrf().disable()
            .addFilter(apiKeyAuthenticationFilter)
            .exceptionHandling()
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(
                "/api/accreditation/guest/append/clean-guest-information/*",
                "/api/v2/accreditation/guest/append/clean-guest-information/*",
                "/api/accreditation/guest/private",
                "/api/v2/accreditation/guest/private"
            )
            .authenticated();
    }
}
