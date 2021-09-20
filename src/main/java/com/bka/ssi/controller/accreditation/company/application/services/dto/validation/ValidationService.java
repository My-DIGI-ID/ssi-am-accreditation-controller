package com.bka.ssi.controller.accreditation.company.application.services.dto.validation;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

@Component
public class ValidationService {

    private Validator validator;
    private Logger logger;

    public ValidationService(Validator validator, Logger logger) {
        this.validator = validator;
        this.logger = logger;
    }

    public <T> boolean validate(T dto) throws ConstraintViolationException {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            for (ConstraintViolation<T> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            logger.debug("Validation failed in: " + sb.toString());
            throw new ConstraintViolationException("Validation failed: " + sb.toString(),
                violations);
        }

        return violations.isEmpty();
    }

    public <T> boolean validate(List<T> dtos) throws ConstraintViolationException {
        boolean isValid = true;

        for (T dto : dtos) {
            Set<ConstraintViolation<T>> violations = validator.validate(dto);

            if (!violations.isEmpty()) {
                StringBuilder sb = new StringBuilder();

                for (ConstraintViolation<T> constraintViolation : violations) {
                    sb.append(constraintViolation.getMessage());
                }

                logger.debug("Validation failed in: " + sb.toString());
                throw new ConstraintViolationException(
                    "Validation failed: " + sb.toString(), violations);
            }

            isValid = isValid && violations.isEmpty();
        }

        return isValid;
    }
}
