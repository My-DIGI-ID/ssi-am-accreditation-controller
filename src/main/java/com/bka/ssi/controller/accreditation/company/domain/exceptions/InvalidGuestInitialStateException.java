package com.bka.ssi.controller.accreditation.company.domain.exceptions;

public class InvalidGuestInitialStateException extends Exception {
    public InvalidGuestInitialStateException() {
        super("Guest initial state is invalid");
    }
}
