package com.bka.ssi.controller.accreditation.company.aop.exceptions;

public class MongoDbConfigurationException extends RuntimeException {
    public MongoDbConfigurationException() {
        super("Exception occurred while configuring the MongoDB client");
    }
}
