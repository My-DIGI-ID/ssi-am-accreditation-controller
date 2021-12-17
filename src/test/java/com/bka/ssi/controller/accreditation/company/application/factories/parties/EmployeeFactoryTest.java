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

import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.EmployeeInputDto;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.ValidationService;
import com.bka.ssi.controller.accreditation.company.application.utilities.CsvBuilder;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.testutilities.party.employee.EmployeeInputDtoBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class EmployeeFactoryTest {

    @Mock
    private CsvBuilder csvBuilder;

    @Mock
    private ValidationService validationService;

    @Mock
    private MultipartFile multipartFile;

    private EmployeeFactory employeeFactory;

    private EmployeeInputDtoBuilder employeeInputDtoBuilder;

    private Logger logger = LoggerFactory.getLogger(EmployeeFactoryTest.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeFactory = new EmployeeFactory(csvBuilder, validationService, logger);
        employeeInputDtoBuilder = new EmployeeInputDtoBuilder();
    }

    @Test
    void createFromDto() throws Exception {
        EmployeeInputDto dto = employeeInputDtoBuilder.buildEmployeeInputDto();

        Employee employee = employeeFactory.create(dto, "username");

        Assertions.assertEquals(dto.getTitle(),
            employee.getCredentialOffer().getCredential().getPersona().getTitle());
        Assertions.assertEquals(dto.getFirstName(),
            employee.getCredentialOffer().getCredential().getPersona().getFirstName());
        Assertions.assertEquals(dto.getLastName(),
            employee.getCredentialOffer().getCredential().getPersona().getLastName());
        Assertions.assertEquals(dto.getEmail(),
            employee.getCredentialOffer().getCredential().getContactInformation().getEmails()
                .get(0));
        Assertions.assertEquals(dto.getPrimaryPhoneNumber(),
            employee.getCredentialOffer().getCredential().getContactInformation().getPhoneNumbers()
                .get(0));
        Assertions.assertEquals(dto.getSecondaryPhoneNumber(),
            employee.getCredentialOffer().getCredential().getContactInformation().getPhoneNumbers()
                .get(1));
        Assertions.assertEquals(dto.getCompanyPostalCode(),
            employee.getCredentialOffer().getCredential().getEmployer().getAddress()
                .getPostalCode());
        Assertions.assertEquals(dto.getCompanyCity(),
            employee.getCredentialOffer().getCredential().getEmployer().getAddress().getCity());
        Assertions.assertEquals(dto.getCompanyStreet(),
            employee.getCredentialOffer().getCredential().getEmployer().getAddress().getStreet());
        Assertions.assertEquals(dto.getCompanyName(),
            employee.getCredentialOffer().getCredential().getEmployer().getName());
        Assertions.assertEquals(dto.getPosition(),
            employee.getCredentialOffer().getCredential().getPosition().getName());
        Assertions.assertEquals(dto.getEmployeeId(),
            employee.getCredentialOffer().getCredential().getEmployeeId());
        Assertions.assertEquals(dto.getEmployeeState(),
            employee.getCredentialOffer().getCredential().getEmployeeState());
        Assertions.assertEquals("username", employee.getCreatedBy());
    }

    @Test
    void createFromNullDto() throws Exception {
        Employee employee = employeeFactory.create((EmployeeInputDto) null, "username");

        Assertions.assertNull(employee);
    }

    @Test
    void createFromFile() throws Exception {
        EmployeeInputDto dto = employeeInputDtoBuilder.buildEmployeeInputDto();

        List<String> header = List.of("header");

        Mockito.when(csvBuilder.getHeaderFromCsv(Mockito.any())).thenReturn(header);

        Mockito.when(csvBuilder.validateHeader(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(csvBuilder.readCsvToListByColumnName(null, EmployeeInputDto.class,
            ',')).thenReturn(List.of(dto));

        Mockito.when(validationService.validate(Mockito.anyList())).thenReturn(true);

        List<Employee> employees = employeeFactory.create(multipartFile, "username");

        Assertions.assertEquals(1, employees.size());

        Employee employee = employees.get(0);
        Assertions.assertEquals(dto.getTitle(),
            employee.getCredentialOffer().getCredential().getPersona().getTitle());
        Assertions.assertEquals(dto.getFirstName(),
            employee.getCredentialOffer().getCredential().getPersona().getFirstName());
        Assertions.assertEquals(dto.getLastName(),
            employee.getCredentialOffer().getCredential().getPersona().getLastName());
        Assertions.assertEquals(dto.getEmail(),
            employee.getCredentialOffer().getCredential().getContactInformation().getEmails()
                .get(0));
        Assertions.assertEquals(dto.getPrimaryPhoneNumber(),
            employee.getCredentialOffer().getCredential().getContactInformation().getPhoneNumbers()
                .get(0));
        Assertions.assertEquals(dto.getSecondaryPhoneNumber(),
            employee.getCredentialOffer().getCredential().getContactInformation().getPhoneNumbers()
                .get(1));
        Assertions.assertEquals(dto.getCompanyPostalCode(),
            employee.getCredentialOffer().getCredential().getEmployer().getAddress()
                .getPostalCode());
        Assertions.assertEquals(dto.getCompanyCity(),
            employee.getCredentialOffer().getCredential().getEmployer().getAddress().getCity());
        Assertions.assertEquals(dto.getCompanyStreet(),
            employee.getCredentialOffer().getCredential().getEmployer().getAddress().getStreet());
        Assertions.assertEquals(dto.getCompanyName(),
            employee.getCredentialOffer().getCredential().getEmployer().getName());
        Assertions.assertEquals(dto.getPosition(),
            employee.getCredentialOffer().getCredential().getPosition().getName());
        Assertions.assertEquals(dto.getEmployeeId(),
            employee.getCredentialOffer().getCredential().getEmployeeId());
        Assertions.assertEquals(dto.getEmployeeState(),
            employee.getCredentialOffer().getCredential().getEmployeeState());
        Assertions.assertEquals("username", employee.getCreatedBy());
    }

    @Test
    void createFromNullFile() throws Exception {
        List<Employee> employees = employeeFactory.create((MultipartFile) null, "username");

        Assertions.assertNull(employees);
    }
}
