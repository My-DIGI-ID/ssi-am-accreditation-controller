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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.documents;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.accreditations.InvitationMongoDbValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZonedDateTime;

/**
 * The type Accreditation mongo db document.
 */
public abstract class AccreditationMongoDbDocument {

    @Id
    private String id;

    @Field("partyId")
    private String partyId;

    @Field("status")
    private String status;

    @Field("invitedBy")
    private String invitedBy;

    @Field("invitedAt")
    private ZonedDateTime invitedAt;

    @Field("invitation")
    private InvitationMongoDbValue invitation;

    /**
     * Instantiates a new Accreditation mongo db document.
     */
    public AccreditationMongoDbDocument() {
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
     * Gets party id.
     *
     * @return the party id
     */
    public String getPartyId() {
        return partyId;
    }

    /**
     * Sets party id.
     *
     * @param partyId the party id
     */
    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
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
     * Gets invited at.
     *
     * @return the invited at
     */
    public ZonedDateTime getInvitedAt() {
        return invitedAt;
    }

    /**
     * Sets invited at.
     *
     * @param invitedAt the invited at
     */
    public void setInvitedAt(ZonedDateTime invitedAt) {
        this.invitedAt = invitedAt;
    }

    /**
     * Gets invitation.
     *
     * @return the invitation
     */
    public InvitationMongoDbValue getInvitation() {
        return invitation;
    }

    /**
     * Sets invitation.
     *
     * @param invitation the invitation
     */
    public void setInvitation(
        InvitationMongoDbValue invitation) {
        this.invitation = invitation;
    }
}
