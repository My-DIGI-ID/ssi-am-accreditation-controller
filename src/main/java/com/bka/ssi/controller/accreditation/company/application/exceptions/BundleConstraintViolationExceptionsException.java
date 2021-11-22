package com.bka.ssi.controller.accreditation.company.application.exceptions;

import java.util.List;
import javax.validation.ConstraintViolationException;

public class BundleConstraintViolationExceptionsException extends BundleExceptionsException {

    public BundleConstraintViolationExceptionsException(
        List<ConstraintViolationException> exceptions) {
        super(exceptions);
    }

    public BundleConstraintViolationExceptionsException(String message,
        List<ConstraintViolationException> exceptions) {
        super(message, exceptions);
    }
}
