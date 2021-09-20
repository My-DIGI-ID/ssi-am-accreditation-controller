package com.bka.ssi.controller.accreditation.company.aop.configuration.agents;

import com.bka.ssi.controller.accreditation.acapy_client.invoker.ApiClient;
import com.bka.ssi.controller.accreditation.acapy_client.invoker.auth.ApiKeyAuth;
import org.springframework.beans.factory.annotation.Value;
import com.bka.ssi.controller.accreditation.acapy_client.api.ConnectionApi;
import com.bka.ssi.controller.accreditation.acapy_client.api.IssueCredentialV10Api;
import com.bka.ssi.controller.accreditation.acapy_client.api.IssueCredentialV20Api;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class ACAPYConfiguration {

    @Value("${accreditation.agent.host}")
    private String host;

    @Value("${accreditation.agent.port}")
    private String port;

    @Value("${accreditation.agent.api_key}")
    private String apiKey;

    @Value("${accreditation.agent.webhook.api_key}")
    private String webhookApiKey;

    @Value("${accreditation.agent.api_key_header_name}")
    private String apiKeyHeaderName;

    private ApiClient apiClient;
    
    @Bean
    public ApiClient apiClient() {
    	ApiClient apiClient =
    		com.bka.ssi.controller.accreditation.acapy_client.invoker.Configuration
                .getDefaultApiClient();
    	
    	apiClient.setBasePath(this.host + ":" + this.port);
    	// ToDo - format according to host <-> accreditation.agent.host <-> ACCR_AGENT_API_URL
    	
    	((ApiKeyAuth) apiClient.getAuthentication("ApiKeyHeader")).setApiKey(this.apiKey);
    	return apiClient;
    }
    
    @Bean
    public ConnectionApi connectionApi() {
    	return new ConnectionApi(apiClient());
    }
    
    @Bean 
    public IssueCredentialV10Api issueCredentialV10Api() {
    	return new IssueCredentialV10Api(apiClient());
    }

    public ACAPYConfiguration() {
        // Might be null, align with Fabio on apiClient initialization
        this.apiClient =
            com.bka.ssi.controller.accreditation.acapy_client.invoker.Configuration
                .getDefaultApiClient();
        this.apiClient.setBasePath(this.host + ":" + this.port);
        // ToDo - format according to host <-> accreditation.agent.host <-> ACCR_AGENT_API_URL

        ((ApiKeyAuth) this.apiClient.getAuthentication("ApiKeyHeader")).setApiKey(this.apiKey);
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getWebhookApiKey() {
        return webhookApiKey;
    }

    public String getApiKeyHeaderName() {
        return apiKeyHeaderName;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }
}
