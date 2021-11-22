package com.bka.ssi.controller.accreditation.company.application.exceptions;

public class InvalidAccreditationStatusForPartyException extends Exception {

    public InvalidAccreditationStatusForPartyException() {
        super("Invalid accrediation status for party");
    }

    public InvalidAccreditationStatusForPartyException(String message) {
        super("Invalid accrediation status for party: " + message);
    }
}
