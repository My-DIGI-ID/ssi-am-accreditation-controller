package com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.parties;

import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.EmployeeCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.enums.CredentialType;
import com.bka.ssi.controller.accreditation.company.domain.values.Address;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialMetadata;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;
import com.bka.ssi.controller.accreditation.company.domain.values.Employer;
import com.bka.ssi.controller.accreditation.company.domain.values.IdentityManagement;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.Position;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents.parties.EmployeeMongoDbDocument;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.AddressMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.ContactInformationMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.CredentialMetadataMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.CredentialOfferMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.EmployerMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.IdentityManagementMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.PersonaMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.PositionMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.parties.EmployeeCredentialMongoDbValue;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
/*
 * TODO - Consider implicit mapping for reflected MongoDB Documents, then mapper is not needed
 */
public class EmployeeMongoDbMapper {

    private final Logger logger;

    public EmployeeMongoDbMapper(Logger logger) {
        this.logger = logger;
    }

    public EmployeeMongoDbDocument entityToDocument(Employee employee) {
        logger.debug("Mapping an employee to a MongoDb document");

        if (employee == null) {
            // throw instead ?
            return null;
        } else {

            PersonaMongoDbValue personaMongoDbValue = new PersonaMongoDbValue();
            personaMongoDbValue
                .setTitle(employee.getCredentialOffer().getCredential().getPersona().getTitle());
            personaMongoDbValue.setFirstName(
                employee.getCredentialOffer().getCredential().getPersona().getFirstName());
            personaMongoDbValue.setLastName(
                employee.getCredentialOffer().getCredential().getPersona().getLastName());

            ContactInformationMongoDbValue contactInformationMongoDbValue =
                new ContactInformationMongoDbValue();
            contactInformationMongoDbValue.setEmails(
                employee.getCredentialOffer().getCredential().getContactInformation().getEmails());
            contactInformationMongoDbValue.setPhoneNumbers(
                employee.getCredentialOffer().getCredential().getContactInformation()
                    .getPhoneNumbers());

            IdentityManagementMongoDbValue identityManagementMongoDbValue =
                new IdentityManagementMongoDbValue();
            identityManagementMongoDbValue.setEmail(
                employee.getCredentialOffer().getCredential().getIdentityManagement().getEmail());
            identityManagementMongoDbValue.setReference(
                employee.getCredentialOffer().getCredential().getIdentityManagement()
                    .getReference());
            identityManagementMongoDbValue.setUsername(
                employee.getCredentialOffer().getCredential().getIdentityManagement()
                    .getUsername());

            AddressMongoDbValue employerAddressMongoDbValue = new AddressMongoDbValue();
            employerAddressMongoDbValue.setPostalCode(
                employee.getCredentialOffer().getCredential().getEmployer().getAddress()
                    .getPostalCode());
            employerAddressMongoDbValue.setCountry(
                employee.getCredentialOffer().getCredential().getEmployer().getAddress()
                    .getCountry());
            employerAddressMongoDbValue.setCity(
                employee.getCredentialOffer().getCredential().getEmployer().getAddress().getCity());
            employerAddressMongoDbValue.setStreet(
                employee.getCredentialOffer().getCredential().getEmployer().getAddress()
                    .getStreet());
            employerAddressMongoDbValue.setHouseNumber(
                employee.getCredentialOffer().getCredential().getEmployer().getAddress()
                    .getHouseNumber());
            employerAddressMongoDbValue.setDoorNumber(
                employee.getCredentialOffer().getCredential().getEmployer().getAddress()
                    .getDoorNumber());

            EmployerMongoDbValue employerMongoDbValue = new EmployerMongoDbValue();
            employerMongoDbValue.setAddress(employerAddressMongoDbValue);
            employerMongoDbValue
                .setName(employee.getCredentialOffer().getCredential().getEmployer().getName());
            employerMongoDbValue.setSubject(
                employee.getCredentialOffer().getCredential().getEmployer().getSubject());
            employerMongoDbValue.setProofOfOwnership(
                employee.getCredentialOffer().getCredential().getEmployer().getProofOfOwnership());

            PositionMongoDbValue positionMongoDbValue = new PositionMongoDbValue();
            positionMongoDbValue
                .setName(employee.getCredentialOffer().getCredential().getPosition().getName());

            EmployeeCredentialMongoDbValue employeeCredentialMongoDbValue =
                new EmployeeCredentialMongoDbValue();
            employeeCredentialMongoDbValue
                .setEmployeeId(employee.getCredentialOffer().getCredential().getEmployeeId());
            employeeCredentialMongoDbValue
                .setEmployeeState(employee.getCredentialOffer().getCredential().getEmployeeState());
            employeeCredentialMongoDbValue.setPersona(personaMongoDbValue);
            employeeCredentialMongoDbValue.setContactInformation(contactInformationMongoDbValue);
            employeeCredentialMongoDbValue.setIdentityManagement(identityManagementMongoDbValue);
            employeeCredentialMongoDbValue.setEmployer(employerMongoDbValue);
            employeeCredentialMongoDbValue.setPosition(positionMongoDbValue);

            CredentialMetadataMongoDbValue credentialMetadataMongoDbValue =
                new CredentialMetadataMongoDbValue();
            credentialMetadataMongoDbValue
                .setIssuedBy(employee.getCredentialOffer().getCredentialMetadata().getIssuedBy());
            credentialMetadataMongoDbValue
                .setIssuedAt(employee.getCredentialOffer().getCredentialMetadata().getIssuedAt());
            credentialMetadataMongoDbValue
                .setPartyPersonalDataDeleted(employee.getCredentialOffer().getCredentialMetadata()
                    .getPartyPersonalDataDeleted());
            credentialMetadataMongoDbValue.setCredentialType(
                employee.getCredentialOffer().getCredentialMetadata().getCredentialType()
                    .getName());

            CredentialOfferMongoDbValue<EmployeeCredentialMongoDbValue>
                credentialOfferMongoDbValue =
                new CredentialOfferMongoDbValue<>();
            credentialOfferMongoDbValue.setCredentialMetadata(credentialMetadataMongoDbValue);
            credentialOfferMongoDbValue.setCredential(employeeCredentialMongoDbValue);

            EmployeeMongoDbDocument document = new EmployeeMongoDbDocument();
            document.setId(employee.getId());
            document.setCreatedBy(employee.getCreatedBy());
            document.setCreatedAt(employee.getCreatedAt());
            document.setCredentialOffer(credentialOfferMongoDbValue);

            return document;
        }
    }

