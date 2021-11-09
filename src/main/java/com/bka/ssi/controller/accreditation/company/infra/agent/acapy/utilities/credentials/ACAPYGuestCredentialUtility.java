package com.bka.ssi.controller.accreditation.company.infra.agent.acapy.utilities.credentials;

import com.bka.ssi.controller.accreditation.acapy_client.model.CredAttrSpec;
import com.bka.ssi.controller.accreditation.acapy_client.model.CredentialPreview;
import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.GuestCredential;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.GuestPrivateInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;
import com.bka.ssi.controller.accreditation.company.infra.agent.acapy.utilities.ACAPYCredentialUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ACAPYGuestCredentialUtility implements ACAPYCredentialUtility<GuestCredential> {

    private Logger logger;

    public ACAPYGuestCredentialUtility(Logger logger) {
        this.logger = logger;
    }

    @Override
    public CredentialPreview createCredentialPreview(GuestCredential input) {

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

        List<String> emails = contactInformation.getEmails();

        attributesItem = new CredAttrSpec();
        attributesItem.name("email");
        attributesItem.value(emails.get(0));
        credentialPreview.addAttributesItem(attributesItem);

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

        attributesItem = new CredAttrSpec();
        attributesItem.name("companyName");
        attributesItem.value(input.getCompanyName());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("typeOfVisit");
        attributesItem.value(input.getTypeOfVisit());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("location");
        attributesItem.value(input.getLocation());
        credentialPreview.addAttributesItem(attributesItem);

        ValidityTimeframe validityTimeframe = input.getValidityTimeframe();

        attributesItem = new CredAttrSpec();
        attributesItem.name("validFrom");
        attributesItem.value(
            validityTimeframe.getValidFrom().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("validUntil");
        attributesItem.value(
            validityTimeframe.getValidUntil().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("invitedBy");
        attributesItem.value(input.getInvitedBy());
        credentialPreview.addAttributesItem(attributesItem);

        GuestPrivateInformation privateInformation = input.getGuestPrivateInformation();

        attributesItem = new CredAttrSpec();
        attributesItem.name("dateOfBirth");
        attributesItem.value(privateInformation.getDateOfBirth());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("licensePlateNumber");
        attributesItem.value(StringUtils.defaultString(privateInformation.getLicencePlateNumber()));
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("companyCity");
        attributesItem.value(privateInformation.getCompanyCity());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("companyStreet");
        attributesItem.value(privateInformation.getCompanyStreet());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("companyPostCode");
        attributesItem.value(privateInformation.getCompanyPostCode());
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("referenceBasisId");
        attributesItem.value(StringUtils.defaultString(input.getReferenceBasisId()));
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("referenceEmployeeId");
        attributesItem.value("");
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("companyProofOfOwnership");
        attributesItem.value("");
        credentialPreview.addAttributesItem(attributesItem);

        attributesItem = new CredAttrSpec();
        attributesItem.name("dataEncryptionAlgorithm");
        attributesItem.value("");
        credentialPreview.addAttributesItem(attributesItem);

        return credentialPreview;
    }
}