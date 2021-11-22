package com.bka.ssi.controller.accreditation.company.application.exceptions;

public class EmailBuildException extends Exception {

    public EmailBuildException() {
        super("Email not built correctly");
    }

    public EmailBuildException(String message) {
        super("Email not built correctly: " + message);
    }
}
