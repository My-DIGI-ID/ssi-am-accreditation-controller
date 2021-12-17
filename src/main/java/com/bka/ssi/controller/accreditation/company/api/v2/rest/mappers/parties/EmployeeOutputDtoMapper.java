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

package com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.parties;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.EmployeeOutputDto;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Employee output dto mapper.
 */
@Service
public class EmployeeOutputDtoMapper {

    private final Logger logger;

    /**
     * Instantiates a new Employee output dto mapper.
     *
     * @param logger the logger
     */
    public EmployeeOutputDtoMapper(Logger logger) {
        this.logger = logger;
    }

    /**
     * Entity to dto employee output dto.
     *
     * @param employee the employee
     * @return the employee output dto
     */
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
