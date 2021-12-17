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

/**
 * The type Http header utility.
 */
public class HttpHeaderUtility {

    private HttpHeaderUtility() {
    }

    /**
     * Gets http servlet request.
     *
     * @return the http servlet request
     */
    public static HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes =
            (ServletRequestAttributes) requestAttributes;
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();

        return httpServletRequest;
    }

    /**
     * Gets http headers.
     *
     * @param request the request
     * @return the http headers
     */
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

    /**
     * Gets http headers.
     *
     * @return the http headers
     */
    public static Map<String, List<String>> getHttpHeaders() {
        HttpServletRequest request = getHttpServletRequest();
        return getHttpHeaders(request);
    }

    /**
     * Gets http header.
     *
     * @param request the request
     * @param header  the header
     * @return the http header
     */
    public static String getHttpHeader(HttpServletRequest request, String header) {
        if (request == null) {
            return null;
        }

        return request.getHeader(header);
    }

    /**
     * Gets http header.
     *
     * @param header the header
     * @return the http header
     */
    public static String getHttpHeader(String header) {
        HttpServletRequest request = getHttpServletRequest();
        return getHttpHeader(request, header);
    }
}
