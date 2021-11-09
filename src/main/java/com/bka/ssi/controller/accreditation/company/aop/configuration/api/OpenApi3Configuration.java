package com.bka.ssi.controller.accreditation.company.aop.configuration.api;

import com.bka.ssi.controller.accreditation.company.aop.configuration.agents.ACAPYConfiguration;
import com.bka.ssi.controller.accreditation.company.aop.configuration.build.InfoConfiguration;
import com.bka.ssi.controller.accreditation.company.aop.configuration.security.ApiKeyConfiguration;
import com.bka.ssi.controller.accreditation.company.aop.configuration.security.SSOConfiguration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApi3Configuration {

    @Value("${swagger_ui.id_provider.host}")
    private String ssoHost;

    private final SSOConfiguration ssoConfiguration;
    private final InfoConfiguration infoConfiguration;
    private final ACAPYConfiguration acapyConfiguration;
    private final ApiKeyConfiguration apiKeyConfiguration;

    public OpenApi3Configuration(SSOConfiguration ssoConfiguration,
        InfoConfiguration infoConfiguration,
        ACAPYConfiguration acapyConfiguration,
        ApiKeyConfiguration apiKeyConfiguration) {
        this.ssoConfiguration = ssoConfiguration;
        this.infoConfiguration = infoConfiguration;
        this.acapyConfiguration = acapyConfiguration;
        this.apiKeyConfiguration = apiKeyConfiguration;
    }

    @Bean
    public OpenAPI openAPI() {
        /* ToDo - redefine ID_PROVIDER_PERMISSIONS_PATH and ID_PROVIDER_TOKEN_PATH to fit a broad
             range of configuration */
        /* Technical Debt.: swagger-ui cannot resolve docker host mapping */
        String url = this.ssoHost
            .replace("{port}", ":" + this.ssoConfiguration.getPort())
            .replace("{path}", "auth/realms/{realm}/protocol/openid-connect/token")
            .replace("{realm}", this.ssoConfiguration.getRealm());

        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes("oauth2_accreditation_party_api", new SecurityScheme()
                    .type(SecurityScheme.Type.OAUTH2)
                    .description(
                        "OAuth2 flow: Accreditation and party API secured by identity provider")
                    .flows(new OAuthFlows()
                        .password(new OAuthFlow()
                            .authorizationUrl(url)
                            .refreshUrl(url)
                            .tokenUrl(url)
                            .scopes(new Scopes())
                        )))
                .addSecuritySchemes("api_key_webhook_api", new SecurityScheme()
                    .type(SecurityScheme.Type.APIKEY)
                    .description("Api Key: Webhook API")
                    .in(SecurityScheme.In.HEADER)
                    .name(this.acapyConfiguration.getApiKeyHeaderName())
                )
                .addSecuritySchemes("api_key_accr_veri_api", new SecurityScheme()
                    .type(SecurityScheme.Type.APIKEY)
                    .description("Api Key: Accreditation-Verification Controller API")
                    .in(SecurityScheme.In.HEADER)
                    .name(this.apiKeyConfiguration.getApiKeyHeaderName())
                )
            )
            .security(Arrays.asList(
                new SecurityRequirement().addList("oauth2_accreditation_party_api"),
                new SecurityRequirement().addList("api_key_webhook_api"),
                new SecurityRequirement().addList("api_key_accr_veri_api")
                )
            )
            .info(new Info()
                .title(this.infoConfiguration.getTitle())
                .description(this.infoConfiguration.getDescription())
                .version(this.infoConfiguration.getVersion())
                .contact(new Contact()
                    .name(this.infoConfiguration.getName())
                    .url(this.infoConfiguration.getUrl())
                    .email(this.infoConfiguration.getEmail())
                )
            );
    }
}
