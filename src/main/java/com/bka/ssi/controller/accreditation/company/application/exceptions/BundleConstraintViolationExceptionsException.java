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

package com.bka.ssi.controller.accreditation.company.application.exceptions;

import java.util.List;
import javax.validation.ConstraintViolationException;

/**
 * The type Bundle constraint violation exceptions exception.
 */
public class BundleConstraintViolationExceptionsException extends BundleExceptionsException {

    /**
     * Instantiates a new Bundle constraint violation exceptions exception.
     *
     * @param exceptions the exceptions
     */
    public BundleConstraintViolationExceptionsException(
        List<ConstraintViolationException> exceptions) {
        super(exceptions);
    }

    /**
     * Instantiates a new Bundle constraint violation exceptions exception.
     *
     * @param message    the message
     * @param exceptions the exceptions
     */
    public BundleConstraintViolationExceptionsException(String message,
        List<ConstraintViolationException> exceptions) {
        super(message, exceptions);
    }
}
