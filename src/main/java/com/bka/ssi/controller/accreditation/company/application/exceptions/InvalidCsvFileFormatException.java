package com.bka.ssi.controller.accreditation.company.application.exceptions;

public class InvalidCsvFileFormatException extends Exception {

    public InvalidCsvFileFormatException() {
        super("Invalid CSV file format");
    }

    public InvalidCsvFileFormatException(String message) {
        super("Invalid CSV file format: " + message);
    }
}