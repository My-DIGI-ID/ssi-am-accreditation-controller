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

package com.bka.ssi.controller.accreditation.company.domain.values;

/**
 * The type Identity management.
 */
public class IdentityManagement {

    private String reference;
    private String username;
    private String email;

    /**
     * Instantiates a new Identity management.
     *
     * @param reference the reference
     * @param username  the username
     * @param email     the email
     */
    public IdentityManagement(String reference, String username, String email) {
        this.reference = reference;
        this.username = username;
        this.email = email;
    }

    /**
     * Instantiates a new Identity management.
     *
     * @param reference the reference
     */
    public IdentityManagement(String reference) {
        this.reference = reference;
    }

    /**
     * Gets reference.
     *
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }
}
