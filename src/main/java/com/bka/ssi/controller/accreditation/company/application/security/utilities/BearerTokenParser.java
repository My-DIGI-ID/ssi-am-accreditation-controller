package com.bka.ssi.controller.accreditation.company.application.security.utilities;

import com.bka.ssi.controller.accreditation.company.application.utilities.http.HttpHeaderUtility;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class BearerTokenParser {

    @Value("${accreditation.jwt.userIdentifierEntryName}")
    private String userIdentifierEntry;

    private Base64.Decoder decoder;

    public BearerTokenParser() {
        this.decoder = Base64.getDecoder();
    }

    public String getToken() {
        String token = HttpHeaderUtility.getHttpHeader("authorization");

        if (token != null) {
            return token.replace("Bearer ", "");
        }

        return null;
    }

    public String parseUserFromJWTToken() {
        String token = this.getToken();

        String[] chunks = token.split("\\.");
        String payload = new String(decoder.decode(chunks[1]));

        Object username;
        try {
            username = new JSONParser(payload).object().get(this.userIdentifierEntry);
        } catch (Exception e) {
            username = "undefined";
        }

        return username.toString();
    }
}
