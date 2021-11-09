package com.bka.ssi.controller.accreditation.company.domain.exceptions;

public class InvalidAccreditationInitialStateException extends Exception {
    public InvalidAccreditationInitialStateException() {
        super("Accreditation initial state is invalid");
    }
}
