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
 * The type Connection invitation.
 */
public class ConnectionInvitation {

    private String invitationUrl;
    private String connectionId;

    /**
     * Instantiates a new Connection invitation.
     *
     * @param invitationUrl the invitation url
     * @param connectionId  the connection id
     */
    public ConnectionInvitation(String invitationUrl, String connectionId) {
        this.invitationUrl = invitationUrl;
        this.connectionId = connectionId;
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
     * Gets connection id.
     *
     * @return the connection id
     */
    public String getConnectionId() {
        return connectionId;
    }
}
