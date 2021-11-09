package com.bka.ssi.controller.accreditation.company.infra.agent.acapy.utilities.credentials;

import com.bka.ssi.controller.accreditation.acapy_client.model.CredAttrSpec;
import com.bka.ssi.controller.accreditation.acapy_client.model.CredentialPreview;
import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.EmployeeCredential;
import com.bka.ssi.controller.accreditation.company.domain.values.Address;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.IdentityManagement;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.Position;
import com.bka.ssi.controller.accreditation.company.infra.agent.acapy.utilities.ACAPYCredentialUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ACAPYEmployeeCredentialUtility implements ACAPYCredentialUtility<EmployeeCredential> {

    private Logger logger;

    public ACAPYEmployeeCredentialUtility(Logger logger) {
        this.logger = logger;
    }

    @Override
    public CredentialPreview createCredentialPreview(EmployeeCredential input) {

        logger.debug("Building credential preview for issuing");

        CredentialPreview credentialPreview = new CredentialPreview();
        CredAttrSpec attributesItem = null;

        Persona persona = input.getPersona();

        // TODO - remove hardcoded attributes to configuration
        attributesItem = new CredAttrSpec();
        attributesItem.name("firstName");
        attributesItem.value(persona.getFirstName());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("lastName");
        attributesItem.value(persona.getLastName());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("title");
        attributesItem.value(StringUtils.defaultString(persona.getTitle()));
        credentialPreview.addAttributesItem(attributesItem);

        ContactInformation contactInformation = input.getContactInformation();

        List<String> phoneNumbers = contactInformation.getPhoneNumbers();

        attributesItem = new CredAttrSpec();
        attributesItem.name("primaryPhoneNumber");
        if (phoneNumbers != null && phoneNumbers.size() > 0) {
            attributesItem.value(phoneNumbers.get(0));
        } else {
            attributesItem.value("");
        }
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("secondaryPhoneNumber");
        if (phoneNumbers != null && phoneNumbers.size() > 1) {
            attributesItem.value(phoneNumbers.get(1));
        } else {
            attributesItem.value("");
        }
        credentialPreview.addAttributesItem(attributesItem);

        List<String> emails = contactInformation.getEmails();

        attributesItem = new CredAttrSpec();
        attributesItem.name("email");
        attributesItem.value(emails.get(0));
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("employeeState");
        attributesItem.value(input.getEmployeeState());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("employeeId");
        attributesItem.value(StringUtils.defaultString(input.getEmployeeId()));
        credentialPreview.addAttributesItem(attributesItem);

        Position position = input.getPosition();

        attributesItem = new CredAttrSpec();
        attributesItem.name("position");
        if (position != null) {
            attributesItem.value(StringUtils.defaultString(input.getPosition().getName()));
        } else {
            attributesItem.value("");
        }
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("companyName");
        attributesItem.value(input.getEmployer().getName());
        credentialPreview.addAttributesItem(attributesItem);

        Address employerAddress = input.getEmployer().getAddress();

        attributesItem = new CredAttrSpec();
        attributesItem.name("companyStreet");
        attributesItem.value(employerAddress.getStreet());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("companyCity");
        attributesItem.value(StringUtils.defaultString(employerAddress.getCity()));
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("companyPostCode");
        attributesItem.value(employerAddress.getPostalCode());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("companyProofOfOwnership");
        attributesItem.value(StringUtils.defaultString(input.getEmployer().getProofOfOwnership()));
        credentialPreview.addAttributesItem(attributesItem);

        IdentityManagement identityManagement = input.getIdentityManagement();

        attributesItem = new CredAttrSpec();
        attributesItem.name("ssoReference");
        if (identityManagement != null) {
            attributesItem.value(StringUtils.defaultString(identityManagement.getReference()));
        } else {
            attributesItem.value("");
        }
        credentialPreview.addAttributesItem(attributesItem);

        return credentialPreview;
    }
}
