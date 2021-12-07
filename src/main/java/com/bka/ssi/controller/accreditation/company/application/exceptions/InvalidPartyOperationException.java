package com.bka.ssi.controller.accreditation.company.application.exceptions;

public class InvalidPartyOperationException extends Exception {
    public InvalidPartyOperationException() {
        super("Invalid operation for party");
    }

    public InvalidPartyOperationException(String message) {
        super("Invalid operation for party: " + message);
    }
}
