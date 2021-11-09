package com.bka.ssi.controller.accreditation.company.application.security.utilities;

import com.bka.ssi.controller.accreditation.company.application.utilities.http.HttpHeaderUtility;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyParser {

    private final ApiKeyRegistry registry;

    public ApiKeyParser(
        ApiKeyRegistry registry) {
        this.registry = registry;
    }

    public String getApiKeyByHeaderName(String headerName) {
        return HttpHeaderUtility.getHttpHeader(headerName);
    }

    public String getApiKeyById(String id) {
        Pair<String, String> headerNameAndKeyPair = this.registry.getEntryById(id);

        if (headerNameAndKeyPair == null) {
            return null;
        }

        String headerName = headerNameAndKeyPair.getFirst();
        return this.getApiKeyByHeaderName(headerName);
    }
}
