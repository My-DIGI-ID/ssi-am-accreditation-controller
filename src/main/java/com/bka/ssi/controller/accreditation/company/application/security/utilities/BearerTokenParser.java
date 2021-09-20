package com.bka.ssi.controller.accreditation.company.application.security.utilities;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

@Service
public class BearerTokenParser {

    Base64.Decoder decoder = Base64.getDecoder();

    public BearerTokenParser() {
    }

    public String getToken() {
        RequestAttributes reqAttr = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servlReqAttr = (ServletRequestAttributes) reqAttr;
        HttpServletRequest httpRequest = servlReqAttr.getRequest();

        Map<String, List<String>> headersMap = Collections
            .list(httpRequest.getHeaderNames())
            .stream()
            .collect(Collectors.toMap(Function.identity(),
                h -> Collections.list(httpRequest.getHeaders(h))));

        List<String> authorizationHeader = headersMap
            .get("authorization");

        if (authorizationHeader != null) {
            return authorizationHeader.get(0).replace("Bearer ", "");
        }

        return null;
    }

    public String parseUserFromJWTToken() {
        String token = this.getToken();

        String[] chunks = token.split("\\.");
        String payload = new String(decoder.decode(chunks[1]));

        Object username;

        try {
            username = new JSONParser(payload).object().get("preferred_username");
        } catch (Exception e) {
            username = "undefined";
        }

        return username.toString();
    }
}
