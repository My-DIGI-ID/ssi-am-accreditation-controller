package com.bka.ssi.controller.accreditation.company.application.exceptions;

public class JsonParseException extends Exception {

    public JsonParseException() {
        super("JSON parse exception");
    }

    public JsonParseException(String message) {
        super("JSON parse exception: " + message);
    }
}
