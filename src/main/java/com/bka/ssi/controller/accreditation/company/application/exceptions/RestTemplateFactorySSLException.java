package com.bka.ssi.controller.accreditation.company.application.exceptions;

public class RestTemplateFactorySSLException extends Exception {
    public RestTemplateFactorySSLException() {
        super("Exception occurred while configuring SSL for the RestTemplate");
    }
}
