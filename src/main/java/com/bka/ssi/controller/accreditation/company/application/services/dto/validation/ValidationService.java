/*
 * Copyright 2021 Bundesrepublik Deutschland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

/**
 * The type Validation service.
 */
@Component
public class ValidationService {

    private final Validator validator;
    private final Logger logger;

    /**
     * Instantiates a new Validation service.
     *
     * @param validator the validator
     * @param logger    the logger
     */
    public ValidationService(Validator validator, Logger logger) {
        this.validator = validator;
        this.logger = logger;
    }

    /**
     * Validate boolean.
     *
     * @param <T> the type parameter
     * @param dto the dto
     * @return the boolean
     * @throws ConstraintViolationException the constraint violation exception
     */
    public <T> boolean validate(T dto) throws ConstraintViolationException {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        return true;
    }

    /**
     * Validate boolean.
     *
     * @param <T>  the type parameter
     * @param dtos the dtos
     * @return the boolean
     * @throws BundleConstraintViolationExceptionsException the bundle constraint violation
     * exceptions exception
     */
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
