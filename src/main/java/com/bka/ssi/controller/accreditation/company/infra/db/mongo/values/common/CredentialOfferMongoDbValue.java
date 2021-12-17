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

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.abstractions.CredentialMongoDbValue;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The type Credential offer mongo db value.
 *
 * @param <T> the type parameter
 */
public class CredentialOfferMongoDbValue<T extends CredentialMongoDbValue> {

    @Field("credentialMetadata")
    private CredentialMetadataMongoDbValue credentialMetadata;

    @Field("credential")
    private T credential;

    /**
     * Instantiates a new Credential offer mongo db value.
     */
    public CredentialOfferMongoDbValue() {
    }

    /**
     * Gets credential metadata.
     *
     * @return the credential metadata
     */
    public CredentialMetadataMongoDbValue getCredentialMetadata() {
        return credentialMetadata;
    }

    /**
     * Sets credential metadata.
     *
     * @param credentialMetadata the credential metadata
     */
    public void setCredentialMetadata(
        CredentialMetadataMongoDbValue credentialMetadata) {
        this.credentialMetadata = credentialMetadata;
    }

    /**
     * Gets credential.
     *
     * @return the credential
     */
    public T getCredential() {
        return credential;
    }

    /**
     * Sets credential.
     *
     * @param credential the credential
     */
    public void setCredential(T credential) {
        this.credential = credential;
    }
}
