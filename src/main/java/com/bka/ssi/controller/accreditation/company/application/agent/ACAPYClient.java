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

package com.bka.ssi.controller.accreditation.company.application.agent;

import com.bka.ssi.controller.accreditation.company.domain.values.BasisIdPresentation;
import com.bka.ssi.controller.accreditation.company.domain.values.ConnectionInvitation;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;

/**
 * The interface Acapy client.
 */
public interface ACAPYClient {

    /**
     * Create connection invitation connection invitation.
     *
     * @param accreditationId the accreditation id
     * @return the connection invitation
     * @throws Exception the exception
     */
    ConnectionInvitation createConnectionInvitation(String accreditationId) throws Exception;

    /**
     * Verify basis id correlation.
     *
     * @param connectionId the connection id
     * @return the correlation
     * @throws Exception the exception
     */
    Correlation verifyBasisId(String connectionId) throws Exception;

    /**
     * Gets basis id presentation.
     *
     * @param presentationExchangeId the presentation exchange id
     * @return the basis id presentation
     * @throws Exception the exception
     */
    BasisIdPresentation getBasisIdPresentation(String presentationExchangeId) throws Exception;

    /**
     * Issue credential correlation.
     *
     * @param credentialOffer the credential offer
     * @param connectionId    the connection id
     * @return the correlation
     * @throws Exception the exception
     */
    Correlation issueCredential(CredentialOffer credentialOffer, String connectionId)
        throws Exception;

    /**
     * Gets revocation correlation.
     *
     * @param credentialExchangeId the credential exchange id
     * @return the revocation correlation
     * @throws Exception the exception
     */
    Correlation getRevocationCorrelation(String credentialExchangeId) throws Exception;

    /**
     * Revoke credential.
     *
     * @param credentialRevocationRegistryId the credential revocation registry id
     * @param credentialRevocationId         the credential revocation id
     * @throws Exception the exception
     */
    void revokeCredential(String credentialRevocationRegistryId, String credentialRevocationId)
        throws Exception;
}
