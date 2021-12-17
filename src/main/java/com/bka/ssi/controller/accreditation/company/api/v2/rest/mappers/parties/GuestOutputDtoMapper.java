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

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.GuestOpenOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.GuestPrivateOutputDto;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Guest output dto mapper.
 */
@Service
public class GuestOutputDtoMapper {

    private final Logger logger;

    /**
     * Instantiates a new Guest output dto mapper.
     *
     * @param logger the logger
     */
    public GuestOutputDtoMapper(Logger logger) {
        this.logger = logger;
    }

    /**
     * Entity to open dto guest open output dto.
     *
     * @param guest the guest
     * @return the guest open output dto
     */
    public GuestOpenOutputDto entityToOpenDto(Guest guest) {
        logger.debug("mapping a Guest to GuestOpenOutputDto");

        if (guest == null) {
            // throw instead?
            return null;
        } else {
            GuestOpenOutputDto dto = new GuestOpenOutputDto();
            dto = this.setGuestOpenOutputDtoProperties(guest, dto);

            return dto;
        }
    }

    /**
     * Entity to private dto guest private output dto.
     *
     * @param guest the guest
     * @return the guest private output dto
     */
    public GuestPrivateOutputDto entityToPrivateDto(Guest guest) {
        logger.debug("mapping a Guest to GuestPrivateOutputDto");

        if (guest == null) {
            // throw instead?
            return null;
        } else {
            GuestPrivateOutputDto dto = new GuestPrivateOutputDto();
            dto = this.setGuestOpenOutputDtoProperties(guest, dto);
            dto = this.setGuestPrivateOutputDtoProperties(guest, dto);

            return dto;
        }
    }

    private <T extends GuestOpenOutputDto> T setGuestOpenOutputDtoProperties(Guest guest, T dto) {
        dto.setId(guest.getId());
        dto.setTitle(
            guest
                .getCredentialOffer()
                .getCredential()
                .getPersona()
                .getTitle());

        dto.setFirstName(
            guest
                .getCredentialOffer()
                .getCredential()
                .getPersona()
                .getFirstName());

        dto.setLastName(
            guest
                .getCredentialOffer()
                .getCredential()
                .getPersona()
                .getLastName());

        String email;
        List<String> emails = guest
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

        List<String> phoneNumbers = guest
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

        dto.setCompanyName(
            guest
                .getCredentialOffer()
                .getCredential()
                .getCompanyName());

        dto.setTypeOfVisit(
            guest
                .getCredentialOffer()
                .getCredential()
                .getTypeOfVisit());

        dto.setLocation(
            guest
                .getCredentialOffer()
                .getCredential()
                .getLocation());

        dto.setValidFrom(
            guest
                .getCredentialOffer()
                .getCredential()
                .getValidityTimeframe()
                .getValidFrom());

        dto.setValidUntil(
            guest
                .getCredentialOffer()
                .getCredential()
                .getValidityTimeframe()
                .getValidUntil());

        dto.setInvitedBy(
            guest
                .getCredentialOffer()
                .getCredential()
                .getInvitedBy());

        dto.setCreatedBy(guest.getCreatedBy());
        dto.setCreatedAt(guest.getCreatedAt());

        return dto;
    }

    private GuestPrivateOutputDto setGuestPrivateOutputDtoProperties(Guest guest,
        GuestPrivateOutputDto dto) {
        dto.setDateOfBirth(
            guest
                .getCredentialOffer()
                .getCredential()
                .getGuestPrivateInformation()
                .getDateOfBirth());

        dto.setLicencePlateNumber(
            guest
                .getCredentialOffer()
                .getCredential()
                .getGuestPrivateInformation()
                .getLicencePlateNumber());

        dto.setCompanyStreet(
            guest
                .getCredentialOffer()
                .getCredential()
                .getGuestPrivateInformation()
                .getCompanyStreet());

        dto.setCompanyCity(
            guest
                .getCredentialOffer()
                .getCredential()
                .getGuestPrivateInformation()
                .getCompanyCity());

        dto.setCompanyPostCode(
            guest
                .getCredentialOffer()
                .getCredential()
                .getGuestPrivateInformation()
                .getCompanyPostCode());

        dto.setAcceptedDocument(
            guest
                .getCredentialOffer()
                .getCredential()
                .getGuestPrivateInformation()
                .getAcceptedDocument());

        return dto;
    }
}
