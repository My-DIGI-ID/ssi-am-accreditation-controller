package com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.parties;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.EmployeeOutputDto;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeOutputDtoMapper {

    private final Logger logger;

    public EmployeeOutputDtoMapper(Logger logger) {
        this.logger = logger;
    }

    public EmployeeOutputDto entityToDto(Employee employee) {
        logger.debug("Mapping Employee to EmployeeOutputDto");

        if (employee == null) {
            return null;
        } else {
            EmployeeOutputDto dto = new EmployeeOutputDto();

            dto.setId(employee.getId());
            dto.setTitle(employee.getCredentialOffer().getCredential().getPersona().getTitle());
            dto.setFirstName(
                employee.getCredentialOffer().getCredential().getPersona().getFirstName());
            dto.setLastName(
                employee.getCredentialOffer().getCredential().getPersona().getLastName());

            String email;
            List<String> emails = employee
                .getCredentialOffer()
                .getCredential()
                .getContactInformation()
                .getEmails();

            if (emails.size() >= 1) {
                email = emails.get(0);
            } else {
                email = null;
            }

            dto.setEmail(email);

            String primaryPhoneNumber;
            String secondaryPhoneNumber;

            List<String> phoneNumbers = employee
                .getCredentialOffer()
                .getCredential()
                .getContactInformation()
                .getPhoneNumbers();

            if (phoneNumbers.size() >= 1) {
                primaryPhoneNumber = phoneNumbers.get(0);
            } else {
                primaryPhoneNumber = null;
            }

            if (phoneNumbers.size() >= 2) {
                secondaryPhoneNumber = phoneNumbers.get(1);
            } else {
                secondaryPhoneNumber = null;
            }

            dto.setPrimaryPhoneNumber(primaryPhoneNumber);
            dto.setSecondaryPhoneNumber(secondaryPhoneNumber);

            dto.setEmployeeId(employee.getCredentialOffer().getCredential().getEmployeeId());
            dto
                .setEmployeeState(employee.getCredentialOffer().getCredential().getEmployeeState());
            dto
                .setPosition(employee.getCredentialOffer().getCredential().getPosition().getName());
            dto.setCompanyName(
                employee.getCredentialOffer().getCredential().getEmployer().getName());
            dto.setCompanyStreet(
                employee.getCredentialOffer().getCredential().getEmployer().getAddress()
                    .getStreet());
            dto.setCompanyPostalCode(
                employee.getCredentialOffer().getCredential().getEmployer().getAddress()
                    .getPostalCode());
            dto.setCompanyCity(
                employee.getCredentialOffer().getCredential().getEmployer().getAddress().getCity());

            dto.setCreatedBy(employee.getCreatedBy());
            dto.setCreatedAt(employee.getCreatedAt());

            return dto;
        }
    }
}
