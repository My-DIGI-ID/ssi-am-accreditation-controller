package com.bka.ssi.controller.accreditation.company.application.factories.parties;

import com.bka.ssi.controller.accreditation.company.application.factories.Factory;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.GuestInputDto;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.ValidationService;
import com.bka.ssi.controller.accreditation.company.application.utilities.CsvBuilder;
import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.GuestCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialMetadata;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class GuestFactory implements Factory<GuestInputDto, Guest> {

    /* ToDo - Set header and separator per env? */
    private final List<String> expectedGuestCsvHeader = Arrays.asList("title", "firstName",
        "lastName", "email", "primaryPhoneNumber", "secondaryPhoneNumber", "companyName",
        "typeOfVisit", "location", "validFromDate", "validFromTime", "validUntilDate",
        "validUntilTime");
    private final char csvSeparator = ',';

    private CsvBuilder csvBuilder;
    private ValidationService validationService;
    private Logger logger;

    public GuestFactory(
        CsvBuilder csvBuilder,
        ValidationService validationService, Logger logger) {
        this.csvBuilder = csvBuilder;
        this.validationService = validationService;
        this.logger = logger;
    }

    /* ToDo - Throw AlreadyExistsException in case employee exists - in service */

    @Override
    public Guest create(GuestInputDto input) {
        logger.debug("creating a Guest instance from GuestInputDto");
        if (input == null) {
            // throw exception instead?
            return null;
        } else {
            Persona persona = input.getTitle() == null
                ? new Persona(input.getFirstName(), input.getLastName())
                : new Persona(input.getTitle(), input.getFirstName(), input.getLastName());

            List<String> emails = Arrays.asList(input.getEmail());
            List<String> phoneNumbers = new ArrayList<>();
            if (input.getPrimaryPhoneNumber() != null) {
                phoneNumbers.add(input.getPrimaryPhoneNumber());
            }
            if (input.getSecondaryPhoneNumber() != null) {
                phoneNumbers.add(input.getSecondaryPhoneNumber());
            }
            ContactInformation contactInformation = new ContactInformation(emails, phoneNumbers);

            ValidityTimeframe validity = new ValidityTimeframe(
                input.getValidFromDate(),
                input.getValidFromTime(),
                input.getValidUntilDate(),
                input.getValidUntilTime()
            );

            GuestCredential guestCredential = new GuestCredential(
                validity,
                persona,
                contactInformation,
                input.getCompanyName(),
                input.getTypeOfVisit(),
                input.getLocation(),
                "TBD"
            );

            CredentialMetadata credentialMetadata = new CredentialMetadata(new Date());
            CredentialOffer<GuestCredential> credentialOffer =
                new CredentialOffer<>(credentialMetadata, guestCredential);

            Guest guest = new Guest(credentialOffer);

            return guest;
        }
    }

    @Override
    public List<Guest> create(MultipartFile input) throws Exception {
        if (input == null) {
            // throw exception instead?
            return null;
        } else {
            List<Guest> guests = new ArrayList<>();

            List<String> actualGuestCsvHeader =
                this.csvBuilder.getHeaderFromCsv(input.getInputStream());

            if (this.csvBuilder.validateHeader(this.expectedGuestCsvHeader,
                actualGuestCsvHeader)) {
                List<GuestInputDto> dtos =
                    this.csvBuilder.readCsvToListByColumnName(input.getInputStream(),
                        GuestInputDto.class, this.csvSeparator);

                if (this.validationService.validate(dtos)) {
                    for (GuestInputDto dto : dtos) {
                        guests.add(this.create(dto));
                    }
                }
            }

            return guests;
        }
    }
}
