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

package com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

/**
 * The type Guest open output dto.
 */
public class GuestOpenOutputDto {

    private String id;
    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private String primaryPhoneNumber;
    private String secondaryPhoneNumber;
    private String companyName;
    private String typeOfVisit;
    private String location;
    private ZonedDateTime validFrom;
    private ZonedDateTime validUntil;
    @JsonProperty("issuedBy")
    private String invitedBy;
    private String createdBy;
    private ZonedDateTime createdAt;

    /**
     * Instantiates a new Guest open output dto.
     */
    public GuestOpenOutputDto() {
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
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
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
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
     * Sets primary phone number.
     *
     * @param primaryPhoneNumber the primary phone number
     */
    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
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
     * Sets secondary phone number.
     *
     * @param secondaryPhoneNumber the secondary phone number
     */
    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
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
     * Sets company name.
     *
     * @param companyName the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
     * Sets type of visit.
     *
     * @param typeOfVisit the type of visit
     */
    public void setTypeOfVisit(String typeOfVisit) {
        this.typeOfVisit = typeOfVisit;
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
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
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
     * Sets valid from.
     *
     * @param validFrom the valid from
     */
    public void setValidFrom(ZonedDateTime validFrom) {
        this.validFrom = validFrom;
    }

    /**
     * Gets valid until.
     *
     * @return the valid until
     */
    public ZonedDateTime getValidUntil() {
        return validUntil;
    }

    /**
     * Sets valid until.
     *
     * @param validUntil the valid until
     */
    public void setValidUntil(ZonedDateTime validUntil) {
        this.validUntil = validUntil;
    }

    /**
     * Gets invited by.
     *
     * @return the invited by
     */
    public String getInvitedBy() {
        return invitedBy;
    }

    /**
     * Sets invited by.
     *
     * @param invitedBy the invited by
     */
    public void setInvitedBy(String invitedBy) {
        this.invitedBy = invitedBy;
    }

    /**
     * Gets created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets created by.
     *
     * @param createdBy the created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
