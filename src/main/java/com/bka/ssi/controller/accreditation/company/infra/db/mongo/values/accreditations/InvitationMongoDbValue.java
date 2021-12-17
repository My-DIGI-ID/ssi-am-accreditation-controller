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

package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.accreditations;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The type Invitation mongo db value.
 */
public class InvitationMongoDbValue {

    @Field("invitationUrl")
    private String invitationUrl;

    @Field("invitationEmail")
    private String invitationEmail;

    @Field("invitationQrCode")
    private String invitationQrCode;

    /**
     * Instantiates a new Invitation mongo db value.
     */
    public InvitationMongoDbValue() {
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
