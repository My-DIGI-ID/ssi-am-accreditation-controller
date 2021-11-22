package com.bka.ssi.controller.accreditation.company.application.services.dto.validation;

import com.bka.ssi.controller.accreditation.company.application.exceptions.BundleConstraintViolationExceptionsException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

@Component
public class ValidationService {

    private final Validator validator;
    private final Logger logger;

    public ValidationService(Validator validator, Logger logger) {
        this.validator = validator;
        this.logger = logger;
    }

    public <T> boolean validate(T dto) throws ConstraintViolationException {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        return true;
    }

    public <T> boolean validate(List<T> dtos) throws BundleConstraintViolationExceptionsException {
        List<ConstraintViolationException> exceptions = new ArrayList<>();

        for (int i = 0; i < dtos.size(); i++) {
            try {
                this.validate(dtos.get(i));
            } catch (ConstraintViolationException e) {
                exceptions
                    .add(new ConstraintViolationException("index: " + i + ", " + e.getMessage(),
                        e.getConstraintViolations()));
            }
        }

        if (!exceptions.isEmpty()) {
            throw new BundleConstraintViolationExceptionsException(exceptions);
        }

        return true;
    }
}
