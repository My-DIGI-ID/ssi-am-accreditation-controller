package com.bka.ssi.controller.accreditation.company.application.exceptions;

public class InvalidCsvFileException extends Exception {

    public InvalidCsvFileException() {
        super("Invalid CSV file");
    }

    public InvalidCsvFileException(String message) {
        super("Invalid CSV file: " + message);
    }
}