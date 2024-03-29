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

package com.bka.ssi.controller.accreditation.company.application.factories.parties;

import com.bka.ssi.controller.accreditation.company.application.factories.PartyFactory;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.EmployeeInputDto;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.ValidationService;
import com.bka.ssi.controller.accreditation.company.application.utilities.CsvBuilder;
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
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Employee factory.
 */
@Component
public class EmployeeFactory implements PartyFactory<EmployeeInputDto, Employee> {

    /* ToDo - Set header and separator per env? */
    private final List<String> expectedEmployeeCsvHeader = Arrays.asList("title", "firstName",
        "lastName", "email", "primaryPhoneNumber", "secondaryPhoneNumber", "employeeId",
        "employeeState", "position", "companyName", "companyStreet", "companyPostalCode",
        "companyCity");
    private final char csvSeparator = ',';

    private CsvBuilder csvBuilder;
    private ValidationService validationService;
    private Logger logger;

    /**
     * Instantiates a new Employee factory.
     *
     * @param csvBuilder        the csv builder
     * @param validationService the validation service
     * @param logger            the logger
     */
    public EmployeeFactory(
        CsvBuilder csvBuilder,
        ValidationService validationService, Logger logger) {
        this.csvBuilder = csvBuilder;
        this.validationService = validationService;
        this.logger = logger;
    }

    /* ToDo - Throw AlreadyExistsException in case employee exists */

    @Override
    public Employee create(EmployeeInputDto input, String userName) throws Exception {
        logger.debug("Creating an employee from EmployeeInputDto");

        if (input == null) {
            // throw exception instead?
            return null;
        } else {
            Persona persona = input.getTitle() == null ?
                new Persona(input.getFirstName(), input.getLastName()) :
                new Persona(input.getTitle(), input.getFirstName(), input.getLastName());

            List<String> emails = Arrays.asList(input.getEmail());
            List<String> phoneNumbers = new ArrayList<>();
            if (input.getPrimaryPhoneNumber() != null) {
                phoneNumbers.add(input.getPrimaryPhoneNumber());
            }
            if (input.getSecondaryPhoneNumber() != null) {
                phoneNumbers.add(input.getSecondaryPhoneNumber());
            }
            ContactInformation contactInformation = new ContactInformation(emails, phoneNumbers);

            /* ToDo - Get reference, username and email by JWT token handler */
            IdentityManagement identityManagement = new IdentityManagement("", "", "");

            Address employerAddress =
                new Address(input.getCompanyPostalCode(), input.getCompanyCity(),
                    input.getCompanyStreet());

            /* ToDo - companySubject and proofOfOwnership? */
            Employer employer = new Employer(input.getCompanyName(), employerAddress);

            Position position = input.getPosition() == null ?
                new Position() :
                new Position(input.getPosition());

            EmployeeCredential employeeCredential = input.getEmployeeId() == null ?
                new EmployeeCredential(input.getEmployeeState(),
                    persona, contactInformation, identityManagement, employer, position) :
                new EmployeeCredential(input.getEmployeeId(), input.getEmployeeState(),
                    persona, contactInformation, identityManagement, employer, position);

            CredentialMetadata credentialMetadata = new CredentialMetadata(CredentialType.EMPLOYEE);
            CredentialOffer<EmployeeCredential> credentialOffer =
                new CredentialOffer<>(credentialMetadata, employeeCredential);

            Employee employee = new Employee(credentialOffer, userName, ZonedDateTime.now());

            return employee;
        }
    }

    @Override
    public List<Employee> create(MultipartFile input, String userName) throws Exception {
        logger.debug("Creating employees from csv");

        if (input == null) {
            // throw exception instead?
            return null;
        } else {
            List<Employee> employees = new ArrayList<>();

            List<String> actualEmployeeCsvHeader =
                this.csvBuilder.getHeaderFromCsv(input.getInputStream());

            if (this.csvBuilder.validateHeader(this.expectedEmployeeCsvHeader,
                actualEmployeeCsvHeader)) {
                List<EmployeeInputDto> dtos =
                    this.csvBuilder.readCsvToListByColumnName(input.getInputStream(),
                        EmployeeInputDto.class, this.csvSeparator);

                if (this.validationService.validate(dtos)) {
                    for (EmployeeInputDto dto : dtos) {
                        employees.add(this.create(dto, userName));
                    }
                }
            }

            return employees;
        }
    }
}
