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

package com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties;

import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.common.NoForbiddenCharacters;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.common.PhoneNumber;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The type Guest input dto.
 */
public class GuestInputDto {

    @Size(max = 50)
    @NoForbiddenCharacters
    @CsvBindByName(column = "title")
    @CsvBindByPosition(position = 0)
    private String title;

    @Size(max = 50)
    @NotNull
    @NotEmpty
    @NoForbiddenCharacters
    @CsvBindByName(column = "firstName")
    @CsvBindByPosition(position = 1)
    private String firstName;

    @Size(max = 50)
    @NotNull
    @NotEmpty
    @NoForbiddenCharacters
    @CsvBindByName(column = "lastName")
    @CsvBindByPosition(position = 2)
    private String lastName;

    @Size(max = 100)
    @Email
    @NotNull
    @NotEmpty
    @CsvBindByName(column = "email")
    @CsvBindByPosition(position = 3)
    private String email;

    @Size(max = 50)
    @PhoneNumber
    @CsvBindByName(column = "primaryPhoneNumber")
    @CsvBindByPosition(position = 4)
    private String primaryPhoneNumber;

    @Size(max = 50)
    @PhoneNumber
    @CsvBindByName(column = "secondaryPhoneNumber")
    @CsvBindByPosition(position = 5)
    private String secondaryPhoneNumber;

    @Size(max = 200)
    @NotNull
    @NotEmpty
    @NoForbiddenCharacters
    @CsvBindByName(column = "companyName")
    @CsvBindByPosition(position = 6)
    private String companyName;

    @Size(max = 100)
    @NotNull
    @NotEmpty
    @NoForbiddenCharacters
    @CsvBindByName(column = "typeOfVisit")
    @CsvBindByPosition(position = 7)
    private String typeOfVisit;

    @Size(max = 100)
    @NotNull
    @NotEmpty
    @NoForbiddenCharacters
    @CsvBindByName(column = "location")
    @CsvBindByPosition(position = 8)
    private String location;

    @NotNull
    @CsvBindByName(column = "validFrom")
    @CsvBindByPosition(position = 9)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime validFrom;

    @NotNull
    @CsvBindByName(column = "validUntil")
    @CsvBindByPosition(position = 10)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime validUntil;

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets primary phone number.
     *
     * @return the primary phone number
     */
    public String getPrimaryPhoneNumber() {
        return primaryPhoneNumber;
    }

    /**
     * Gets secondary phone number.
     *
     * @return the secondary phone number
     */
    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    /**
     * Gets company name.
     *
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Gets type of visit.
     *
     * @return the type of visit
     */
    public String getTypeOfVisit() {
        return typeOfVisit;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets valid from.
     *
     * @return the valid from
     */
    public ZonedDateTime getValidFrom() {
        return validFrom;
    }

    /**
     * Gets valid until.
     *
     * @return the valid until
     */
    public ZonedDateTime getValidUntil() {
        return validUntil;
    }
}
