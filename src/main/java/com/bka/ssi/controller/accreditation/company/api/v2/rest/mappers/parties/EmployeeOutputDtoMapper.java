package com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.parties;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.EmployeeOutputDto;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeOutputDtoMapper {

    public EmployeeOutputDto employeeToEmployeePartyOutputDto(
        Employee employee) {
        if (employee == null) {
            return null;
        } else {
            EmployeeOutputDto output = new EmployeeOutputDto();

            output.setId(employee.getId());
            output.setTitle(employee.getCredentialOffer().getCredential().getPersona().getTitle());
            output.setFirstName(
                employee.getCredentialOffer().getCredential().getPersona().getFirstName());
            output.setLastName(
                employee.getCredentialOffer().getCredential().getPersona().getLastName());
            output.setEmail(
                employee.getCredentialOffer().getCredential().getContactInformation().getEmails()
                    .get(0));

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

            output.setEmail(email);

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

            output.setPrimaryPhoneNumber(primaryPhoneNumber);
            output.setSecondaryPhoneNumber(secondaryPhoneNumber);

            output.setEmployeeId(employee.getCredentialOffer().getCredential().getEmployeeId());
            output
                .setEmployeeState(employee.getCredentialOffer().getCredential().getEmployeeState());
            output
                .setPosition(employee.getCredentialOffer().getCredential().getPosition().getName());
            output.setCompanyName(
                employee.getCredentialOffer().getCredential().getEmployer().getName());
            output.setCompanyStreet(
                employee.getCredentialOffer().getCredential().getEmployer().getAddress()
                    .getStreet());
            output.setCompanyPostalCode(
                employee.getCredentialOffer().getCredential().getEmployer().getAddress()
                    .getPostalCode());
            output.setCompanyCity(
                employee.getCredentialOffer().getCredential().getEmployer().getAddress().getCity());

            return output;
        }
    }
}
