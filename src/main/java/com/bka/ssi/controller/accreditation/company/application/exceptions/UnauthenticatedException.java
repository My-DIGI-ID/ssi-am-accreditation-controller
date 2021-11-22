package com.bka.ssi.controller.accreditation.company.application.exceptions;

public class UnauthenticatedException extends Exception {

    public UnauthenticatedException() {
        super("User is not authenticated");
    }

    public UnauthenticatedException(String message) {
        super(message);
    }
}
