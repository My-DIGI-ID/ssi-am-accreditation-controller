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

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.abstractions.CredentialMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.ContactInformationMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.PersonaMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.ValidityTimeframeDbValue;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The type Guest credential mongo db value.
 */
public class GuestCredentialMongoDbValue extends CredentialMongoDbValue {

    @Field("validityTimeframe")
    private ValidityTimeframeDbValue validityTimeframe;

    @Field("persona")
    private PersonaMongoDbValue persona;

    @Field("contactInformation")
    private ContactInformationMongoDbValue contactInformation;

    @Field("companyName")
    private String companyName;

    @Field("typeOfVisit")
    private String typeOfVisit;

    @Field("location")
    private String location;

    @Field("invitedBy")
    private String invitedBy;

    @Field("referenceBasisId")
    private String referenceBasisId;

    @Field("guestPrivateInformation")
    private GuestPrivateInformationMongoDbValue guestPrivateInformation;

    /**
     * Gets validity timeframe.
     *
     * @return the validity timeframe
     */
    public ValidityTimeframeDbValue getValidityTimeframe() {
        return validityTimeframe;
    }

    /**
     * Sets validity timeframe.
     *
     * @param validityTimeframe the validity timeframe
     */
    public void setValidityTimeframe(
        ValidityTimeframeDbValue validityTimeframe) {
        this.validityTimeframe = validityTimeframe;
    }

    /**
     * Gets persona.
     *
     * @return the persona
     */
    public PersonaMongoDbValue getPersona() {
        return persona;
    }

    /**
     * Sets persona.
     *
     * @param persona the persona
     */
    public void setPersona(
        PersonaMongoDbValue persona) {
        this.persona = persona;
    }

    /**
     * Gets contact information.
     *
     * @return the contact information
     */
    public ContactInformationMongoDbValue getContactInformation() {
        return contactInformation;
    }

    /**
     * Sets contact information.
     *
     * @param contactInformation the contact information
     */
    public void setContactInformation(
        ContactInformationMongoDbValue contactInformation) {
        this.contactInformation = contactInformation;
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
     * Gets reference basis id.
     *
     * @return the reference basis id
     */
    public String getReferenceBasisId() {
        return referenceBasisId;
    }

    /**
     * Sets reference basis id.
     *
     * @param referenceBasisId the reference basis id
     */
    public void setReferenceBasisId(String referenceBasisId) {
        this.referenceBasisId = referenceBasisId;
    }

    /**
     * Gets guest private information.
     *
     * @return the guest private information
     */
    public GuestPrivateInformationMongoDbValue getGuestPrivateInformation() {
        return guestPrivateInformation;
    }

    /**
     * Sets guest private information.
     *
     * @param guestPrivateInformation the guest private information
     */
    public void setGuestPrivateInformation(
        GuestPrivateInformationMongoDbValue guestPrivateInformation) {
        this.guestPrivateInformation = guestPrivateInformation;
    }
}
