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

package com.bka.ssi.controller.accreditation.company.domain.entities;

import java.time.ZonedDateTime;

import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.common.Entity;
import com.bka.ssi.controller.accreditation.company.domain.enums.AccreditationStatus;

/**
 * The type Accreditation.
 *
 * @param <T> the type parameter
 * @param <R> the type parameter
 */
public abstract class Accreditation<T extends Party, R extends AccreditationStatus>
    extends Entity {

    /**
     * The Status.
     */
    protected R status;
    /**
     * The Invited by.
     */
    protected String invitedBy;
    /**
     * The Invited at.
     */
    protected ZonedDateTime invitedAt;

    /**
     * The Invitation url.
     */
    /* ToDo -Have following invitation fields in an invitation value object, see mongodb document */
    protected String invitationUrl;
    /**
     * The Invitation email.
     */
    protected String invitationEmail;
    /**
     * The Invitation qr code.
     */
    protected String invitationQrCode;
    private T party;

    /**
     * Instantiates a new Accreditation.
     *
     * @param party     the party
     * @param status    the status
     * @param invitedBy the invited by
     * @param invitedAt the invited at
     */
    public Accreditation(T party, R status, String invitedBy, ZonedDateTime invitedAt) {
        super(null);

        this.party = party;
        this.status = status;
        this.invitedBy = invitedBy;
        this.invitedAt = invitedAt;
    }

    /**
     * Instantiates a new Accreditation.
     *
     * @param id        the id
     * @param party     the party
     * @param status    the status
     * @param invitedBy the invited by
     * @param invitedAt the invited at
     */
    public Accreditation(String id, T party, R status, String invitedBy, ZonedDateTime invitedAt) {
        super(id);

        this.party = party;
        this.status = status;
        this.invitedBy = invitedBy;
        this.invitedAt = invitedAt;
    }

    /**
     * Instantiates a new Accreditation.
     *
     * @param id               the id
     * @param party            the party
     * @param status           the status
     * @param invitedBy        the invited by
     * @param invitedAt        the invited at
     * @param invitationUrl    the invitation url
     * @param invitationEmail  the invitation email
     * @param invitationQrCode the invitation qr code
     */
    public Accreditation(String id, T party, R status, String invitedBy, ZonedDateTime invitedAt,
        String invitationUrl, String invitationEmail, String invitationQrCode) {
        super(id);
        this.party = party;
        this.status = status;
        this.invitationUrl = invitationUrl;
        this.invitationEmail = invitationEmail;
        this.invitationQrCode = invitationQrCode;
        this.invitedBy = invitedBy;
        this.invitedAt = invitedAt;
    }

    /**
     * Gets party.
     *
     * @return the party
     */
    public T getParty() {
        return this.party;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public R getStatus() {
        return this.status;
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
     * Gets invited at.
     *
     * @return the invited at
     */
    public ZonedDateTime getInvitedAt() {
        return invitedAt;
    }

    /**
     * Gets invitation url.
     *
     * @return the invitation url
     */
    public String getInvitationUrl() {
        return invitationUrl;
    }

    /**
     * Gets invitation email.
     *
     * @return the invitation email
     */
    public String getInvitationEmail() {
        return invitationEmail;
    }

    /**
     * Gets invitation qr code.
     *
     * @return the invitation qr code
     */
    public String getInvitationQrCode() {
        return invitationQrCode;
    }
}