    public Employee documentToEntity(EmployeeMongoDbDocument document) {
        logger.debug("Mapping a MongoDb document to an employee");

        if (document == null) {
            // throw instead ?
            return null;
        } else {

            Persona persona =
                new Persona(
                    document.getCredentialOffer().getCredential().getPersona()
                        .getTitle(),
                    document.getCredentialOffer().getCredential().getPersona()
                        .getFirstName(),
                    document.getCredentialOffer().getCredential().getPersona()
                        .getLastName());

            ContactInformation contactInformation =
                new ContactInformation(document.getCredentialOffer().getCredential()
                    .getContactInformation().getEmails(),
                    document.getCredentialOffer().getCredential()
                        .getContactInformation().getPhoneNumbers());

            IdentityManagement identityManagement =
                new IdentityManagement(document.getCredentialOffer().getCredential()
                    .getIdentityManagement().getReference(),
                    document.getCredentialOffer().getCredential()
                        .getIdentityManagement().getUsername(),
                    document.getCredentialOffer().getCredential()
                        .getIdentityManagement().getEmail()
                );

            Address employerAddress =
                new Address(
                    document.getCredentialOffer().getCredential().getEmployer()
                        .getAddress().getPostalCode(),
                    document.getCredentialOffer().getCredential().getEmployer()
                        .getAddress().getCity(),
                    document.getCredentialOffer().getCredential().getEmployer()
                        .getAddress().getStreet());

            Employer employer =
                new Employer(
                    document.getCredentialOffer().getCredential().getEmployer()
                        .getName(), employerAddress,
                    document.getCredentialOffer().getCredential().getEmployer()
                        .getSubject(),
                    document.getCredentialOffer().getCredential().getEmployer()
                        .getProofOfOwnership());

            Position position =
                new Position(
                    document.getCredentialOffer().getCredential().getPosition()
                        .getName());

            EmployeeCredential employeeCredential =
                new EmployeeCredential(
                    document.getCredentialOffer().getCredential().getEmployeeId(),
                    document.getCredentialOffer().getCredential().getEmployeeState(),
                    persona,
                    contactInformation, identityManagement, employer, position);

            CredentialMetadata credentialMetadata =
                new CredentialMetadata(
                    document.getCredentialOffer().getCredentialMetadata().getIssuedBy(),
                    document.getCredentialOffer().getCredentialMetadata().getIssuedAt(),
                    document.getCredentialOffer().getCredentialMetadata()
                        .getPartyPersonalDataDeleted(),
                    CredentialType.valueOf(
                        document.getCredentialOffer().getCredentialMetadata().getCredentialType()));

            CredentialOffer<EmployeeCredential> credentialOffer =
                new CredentialOffer<>(credentialMetadata, employeeCredential);

            Employee employee =
                new Employee(document.getId(), credentialOffer, document.getCreatedBy(),
                    document.getCreatedAt());

            return employee;
        }
    }
}
