package com.bka.ssi.controller.accreditation.company.application.exceptions;

public class AlreadyExistsException extends Exception {

    public AlreadyExistsException() {
        super("Already exists");
    }

    public AlreadyExistsException(String message) {
        super("Already exists: " + message);
    }
}