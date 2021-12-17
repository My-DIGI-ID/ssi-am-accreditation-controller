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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Bundle exceptions exception.
 */
public class BundleExceptionsException extends Exception {
    private final List<? extends Exception> exceptions;

    /**
     * Instantiates a new Bundle exceptions exception.
     *
     * @param exceptions the exceptions
     */
    public BundleExceptionsException(List<? extends Exception> exceptions) {
        this(exceptions != null ? toString(exceptions) : null, exceptions);
    }

    /**
     * Instantiates a new Bundle exceptions exception.
     *
     * @param message    the message
     * @param exceptions the exceptions
     */
    public BundleExceptionsException(String message, List<? extends Exception> exceptions) {
        super(message);
        if (exceptions == null) {
            this.exceptions = null;
        } else {
            this.exceptions = new ArrayList<>(exceptions);
        }
    }

    /**
     * Gets exceptions.
     *
     * @return the exceptions
     */
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
