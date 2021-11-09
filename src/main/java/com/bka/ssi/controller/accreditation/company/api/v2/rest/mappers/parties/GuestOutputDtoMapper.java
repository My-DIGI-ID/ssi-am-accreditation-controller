package com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.parties;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.GuestOpenOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.GuestPrivateOutputDto;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestOutputDtoMapper {

    private final Logger logger;

    public GuestOutputDtoMapper(Logger logger) {
        this.logger = logger;
    }

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
