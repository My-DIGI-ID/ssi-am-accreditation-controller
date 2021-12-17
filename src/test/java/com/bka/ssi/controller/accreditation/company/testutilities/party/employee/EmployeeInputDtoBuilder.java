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

package com.bka.ssi.controller.accreditation.company.testutilities.party.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.EmployeeInputDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class EmployeeInputDtoBuilder {

    private final String json;

    public String title;
    public String firstName;
    public String lastName;
    public String email;
    public String primaryPhoneNumber;
    public String secondaryPhoneNumber;
    public String employeeId;
    public String employeeState;
    public String position;
    public String companyName;
    public String companyStreet;
    public String companyPostalCode;
    public String companyCity;

    public EmployeeInputDtoBuilder() {
        this.json =
            "{\"title\":\"%1$s\", \"firstName\":\"%2$s\",\"lastName\":\"%3$s\"," +
                "\"email\":\"%4$s\",\"primaryPhoneNumber\":\"%5$s\", " +
                "\"secondaryPhoneNumber\":\"%6$s\"," +
                "\"employeeId\":\"%7$s\", \"employeeState\":\"%8$s\", \"position\":\"%9$s\", " +
                "\"companyName\":\"%10$s\", \"companyStreet\":\"%11$s\", " +
                "\"companyPostalCode\":\"%12$s\", \"companyCity\":\"%13$s\"}";
    }

    public EmployeeInputDto build() {
        String concatenated = String.format(this.json, this.title, this.firstName, this.lastName,
            this.email, this.primaryPhoneNumber, this.secondaryPhoneNumber, this.employeeId,
            this.employeeState, this.position, this.companyName, this.companyStreet,
            this.companyPostalCode, this.companyCity);

        ObjectMapper objectMapper = new ObjectMapper();
        EmployeeInputDto dto = new EmployeeInputDto();

        try {
            dto = objectMapper.readValue(concatenated, EmployeeInputDto.class);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            fail(e.getMessage());
        }

        return dto;
    }

    public void reset() {
        this.title = null;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.primaryPhoneNumber = null;
        this.secondaryPhoneNumber = null;
        this.employeeId = null;
        this.employeeState = null;
        this.position = null;
        this.companyName = null;
        this.companyStreet = null;
        this.companyPostalCode = null;
        this.companyCity = null;
    }

    public EmployeeInputDto buildEmployeeInputDto() {
        this.title = this.title != null ? this.title : "title";
        this.firstName = this.firstName != null ? this.firstName : "firstName";
        this.lastName = this.lastName != null ? this.lastName : "lastName";
        this.email = this.email != null ? this.email : "email@email.xyz";
        this.primaryPhoneNumber =
            this.primaryPhoneNumber != null ? this.primaryPhoneNumber : "0123456789";
        this.secondaryPhoneNumber =
            this.secondaryPhoneNumber != null ? this.secondaryPhoneNumber : "9876543210";
        this.employeeId = this.employeeId != null ? this.employeeId : "employeeId";
        this.employeeState = this.employeeState != null ? this.employeeState : "INTERNAL";
        this.position = this.position != null ? this.position : "position";
        this.companyName = this.companyName != null ? this.companyName : "companyName";
        this.companyStreet = this.companyStreet != null ? this.companyStreet : "companyStreet";
        this.companyPostalCode =
            this.companyPostalCode != null ? this.companyPostalCode : "10015";
        this.companyCity = this.companyCity != null ? this.companyCity : "companyCity";

        return this.build();
    }

    @Test
    void buildEmployeeInputDtoTest() {
        EmployeeInputDto dto = this.buildEmployeeInputDto();

        assertEquals(this.title, dto.getTitle());
        assertEquals(this.firstName, dto.getFirstName());
        assertEquals(this.lastName, dto.getLastName());
        assertEquals(this.email, dto.getEmail());
        assertEquals(this.primaryPhoneNumber, dto.getPrimaryPhoneNumber());
        assertEquals(this.secondaryPhoneNumber, dto.getSecondaryPhoneNumber());
        assertEquals(this.employeeId, dto.getEmployeeId());
        assertEquals(this.employeeState, dto.getEmployeeState());
        assertEquals(this.position, dto.getPosition());
        assertEquals(this.companyName, dto.getCompanyName());
        assertEquals(this.companyStreet, dto.getCompanyStreet());
        assertEquals(this.companyPostalCode, dto.getCompanyPostalCode());
        assertEquals(this.companyCity, dto.getCompanyCity());

        this.reset();
    }
}
