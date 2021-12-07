package com.bka.ssi.controller.accreditation.company.application.utilities.http;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

public class HttpHeaderUtility {

    private HttpHeaderUtility() {
    }

    public static HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes =
            (ServletRequestAttributes) requestAttributes;
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();

        return httpServletRequest;
    }

    public static Map<String, List<String>> getHttpHeaders(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        return Collections
            .list(request.getHeaderNames())
            .stream()
            .collect(Collectors.toMap(Function.identity(),
                header -> Collections.list(request.getHeaders(header))));
    }

    public static Map<String, List<String>> getHttpHeaders() {
        HttpServletRequest request = getHttpServletRequest();
        return getHttpHeaders(request);
    }

    public static String getHttpHeader(HttpServletRequest request, String header) {
        if (request == null) {
            return null;
        }

        return request.getHeader(header);
    }

    public static String getHttpHeader(String header) {
        HttpServletRequest request = getHttpServletRequest();
        return getHttpHeader(request, header);
    }
}
