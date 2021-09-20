package com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.parties;

import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.GuestCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialMetadata;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;
import com.bka.ssi.controller.accreditation.company.domain.values.GuestPrivateInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties.GuestMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.ContactInformationMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.CredentialMetadataMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.CredentialOfferMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.PersonaMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.ValidityTimeframeDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.parties.GuestCredentialMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.parties.GuestPrivateInformationMongoDbValue;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GuestMongoDbMapper {

    private Logger logger;

    public GuestMongoDbMapper(Logger logger) {
        this.logger = logger;
    }

    public GuestMongoDbDocument guestToGuestMongoDBDocument(Guest guest) {
        logger.debug("mapping a Guest to a MongoDb document");
        if (guest == null) {
            // throw instead ?
            return null;
        } else {
            GuestMongoDbDocument document = new GuestMongoDbDocument();
            CredentialOfferMongoDbValue<GuestCredentialMongoDbValue> credentialOffer =
                new CredentialOfferMongoDbValue();

            CredentialMetadataMongoDbValue credentialMeta = new CredentialMetadataMongoDbValue();
            GuestCredentialMongoDbValue credential = new GuestCredentialMongoDbValue();

            ValidityTimeframeDbValue validity = new ValidityTimeframeDbValue();

            validity.setValidFromDate(
                guest
                    .getCredentialOffer()
                    .getCredential()
                    .getValidityTimeframe()
                    .getValidFromDate());

            validity.setValidFromTime(
                guest
                    .getCredentialOffer()
                    .getCredential()
                    .getValidityTimeframe()
                    .getValidFromTime());

            validity.setValidUntilDate(
                guest
                    .getCredentialOffer()
                    .getCredential()
                    .getValidityTimeframe()
                    .getValidUntilDate());

            validity.setValidUntilTime(
                guest
                    .getCredentialOffer()
                    .getCredential()
                    .getValidityTimeframe()
                    .getValidUntilTime());

            PersonaMongoDbValue persona = new PersonaMongoDbValue();

            persona.setTitle(
                guest
                    .getCredentialOffer()
                    .getCredential()
                    .getPersona()
                    .getTitle());

            persona.setFirstName(
                guest
                    .getCredentialOffer()
                    .getCredential()
                    .getPersona()
                    .getFirstName());

            persona.setLastName(
                guest
                    .getCredentialOffer()
                    .getCredential()
                    .getPersona()
                    .getLastName());

            ContactInformationMongoDbValue contact = new ContactInformationMongoDbValue();

            contact.setEmails(
                guest
                    .getCredentialOffer()
                    .getCredential()
                    .getContactInformation()
                    .getEmails());

            contact.setPhoneNumbers(
                guest
                    .getCredentialOffer()
                    .getCredential()
                    .getContactInformation()
                    .getPhoneNumbers());

            GuestPrivateInformationMongoDbValue privateInformation =
                new GuestPrivateInformationMongoDbValue();

            if (guest.getCredentialOffer().getCredential().getGuestPrivateInformation() != null) {
                privateInformation.setDateOfBirth(
                    guest
                        .getCredentialOffer()
                        .getCredential()
                        .getGuestPrivateInformation()
                        .getDateOfBirth());

                privateInformation.setLicencePlateNumber(
                    guest
                        .getCredentialOffer()
                        .getCredential()
                        .getGuestPrivateInformation()
                        .getLicencePlateNumber());

                privateInformation.setCompanyStreet(
                    guest
                        .getCredentialOffer()
                        .getCredential()
                        .getGuestPrivateInformation()
                        .getCompanyStreet());

                privateInformation.setCompanyCity(
                    guest
                        .getCredentialOffer()
                        .getCredential()
                        .getGuestPrivateInformation()
                        .getCompanyCity());

                privateInformation.setCompanyPostCode(
                    guest
                        .getCredentialOffer()
                        .getCredential()
                        .getGuestPrivateInformation()
                        .getCompanyPostCode());

                privateInformation.setAcceptedDocument(
                    guest
                        .getCredentialOffer()
                        .getCredential()
                        .getGuestPrivateInformation()
                        .getAcceptedDocument());
            } else {
                privateInformation.setDateOfBirth("TBD");
                privateInformation.setLicencePlateNumber("TBD");
                privateInformation.setCompanyStreet("TBD");
                privateInformation.setCompanyCity("TBD");
                privateInformation.setCompanyPostCode("TBD");
                privateInformation.setAcceptedDocument("TBD");
            }

            credential.setValidityTimeframe(validity);
            credential.setPersona(persona);
            credential.setContactInformation(contact);
            credential.setGuestPrivateInformation(privateInformation);

            credential.setCompanyName(
                guest.getCredentialOffer().getCredential().getCompanyName());

            credential.setTypeOfVisit(
                guest.getCredentialOffer().getCredential().getTypeOfVisit());

            credential.setLocation(
                guest.getCredentialOffer().getCredential().getLocation());

            credential.setIssuedBy(
                guest.getCredentialOffer().getCredential().getInvitedBy());

            credential.setReferenceBasisId(
                guest.getCredentialOffer().getCredential().getReferenceBasisId());

            credentialMeta.setId(guest.getCredentialOffer().getCredentialMetadata().getId());
            credentialMeta.setDid(guest.getCredentialOffer().getCredentialMetadata().getDid());
            credentialMeta.setType(guest.getCredentialOffer().getCredentialMetadata().getType());
            credentialMeta.setPartyCreated(
                guest.getCredentialOffer().getCredentialMetadata().getPartyCreated());
            credentialMeta.setPartyPersonalDataDeleted(
                guest.getCredentialOffer().getCredentialMetadata().getPartyPersonalDataDeleted());

            credentialOffer.setCredential(credential);
            credentialOffer.setCredentialMetadata(credentialMeta);

            document.setId(guest.getId());
            document.setCredentialOffer(credentialOffer);

            return document;
        }
    }

    public Guest guestMongoDBDocumentToGuest(GuestMongoDbDocument document) {
        logger.debug("mapping a MongoDb document to a Guest");
        if (document == null) {
            // throw instead ?
            return null;
        } else {
            ValidityTimeframe validity = new ValidityTimeframe(
                document
                    .getCredentialOffer()
                    .getCredential()
                    .getValidityTimeframe()
                    .getValidFromDate(),
                document
                    .getCredentialOffer()
                    .getCredential()
                    .getValidityTimeframe()
                    .getValidFromTime(),
                document
                    .getCredentialOffer()
                    .getCredential()
                    .getValidityTimeframe()
                    .getValidUntilDate(),
                document
                    .getCredentialOffer()
                    .getCredential()
                    .getValidityTimeframe()
                    .getValidUntilTime()
            );

            Persona persona = new Persona(
                document
                    .getCredentialOffer()
                    .getCredential()
                    .getPersona()
                    .getTitle(),
                document
                    .getCredentialOffer()
                    .getCredential()
                    .getPersona()
                    .getFirstName(),
                document
                    .getCredentialOffer()
                    .getCredential()
                    .getPersona()
                    .getLastName()
            );

            List<String> emails = document
                .getCredentialOffer()
                .getCredential()
                .getContactInformation()
                .getEmails();

            List<String> phoneNumbers = document
                .getCredentialOffer()
                .getCredential()
                .getContactInformation()
                .getPhoneNumbers();

            emails = emails == null ? new ArrayList<>() : new ArrayList<>(emails);
            phoneNumbers = phoneNumbers == null ? new ArrayList<>() : new ArrayList<>(phoneNumbers);

            ContactInformation contact = new ContactInformation(emails, phoneNumbers);

            GuestPrivateInformation privateInformation = new GuestPrivateInformation(
                document
                    .getCredentialOffer()
                    .getCredential()
                    .getGuestPrivateInformation()
                    .getDateOfBirth(),
                document
                    .getCredentialOffer()
                    .getCredential()
                    .getGuestPrivateInformation()
                    .getLicencePlateNumber(),
                document
                    .getCredentialOffer()
                    .getCredential()
                    .getGuestPrivateInformation()
                    .getCompanyStreet(),
                document
                    .getCredentialOffer()
                    .getCredential()
                    .getGuestPrivateInformation()
                    .getCompanyCity(),
                document
                    .getCredentialOffer()
                    .getCredential()
                    .getGuestPrivateInformation()
                    .getCompanyPostCode(),
                document
                    .getCredentialOffer()
                    .getCredential()
                    .getGuestPrivateInformation()
                    .getAcceptedDocument()
            );

            GuestCredential credential = new GuestCredential(
                validity,
                persona,
                contact,
                document.getCredentialOffer().getCredential().getCompanyName(),
                document.getCredentialOffer().getCredential().getTypeOfVisit(),
                document.getCredentialOffer().getCredential().getLocation(),
                document.getCredentialOffer().getCredential().getIssuedBy(),
                document.getCredentialOffer().getCredential().getReferenceBasisId(),
                privateInformation
            );

            CredentialMetadata credentialMeta = new CredentialMetadata(
                document.getCredentialOffer().getCredentialMetadata().getId(),
                document.getCredentialOffer().getCredentialMetadata().getDid(),
                document.getCredentialOffer().getCredentialMetadata().getType(),
                document.getCredentialOffer().getCredentialMetadata().getPartyCreated(),
                document.getCredentialOffer().getCredentialMetadata().getPartyPersonalDataDeleted()
            );

            CredentialOffer<GuestCredential> credentialOffer = new CredentialOffer(
                credentialMeta,
                credential
            );

            Guest guest = new Guest(document.getId(), credentialOffer);

            return guest;
        }
    }
}
