package com.bka.ssi.controller.accreditation.company.infra.db.mongo.mappers.parties;

import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.EmployeeCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
/*
 * TODO - Consider implicit mapping for reflected MongoDB Documents, then mapper is not needed
 */
public class EmployeeMongoDbMapper {

    @Autowired
    private Logger logger;

    public EmployeeMongoDbDocument employeeToEmployeeMongoDbDocument(Employee employee) {
        if (employee == null) {
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
                .setId(employee.getCredentialOffer().getCredentialMetadata().getId());
            credentialMetadataMongoDbValue
                .setDid(employee.getCredentialOffer().getCredentialMetadata().getDid());
            credentialMetadataMongoDbValue
                .setType(employee.getCredentialOffer().getCredentialMetadata().getType());

            CredentialOfferMongoDbValue<EmployeeCredentialMongoDbValue>
                credentialOfferMongoDbValue =
                new CredentialOfferMongoDbValue<>();
            credentialOfferMongoDbValue.setCredentialMetadata(credentialMetadataMongoDbValue);
            credentialOfferMongoDbValue.setCredential(employeeCredentialMongoDbValue);

            EmployeeMongoDbDocument employeeMongoDBDocument = new EmployeeMongoDbDocument();
            employeeMongoDBDocument.setId(employee.getId());
            employeeMongoDBDocument.setCredentialOffer(credentialOfferMongoDbValue);

            return employeeMongoDBDocument;
        }
    }

    public Employee employeeMongoDbDocumentToEmployee(
        EmployeeMongoDbDocument employeeMongoDbDocument) {
        if (employeeMongoDbDocument == null) {
            return null;
        } else {

            Persona persona =
                new Persona(
                    employeeMongoDbDocument.getCredentialOffer().getCredential().getPersona()
                        .getTitle(),
                    employeeMongoDbDocument.getCredentialOffer().getCredential().getPersona()
                        .getFirstName(),
                    employeeMongoDbDocument.getCredentialOffer().getCredential().getPersona()
                        .getLastName());

            ContactInformation contactInformation =
                new ContactInformation(employeeMongoDbDocument.getCredentialOffer().getCredential()
                    .getContactInformation().getEmails(),
                    employeeMongoDbDocument.getCredentialOffer().getCredential()
                        .getContactInformation().getPhoneNumbers());

            IdentityManagement identityManagement =
                new IdentityManagement(employeeMongoDbDocument.getCredentialOffer().getCredential()
                    .getIdentityManagement().getReference(),
                    employeeMongoDbDocument.getCredentialOffer().getCredential()
                        .getIdentityManagement().getUsername(),
                    employeeMongoDbDocument.getCredentialOffer().getCredential()
                        .getIdentityManagement().getEmail()
                );

            Address employerAddress =
                new Address(
                    employeeMongoDbDocument.getCredentialOffer().getCredential().getEmployer()
                        .getAddress().getPostalCode(),
                    employeeMongoDbDocument.getCredentialOffer().getCredential().getEmployer()
                        .getAddress().getCity(),
                    employeeMongoDbDocument.getCredentialOffer().getCredential().getEmployer()
                        .getAddress().getStreet());

            Employer employer =
                new Employer(
                    employeeMongoDbDocument.getCredentialOffer().getCredential().getEmployer()
                        .getName(), employerAddress,
                    employeeMongoDbDocument.getCredentialOffer().getCredential().getEmployer()
                        .getSubject(),
                    employeeMongoDbDocument.getCredentialOffer().getCredential().getEmployer()
                        .getProofOfOwnership());

            Position position =
                new Position(
                    employeeMongoDbDocument.getCredentialOffer().getCredential().getPosition()
                        .getName());

            EmployeeCredential employeeCredential =
                new EmployeeCredential(
                    employeeMongoDbDocument.getCredentialOffer().getCredential().getEmployeeId(),
                    employeeMongoDbDocument.getCredentialOffer().getCredential().getEmployeeState(),
                    persona,
                    contactInformation, identityManagement, employer, position);

            CredentialMetadata credentialMetadata =
                new CredentialMetadata(
                    employeeMongoDbDocument.getCredentialOffer().getCredentialMetadata().getId(),
                    employeeMongoDbDocument.getCredentialOffer().getCredentialMetadata().getType(),
                    employeeMongoDbDocument.getCredentialOffer().getCredentialMetadata().getDid(),
                    employeeMongoDbDocument.getCredentialOffer().getCredentialMetadata()
                        .getPartyCreated());

            CredentialOffer<EmployeeCredential> credentialOffer =
                new CredentialOffer<>(credentialMetadata, employeeCredential);

            Employee employee = new Employee(employeeMongoDbDocument.getId(), credentialOffer);

            return employee;
        }
    }
}
