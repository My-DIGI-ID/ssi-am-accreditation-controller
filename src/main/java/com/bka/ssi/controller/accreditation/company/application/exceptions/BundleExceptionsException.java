package com.bka.ssi.controller.accreditation.company.application.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BundleExceptionsException extends Exception {
    private final List<? extends Exception> exceptions;

    public BundleExceptionsException(List<? extends Exception> exceptions) {
        this(exceptions != null ? toString(exceptions) : null, exceptions);
    }

    public BundleExceptionsException(String message, List<? extends Exception> exceptions) {
        super(message);
        if (exceptions == null) {
            this.exceptions = null;
        } else {
            this.exceptions = new ArrayList<>(exceptions);
        }
    }

    public List<? extends Exception> getExceptions() {
        return exceptions;
    }

    private static String toString(List<? extends Exception> exceptions) {
        return exceptions
            .stream()
            .map(exception -> exception == null || exception.getMessage() == null ? "null" :
                exception.getMessage())
            .collect(Collectors.joining(", "));
    }
}
