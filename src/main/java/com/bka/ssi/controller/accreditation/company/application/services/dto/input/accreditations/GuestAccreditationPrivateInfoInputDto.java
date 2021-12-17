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

package com.bka.ssi.controller.accreditation.company.application.services.dto.input.accreditations;

import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.common.NoForbiddenCharacters;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.common.PhoneNumber;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.common.PostalCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The type Guest accreditation private info input dto.
 */
public class GuestAccreditationPrivateInfoInputDto {

    @Size(max = 50)
    @NoForbiddenCharacters
    private String licencePlateNumber;

    @Size(max = 50)
    @NotNull
    @NotEmpty
    @NoForbiddenCharacters
    private String companyStreet;

    @Size(max = 50)
    @NotNull
    @NotEmpty
    @NoForbiddenCharacters
    private String companyCity;

    @Size(max = 50)
    @NoForbiddenCharacters
    @NotNull
    @NotEmpty
    private String companyPostCode;

    @Size(max = 50)
    @NoForbiddenCharacters
    private String acceptedDocument;

    @Size(max = 50)
    @PhoneNumber
    @NotEmpty
    @NotNull
    private String primaryPhoneNumber;

    @Size(max = 50)
    @PhoneNumber
    private String secondaryPhoneNumber;

    /**
     * Gets licence plate number.
     *
     * @return the licence plate number
     */
    public String getLicencePlateNumber() {
        return licencePlateNumber;
    }

    /**
     * Gets company street.
     *
     * @return the company street
     */
    public String getCompanyStreet() {
        return companyStreet;
    }

    /**
     * Gets company city.
     *
     * @return the company city
     */
    public String getCompanyCity() {
        return companyCity;
    }

    /**
     * Gets company post code.
     *
     * @return the company post code
     */
    public String getCompanyPostCode() {
        return companyPostCode;
    }

    /**
     * Gets accepted document.
     *
     * @return the accepted document
     */
    public String getAcceptedDocument() {
        return acceptedDocument;
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
}
