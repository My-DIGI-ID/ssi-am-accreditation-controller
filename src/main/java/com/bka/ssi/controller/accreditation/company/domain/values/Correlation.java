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
 * The type Correlation.
 */
public class Correlation {

    /* ToDo - differentiate between different Correlation and have this as abstract class, e.g.
        verificationCorrelation, issuanceCorrelation, revocationCorrelation */

    private String connectionId;
    private String threadId;
    private String presentationExchangeId;
    private String credentialRevocationRegistryId;
    private String credentialRevocationId;

    /**
     * Instantiates a new Correlation.
     *
     * @param connectionId                   the connection id
     * @param threadId                       the thread id
     * @param presentationExchangeId         the presentation exchange id
     * @param credentialRevocationRegistryId the credential revocation registry id
     * @param credentialRevocationId         the credential revocation id
     */
    public Correlation(String connectionId, String threadId, String presentationExchangeId,
        String credentialRevocationRegistryId, String credentialRevocationId) {
        this.connectionId = connectionId;
        this.threadId = threadId;
        this.presentationExchangeId = presentationExchangeId;
        this.credentialRevocationRegistryId = credentialRevocationRegistryId;
        this.credentialRevocationId = credentialRevocationId;
    }

    /**
     * Instantiates a new Correlation.
     *
     * @param connectionId           the connection id
     * @param threadId               the thread id
     * @param presentationExchangeId the presentation exchange id
     */
    public Correlation(String connectionId, String threadId, String presentationExchangeId) {
        this.connectionId = connectionId;
        this.threadId = threadId;
        this.presentationExchangeId = presentationExchangeId;
    }

    /**
     * Instantiates a new Correlation.
     *
     * @param connectionId the connection id
     */
    public Correlation(String connectionId) {
        this.connectionId = connectionId;
    }

    /**
     * Instantiates a new Correlation.
     */
    public Correlation() {
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
     * Gets thread id.
     *
     * @return the thread id
     */
    public String getThreadId() {
        return threadId;
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
     * Gets credential revocation registry id.
     *
     * @return the credential revocation registry id
     */
    public String getCredentialRevocationRegistryId() {
        return credentialRevocationRegistryId;
    }

    /**
     * Gets credential revocation id.
     *
     * @return the credential revocation id
     */
    public String getCredentialRevocationId() {
        return credentialRevocationId;
    }
}
