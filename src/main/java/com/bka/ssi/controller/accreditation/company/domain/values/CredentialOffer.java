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

import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.credential.Credential;

/**
 * The type Credential offer.
 *
 * @param <T> the type parameter
 */
public class CredentialOffer<T extends Credential> {

    private CredentialMetadata credentialMetadata;
    private T credential;

    /**
     * Instantiates a new Credential offer.
     *
     * @param credentialMetadata the credential metadata
     * @param credential         the credential
     */
    public CredentialOffer(CredentialMetadata credentialMetadata, T credential) {
        this.credentialMetadata = credentialMetadata;
        this.credential = credential;
    }

    /**
     * Gets credential metadata.
     *
     * @return the credential metadata
     */
    public CredentialMetadata getCredentialMetadata() {
        return credentialMetadata;
    }

    /**
     * Gets credential.
     *
     * @return the credential
     */
    public T getCredential() {
        return credential;
    }
}
