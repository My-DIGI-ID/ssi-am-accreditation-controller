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

package com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.GuestOpenOutputDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

/**
 * The type Guest accreditation open output dto.
 */
public class GuestAccreditationOpenOutputDto {

    private String id;
    private GuestOpenOutputDto guest;
    private String status;
    private String invitedBy;
    private ZonedDateTime invitedAt;
    // ToDo - JsonProperty only for compatibility reasons, should be invitationUrl and invitationQrCode
    @JsonProperty("invitationLink")
    private String invitationUrl;
    private String invitationEmail;
    @JsonProperty("connectionQrCode")
    private String invitationQrCode;

    /**
     * Instantiates a new Guest accreditation open output dto.
     */
    public GuestAccreditationOpenOutputDto() {
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
     * Gets guest.
     *
     * @return the guest
     */
    public GuestOpenOutputDto getGuest() {
        return guest;
    }

    /**
     * Sets guest.
     *
     * @param guest the guest
     */
    public void setGuest(
        GuestOpenOutputDto guest) {
        this.guest = guest;
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
     * Gets invitation url.
     *
     * @return the invitation url
     */
    public String getInvitationUrl() {
        return invitationUrl;
    }

    /**
     * Sets invitation url.
     *
     * @param invitationUrl the invitation url
     */
    public void setInvitationUrl(String invitationUrl) {
        this.invitationUrl = invitationUrl;
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
     * Sets invitation email.
     *
     * @param invitationEmail the invitation email
     */
    public void setInvitationEmail(String invitationEmail) {
        this.invitationEmail = invitationEmail;
    }

    /**
     * Gets invitation qr code.
     *
     * @return the invitation qr code
     */
    public String getInvitationQrCode() {
        return invitationQrCode;
    }

    /**
     * Sets invitation qr code.
     *
     * @param invitationQrCode the invitation qr code
     */
    public void setInvitationQrCode(String invitationQrCode) {
        this.invitationQrCode = invitationQrCode;
    }
}
