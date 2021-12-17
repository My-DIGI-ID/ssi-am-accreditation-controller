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

package com.bka.ssi.controller.accreditation.company.domain.values;

/**
 * The type Guest private information.
 */
public class GuestPrivateInformation {

    private String dateOfBirth;
    private String licencePlateNumber;
    private String companyStreet;
    private String companyCity;
    private String companyPostCode;
    private String acceptedDocument;

    /**
     * Instantiates a new Guest private information.
     *
     * @param dateOfBirth        the date of birth
     * @param licencePlateNumber the licence plate number
     * @param companyStreet      the company street
     * @param companyCity        the company city
     * @param companyPostCode    the company post code
     * @param acceptedDocument   the accepted document
     */
    public GuestPrivateInformation(String dateOfBirth, String licencePlateNumber,
        String companyStreet, String companyCity,
        String companyPostCode, String acceptedDocument) {
        this.dateOfBirth = dateOfBirth;
        this.licencePlateNumber = licencePlateNumber;
        this.companyStreet = companyStreet;
        this.companyCity = companyCity;
        this.companyPostCode = companyPostCode;
        this.acceptedDocument = acceptedDocument;
    }

    /**
     * Instantiates a new Guest private information.
     *
     * @param licencePlateNumber the licence plate number
     * @param companyStreet      the company street
     * @param companyCity        the company city
     * @param companyPostCode    the company post code
     * @param acceptedDocument   the accepted document
     */
    public GuestPrivateInformation(String licencePlateNumber, String companyStreet,
        String companyCity, String companyPostCode,
        String acceptedDocument) {
        this.licencePlateNumber = licencePlateNumber;
        this.companyStreet = companyStreet;
        this.companyCity = companyCity;
        this.companyPostCode = companyPostCode;
        this.acceptedDocument = acceptedDocument;
    }

    /**
     * Gets date of birth.
     *
     * @return the date of birth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

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
}
