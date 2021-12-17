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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.parties;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The type Guest private information mongo db value.
 */
public class GuestPrivateInformationMongoDbValue {

    @Field("dateOfBirth")
    private String dateOfBirth;

    @Field("licencePlateNumber")
    private String licencePlateNumber;

    @Field("companyStreet")
    private String companyStreet;

    @Field("companyCity")
    private String companyCity;

    @Field("companyPostCode")
    private String companyPostCode;

    @Field("acceptedDocument")
    private String acceptedDocument;

    /**
     * Instantiates a new Guest private information mongo db value.
     */
    public GuestPrivateInformationMongoDbValue() {
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
     * Sets date of birth.
     *
     * @param dateOfBirth the date of birth
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
     * Sets licence plate number.
     *
     * @param licencePlateNumber the licence plate number
     */
    public void setLicencePlateNumber(String licencePlateNumber) {
        this.licencePlateNumber = licencePlateNumber;
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
     * Sets company street.
     *
     * @param companyStreet the company street
     */
    public void setCompanyStreet(String companyStreet) {
        this.companyStreet = companyStreet;
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
     * Sets company city.
     *
     * @param companyCity the company city
     */
    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
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
     * Sets company post code.
     *
     * @param companyPostCode the company post code
     */
    public void setCompanyPostCode(String companyPostCode) {
        this.companyPostCode = companyPostCode;
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
     * Sets accepted document.
     *
     * @param acceptedDocument the accepted document
     */
    public void setAcceptedDocument(String acceptedDocument) {
        this.acceptedDocument = acceptedDocument;
    }
}
