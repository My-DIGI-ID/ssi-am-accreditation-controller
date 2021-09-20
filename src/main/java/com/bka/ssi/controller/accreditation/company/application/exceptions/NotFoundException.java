package com.bka.ssi.controller.accreditation.company.application.exceptions;

public class NotFoundException extends Exception {

    public NotFoundException() {
        super("Not found");
    }

    public NotFoundException(String message) {
        super("Not found: " + message);
    }
}
