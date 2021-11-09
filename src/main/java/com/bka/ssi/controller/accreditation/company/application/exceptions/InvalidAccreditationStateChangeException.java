package com.bka.ssi.controller.accreditation.company.application.exceptions;

public class InvalidAccreditationStateChangeException extends Exception {

    public InvalidAccreditationStateChangeException() {
        super("Invalid accreditation state change");
    }

    public InvalidAccreditationStateChangeException(String message) {
        super("Invalid accreditation state change: " + message);
    }
}
