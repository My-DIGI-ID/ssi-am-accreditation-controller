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
 * The type Correlation mongo db document.
 */
public class CorrelationMongoDbDocument {

    @Field("connectionId")
    private String connectionId;

    @Field("threadId")
    private String threadId;

    @Field("presentationExchangeId")
    private String presentationExchangeId;

    @Field("credentialRevocationRegistryId")
    private String credentialRevocationRegistryId;

    @Field("credentialRevocationId")
    private String credentialRevocationId;

    /**
     * Instantiates a new Correlation mongo db document.
     */
    public CorrelationMongoDbDocument() {
    }

    /**
     * Gets connection id.
     *
     * @return the connection id
     */
    public String getConnectionId() {
        return connectionId;
    }

    /**
     * Sets connection id.
     *
     * @param connectionId the connection id
     */
    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    /**
     * Gets thread id.
     *
     * @return the thread id
     */
    public String getThreadId() {
        return threadId;
    }

    /**
     * Sets thread id.
     *
     * @param threadId the thread id
     */
    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    /**
     * Gets presentation exchange id.
     *
     * @return the presentation exchange id
     */
    public String getPresentationExchangeId() {
        return presentationExchangeId;
    }

    /**
     * Sets presentation exchange id.
     *
     * @param presentationExchangeId the presentation exchange id
     */
    public void setPresentationExchangeId(String presentationExchangeId) {
        this.presentationExchangeId = presentationExchangeId;
    }

    /**
     * Gets credential revocation registry id.
     *
     * @return the credential revocation registry id
     */
    public String getCredentialRevocationRegistryId() {
        return credentialRevocationRegistryId;
    }

    /**
     * Sets credential revocation registry id.
     *
     * @param credentialRevocationRegistryId the credential revocation registry id
     */
    public void setCredentialRevocationRegistryId(String credentialRevocationRegistryId) {
        this.credentialRevocationRegistryId = credentialRevocationRegistryId;
    }

    /**
     * Gets credential revocation id.
     *
     * @return the credential revocation id
     */
    public String getCredentialRevocationId() {
        return credentialRevocationId;
    }

    /**
     * Sets credential revocation id.
     *
     * @param credentialRevocationId the credential revocation id
     */
    public void setCredentialRevocationId(String credentialRevocationId) {
        this.credentialRevocationId = credentialRevocationId;
    }
}
