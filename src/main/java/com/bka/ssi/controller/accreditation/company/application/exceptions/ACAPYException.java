package com.bka.ssi.controller.accreditation.company.application.exceptions;

public class ACAPYException extends Exception {

    public ACAPYException() {
        super("ACAPY API client Exception");
    }

    public ACAPYException(String message) {
        super("ACAPY API client Exception: " + message);
    }
}
