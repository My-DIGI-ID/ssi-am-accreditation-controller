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

package com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.EmployeeAccreditationOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.parties.EmployeeOutputDtoMapper;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import com.bka.ssi.controller.accreditation.company.testutilities.accreditation.employee.EmployeeAccreditationBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployeeAccreditationOutputDtoMapperTest {

    private static final Logger logger =
        LoggerFactory.getLogger(EmployeeAccreditationOutputDtoMapperTest.class);
    private static EmployeeAccreditationOutputDtoMapper employeeAccreditationOutputDtoMapper;
    private static EmployeeOutputDtoMapper employeeOutputDtoMapper;

    private static EmployeeAccreditationBuilder employeeAccreditationBuilder;

    private EmployeeAccreditation employeeAccreditation;

    @BeforeAll
    static void init() {
        employeeOutputDtoMapper = new EmployeeOutputDtoMapper(logger);
        employeeAccreditationOutputDtoMapper =
            new EmployeeAccreditationOutputDtoMapper(employeeOutputDtoMapper, logger);
        employeeAccreditationBuilder = new EmployeeAccreditationBuilder();
    }

    @BeforeEach
    void setup() {
        employeeAccreditation = employeeAccreditationBuilder.buildEmployeeAccreditation();
    }

    @Test
    public void shouldMapEntityToDto() {
        // when
        EmployeeAccreditationOutputDto employeeAccreditationOutputDto =
            employeeAccreditationOutputDtoMapper.entityToDto(employeeAccreditation);

        // then
        assertEquals(employeeAccreditation.getId(), employeeAccreditationOutputDto.getId());
        assertEquals(employeeAccreditation.getStatus().getName(),
            employeeAccreditationOutputDto.getStatus());
        assertEquals(employeeAccreditation.getInvitedBy(),
            employeeAccreditationOutputDto.getInvitedBy());
        assertEquals(employeeAccreditation.getInvitedAt(),
            employeeAccreditationOutputDto.getInvitedAt());
        assertEquals(employeeAccreditation.getInvitationUrl(),
            employeeAccreditationOutputDto.getInvitationUrl());
        assertEquals(employeeAccreditation.getInvitationEmail(),
            employeeAccreditationOutputDto.getInvitationEmail());
        assertEquals(employeeAccreditation.getInvitationQrCode(),
            employeeAccreditationOutputDto.getInvitationQrCode());

        // implicitly testing EmployeeOutputDtoMapper
        assertEquals(employeeAccreditation.getParty().getId(),
            employeeAccreditationOutputDto.getEmployee().getId());
        assertEquals(
            employeeAccreditation.getParty().getCredentialOffer().getCredential().getPersona()
                .getTitle(),
            employeeAccreditationOutputDto.getEmployee().getTitle());
        assertEquals(
            employeeAccreditation.getParty().getCredentialOffer().getCredential().getPersona()
                .getFirstName(),
            employeeAccreditationOutputDto.getEmployee().getFirstName());
        assertEquals(
            employeeAccreditation.getParty().getCredentialOffer().getCredential().getPersona()
                .getLastName(),
            employeeAccreditationOutputDto.getEmployee().getLastName());
        assertEquals(employeeAccreditation.getParty().getCredentialOffer().getCredential()
                .getContactInformation().getEmails().get(0),
            employeeAccreditationOutputDto.getEmployee().getEmail());
        assertEquals(employeeAccreditation.getParty().getCredentialOffer().getCredential()
                .getContactInformation().getPhoneNumbers().get(0),
            employeeAccreditationOutputDto.getEmployee().getPrimaryPhoneNumber());
        assertEquals(employeeAccreditation.getParty().getCredentialOffer().getCredential()
                .getContactInformation().getPhoneNumbers().get(1),
            employeeAccreditationOutputDto.getEmployee().getSecondaryPhoneNumber());
        assertEquals(
            employeeAccreditation.getParty().getCredentialOffer().getCredential().getEmployeeId(),
            employeeAccreditationOutputDto.getEmployee().getEmployeeId());
        assertEquals(employeeAccreditation.getParty().getCredentialOffer().getCredential()
                .getEmployeeState(),
            employeeAccreditationOutputDto.getEmployee().getEmployeeState());
        assertEquals(
            employeeAccreditation.getParty().getCredentialOffer().getCredential().getPosition()
                .getName(),
            employeeAccreditationOutputDto.getEmployee().getPosition());
        assertEquals(
            employeeAccreditation.getParty().getCredentialOffer().getCredential().getEmployer()
                .getName(),
            employeeAccreditationOutputDto.getEmployee().getCompanyName());
        assertEquals(
            employeeAccreditation.getParty().getCredentialOffer().getCredential().getEmployer()
                .getAddress().getPostalCode(),
            employeeAccreditationOutputDto.getEmployee().getCompanyPostalCode());
        assertEquals(
            employeeAccreditation.getParty().getCredentialOffer().getCredential().getEmployer()
                .getAddress().getCity(),
            employeeAccreditationOutputDto.getEmployee().getCompanyCity());
        assertEquals(
            employeeAccreditation.getParty().getCredentialOffer().getCredential().getEmployer()
                .getAddress().getStreet(),
            employeeAccreditationOutputDto.getEmployee().getCompanyStreet());
        assertEquals(employeeAccreditation.getParty().getCreatedBy(),
            employeeAccreditationOutputDto.getEmployee().getCreatedBy());
        assertEquals(employeeAccreditation.getParty().getCreatedAt(),
            employeeAccreditationOutputDto.getEmployee().getCreatedAt());
    }
}
