package com.bka.ssi.controller.accreditation.company.infra.agent.acapy.utilities;

import com.bka.ssi.controller.accreditation.acapy_client.model.CredentialPreview;
import com.bka.ssi.controller.accreditation.company.aop.configuration.agents.CredentialsConfiguration;
import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.EmployeeCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.GuestCredential;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;
import com.bka.ssi.controller.accreditation.company.infra.agent.acapy.utilities.credentials.ACAPYEmployeeCredentialUtility;
import com.bka.ssi.controller.accreditation.company.infra.agent.acapy.utilities.credentials.ACAPYGuestCredentialUtility;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

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

    public CredentialPreview buildCredPreview(
        CredentialOffer credentialOffer) {

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
