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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The type Identity management mongo db value.
 */
public class IdentityManagementMongoDbValue {

    @Field("reference")
    private String reference;

    @Field("username")
    private String username;

    @Field("email")
    private String email;

    /**
     * Instantiates a new Identity management mongo db value.
     */
    public IdentityManagementMongoDbValue() {
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
     * Sets reference.
     *
     * @param reference the reference
     */
    public void setReference(String reference) {
        this.reference = reference;
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
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
