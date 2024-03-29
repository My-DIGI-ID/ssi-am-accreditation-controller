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
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.GuestInputDto;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.ValidationService;
import com.bka.ssi.controller.accreditation.company.application.utilities.CsvBuilder;
import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.GuestCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.enums.CredentialType;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialMetadata;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Guest factory.
 */
@Component
public class GuestFactory implements PartyFactory<GuestInputDto, Guest> {

    /* ToDo - Set header and separator per env? */
    private final List<String> expectedGuestCsvHeader = Arrays.asList("title", "firstName",
        "lastName", "email", "primaryPhoneNumber", "secondaryPhoneNumber", "companyName",
        "typeOfVisit", "location", "validFrom", "validUntil");
    private final char csvSeparator = ',';

    private CsvBuilder csvBuilder;
    private ValidationService validationService;
    private Logger logger;

    /**
     * Instantiates a new Guest factory.
     *
     * @param csvBuilder        the csv builder
     * @param validationService the validation service
     * @param logger            the logger
     */
    public GuestFactory(
        CsvBuilder csvBuilder,
        ValidationService validationService, Logger logger) {
        this.csvBuilder = csvBuilder;
        this.validationService = validationService;
        this.logger = logger;
    }

    /* ToDo - Throw AlreadyExistsException in case employee exists - in service */

    @Override
    public Guest create(GuestInputDto input, String userName) throws Exception {
        logger.debug("Creating a guest from GuestInputDto");

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
                input.getValidFrom(),
                input.getValidUntil()
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

            CredentialMetadata credentialMetadata = new CredentialMetadata(CredentialType.GUEST);
            CredentialOffer<GuestCredential> credentialOffer =
                new CredentialOffer<>(credentialMetadata, guestCredential);

            Guest guest = new Guest(credentialOffer, userName, ZonedDateTime.now());

            return guest;
        }
    }

    @Override
    public List<Guest> create(MultipartFile input, String userName) throws Exception {
        logger.debug("Creating guests from csv");

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
                        guests.add(this.create(dto, userName));
                    }
                }
            }

            return guests;
        }
    }
}
