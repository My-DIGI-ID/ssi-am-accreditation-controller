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

package com.bka.ssi.controller.accreditation.company.testutilities.accreditation.guest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.bka.ssi.controller.accreditation.company.application.services.dto.input.accreditations.GuestAccreditationPrivateInfoInputDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GuestAccreditationPrivateInfoInputDtoBuilder {

    private final String json;

    public String licencePlateNumber;
    public String companyStreet;
    public String companyCity;
    public String companyPostCode;
    public String acceptedDocument;
    public String primaryPhoneNumber;
    public String secondaryPhoneNumber;

    public GuestAccreditationPrivateInfoInputDtoBuilder() {
        this.json =
            "{\"licencePlateNumber\":\"%1$s\", \"companyStreet\":\"%2$s\"," +
                "\"companyCity\":\"%3$s\"," +
                "\"companyPostCode\":\"%4$s\",\"acceptedDocument\":\"%5$s\", " +
                "\"primaryPhoneNumber\":\"%6$s\", \"secondaryPhoneNumber\":\"%7$s\"}";
    }

    public GuestAccreditationPrivateInfoInputDto build() {
        String concatenated =
            String.format(this.json, this.licencePlateNumber, this.companyStreet, this.companyCity,
                this.companyPostCode, this.acceptedDocument, this.primaryPhoneNumber,
                this.secondaryPhoneNumber);

        ObjectMapper objectMapper = new ObjectMapper();
        GuestAccreditationPrivateInfoInputDto dto = new GuestAccreditationPrivateInfoInputDto();

        try {
            dto = objectMapper.readValue(concatenated, GuestAccreditationPrivateInfoInputDto.class);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            fail(e.getMessage());
        }

        return dto;
    }

    public void reset() {
        this.licencePlateNumber = null;
        this.companyStreet = null;
        this.companyCity = null;
        this.companyPostCode = null;
        this.acceptedDocument = null;
        this.primaryPhoneNumber = null;
        this.secondaryPhoneNumber = null;
    }

    public GuestAccreditationPrivateInfoInputDto buildGuestAccreditationPrivateInfoInputDto() {
        this.licencePlateNumber =
            this.licencePlateNumber != null ? this.licencePlateNumber : "licencePlateNumber";
        this.companyStreet = this.companyStreet != null ? this.companyStreet : "companyStreet";
        this.companyCity = this.companyCity != null ? this.companyCity : "companyCity";
        this.companyPostCode =
            this.companyPostCode != null ? this.companyPostCode : "10015";
        this.acceptedDocument =
            this.acceptedDocument != null ? this.acceptedDocument : "acceptedDocument";
        this.primaryPhoneNumber =
            this.primaryPhoneNumber != null ? this.primaryPhoneNumber : "0123456789";
        this.secondaryPhoneNumber =
            this.secondaryPhoneNumber != null ? this.secondaryPhoneNumber : "9876543210";

        return this.build();
    }

    @Test
    void buildGuestAccreditationPrivateInfoInputDtoTest() {
        GuestAccreditationPrivateInfoInputDto dto =
            this.buildGuestAccreditationPrivateInfoInputDto();

        assertEquals(this.licencePlateNumber, dto.getLicencePlateNumber());
        assertEquals(this.companyStreet, dto.getCompanyStreet());
        assertEquals(this.companyCity, dto.getCompanyCity());
        assertEquals(this.companyPostCode, dto.getCompanyPostCode());
        assertEquals(this.acceptedDocument, dto.getAcceptedDocument());
        assertEquals(this.primaryPhoneNumber, dto.getPrimaryPhoneNumber());
        assertEquals(this.secondaryPhoneNumber, dto.getSecondaryPhoneNumber());

        this.reset();
    }
}
