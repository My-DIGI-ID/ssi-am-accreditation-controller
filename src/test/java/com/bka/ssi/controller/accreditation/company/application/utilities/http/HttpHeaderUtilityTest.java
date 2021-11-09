package com.bka.ssi.controller.accreditation.company.application.utilities.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class HttpHeaderUtilityTest {

    private HttpServletRequest request;

    @BeforeEach
    public void init() {
        request = Mockito.mock(HttpServletRequest.class);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Mockito.when(request.getHeaderNames()).thenReturn(Collections.enumeration(
            Arrays.asList("authorization", "X-API-Key")));
        Mockito.when(request.getHeaders("authorization")).thenReturn(Collections.enumeration(
            Arrays.asList("Bearer xyz")));
        Mockito.when(request.getHeaders("X-API-Key")).thenReturn(Collections.enumeration(
            Arrays.asList("keyxyz")));
        Mockito.when(request.getHeader("authorization")).thenReturn("Bearer xyz");
        Mockito.when(request.getHeader("X-API-Key")).thenReturn("keyxyz");
    }

    @Test
    public void getHttpServletRequestShouldNotReturnNull() {
        assertNotNull(HttpHeaderUtility.getHttpServletRequest());
    }

    @Test
    public void getHttpHeadersShouldReturnMapOfHeader() {
        Map<String, List<String>> headers = HttpHeaderUtility.getHttpHeaders();

        assertEquals(2, headers.size());
        assertEquals(request.getHeader("authorization"), headers.get("authorization").get(0));
        assertEquals(request.getHeader("X-API-Key"), headers.get("X-API-Key").get(0));
    }

    @Test
    public void getHttpHeaderShouldReturnHeader() {
        String value = HttpHeaderUtility.getHttpHeader("X-API-Key");

        assertEquals(request.getHeader("X-API-Key"), value);
    }

    @Test
    public void getHttpHeaderShouldReturnNullWhenHeaderNotFound() {
        assertNull(HttpHeaderUtility.getHttpHeader(""));
    }
}
