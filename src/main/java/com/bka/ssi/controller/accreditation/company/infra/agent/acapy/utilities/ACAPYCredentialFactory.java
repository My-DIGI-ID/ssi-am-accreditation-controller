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

package com.bka.ssi.controller.accreditation.company.infra.agent.acapy.utilities;

import com.bka.ssi.controller.accreditation.company.aop.configuration.agents.CredentialsConfiguration;
import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.EmployeeCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.GuestCredential;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;
import com.bka.ssi.controller.accreditation.company.infra.agent.acapy.utilities.credentials.ACAPYEmployeeCredentialUtility;
import com.bka.ssi.controller.accreditation.company.infra.agent.acapy.utilities.credentials.ACAPYGuestCredentialUtility;
import io.github.my_digi_id.acapy_client.model.CredentialPreview;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * The type Acapy credential factory.
 */
@Component
public class ACAPYCredentialFactory {

    private final CredentialsConfiguration credentialsConfiguration;
    private final ACAPYGuestCredentialUtility guestCredentialUtility;
    private final ACAPYEmployeeCredentialUtility employeeCredentialUtility;
    private Logger logger;

    private ACAPYCredentialFactory(
        CredentialsConfiguration credentialsConfiguration,
        ACAPYGuestCredentialUtility guestCredentialUtility,
        ACAPYEmployeeCredentialUtility employeeCredentialUtility,
        Logger logger) {

        this.credentialsConfiguration = credentialsConfiguration;
        this.guestCredentialUtility = guestCredentialUtility;
        this.employeeCredentialUtility = employeeCredentialUtility;
        this.logger = logger;
    }

    /**
     * Gets credential definition id.
     *
     * @param credentialOffer the credential offer
     * @return the credential definition id
     */
    public String getCredentialDefinitionId(
        CredentialOffer credentialOffer) {
        switch (credentialOffer.getCredentialMetadata().getCredentialType()) {
            case EMPLOYEE:
                return credentialsConfiguration.getEmployeeCredentialDefinitionId();
            case GUEST:
                return credentialsConfiguration.getGuestCredentialDefinitionId();
            default:
                logger.error("Undefined credential type");
                throw new IllegalArgumentException();
        }
    }

    /**
     * Build cred preview credential preview.
     *
     * @param credentialOffer the credential offer
     * @return the credential preview
     */
    public CredentialPreview buildCredPreview(CredentialOffer credentialOffer) {

        switch (credentialOffer.getCredentialMetadata().getCredentialType()) {
            case EMPLOYEE:
                return employeeCredentialUtility.createCredentialPreview(
                    (EmployeeCredential) credentialOffer.getCredential());
            case GUEST:
                return guestCredentialUtility.createCredentialPreview(
                    (GuestCredential) credentialOffer.getCredential());
            default:
                logger.error("Undefined credential type");

                throw new IllegalArgumentException();
        }
    }
}
