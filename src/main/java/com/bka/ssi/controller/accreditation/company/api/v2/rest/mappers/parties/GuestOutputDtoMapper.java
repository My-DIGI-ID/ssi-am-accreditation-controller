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

    public GuestOpenOutputDto guestToGuestOpenOutputDto(
        Guest guest) {
        logger.debug("mapping a Guest to OpenOutputDto");
        if (guest == null) {
            // throw instead?
            return null;
        } else {
            GuestOpenOutputDto output = new GuestOpenOutputDto();
            output = this.setGuestOpenOutputDtoProperties(guest, output);

            return output;
        }
    }

    public GuestPrivateOutputDto guestToGuestPrivateOutputDto(Guest guest) {
        if (guest == null) {
            // throw instead?
            return null;
        } else {
            GuestPrivateOutputDto output = new GuestPrivateOutputDto();
            output = this.setGuestOpenOutputDtoProperties(guest, output);
            output = this.setGuestPrivateOutputDtoProperties(guest, output);

            return output;
        }
    }

    private <T extends GuestOpenOutputDto> T setGuestOpenOutputDtoProperties(Guest guest,
        T output) {

        output.setId(guest.getId());
        output.setTitle(
            guest
                .getCredentialOffer()
                .getCredential()
                .getPersona()
                .getTitle());

        output.setFirstName(
            guest
                .getCredentialOffer()
                .getCredential()
                .getPersona()
                .getFirstName());

        output.setLastName(
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

        output.setEmail(email);

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

        output.setPrimaryPhoneNumber(primaryPhoneNumber);
        output.setSecondaryPhoneNumber(secondaryPhoneNumber);

        output.setCompanyName(
            guest
                .getCredentialOffer()
                .getCredential()
                .getCompanyName());

        output.setTypeOfVisit(
            guest
                .getCredentialOffer()
                .getCredential()
                .getTypeOfVisit());

        output.setLocation(
            guest
                .getCredentialOffer()
                .getCredential()
                .getLocation());

        output.setValidFromDate(
            guest
                .getCredentialOffer()
                .getCredential()
                .getValidityTimeframe()
                .getValidFromDate());

        output.setValidFromTime(
            guest
                .getCredentialOffer()
                .getCredential()
                .getValidityTimeframe()
                .getValidFromTime());

        output.setValidUntilDate(
            guest
                .getCredentialOffer()
                .getCredential()
                .getValidityTimeframe()
                .getValidUntilDate());

        output.setValidUntilTime(
            guest
                .getCredentialOffer()
                .getCredential()
                .getValidityTimeframe()
                .getValidUntilTime());

        output.setIssuedBy(
            guest
                .getCredentialOffer()
                .getCredential()
                .getInvitedBy());

        return output;
    }

    private GuestPrivateOutputDto setGuestPrivateOutputDtoProperties(Guest guest,
        GuestPrivateOutputDto output) {

        output.setDateOfBirth(
            guest
                .getCredentialOffer()
                .getCredential()
                .getGuestPrivateInformation()
                .getDateOfBirth());

        output.setLicencePlateNumber(
            guest
                .getCredentialOffer()
                .getCredential()
                .getGuestPrivateInformation()
                .getLicencePlateNumber());

        output.setCompanyStreet(
            guest
                .getCredentialOffer()
                .getCredential()
                .getGuestPrivateInformation()
                .getCompanyStreet());

        output.setCompanyCity(
            guest
                .getCredentialOffer()
                .getCredential()
                .getGuestPrivateInformation()
                .getCompanyCity());

        output.setCompanyPostCode(
            guest
                .getCredentialOffer()
                .getCredential()
                .getGuestPrivateInformation()
                .getCompanyPostCode());

        output.setAcceptedDocument(
            guest
                .getCredentialOffer()
                .getCredential()
                .getGuestPrivateInformation()
                .getAcceptedDocument());

        return output;
    }
}
