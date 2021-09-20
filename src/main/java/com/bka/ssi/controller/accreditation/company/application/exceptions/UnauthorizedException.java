package com.bka.ssi.controller.accreditation.company.application.exceptions;

public class UnauthorizedException extends Exception {
    public UnauthorizedException() {
        super("User is not authorized to perform transaction");
    }
}
