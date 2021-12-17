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

package com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Acapy connection dto.
 */
public class ACAPYConnectionDto {

    /*
    connection_id: the unique connection identifier
    state: init / invitation / request / response / active / error / inactive
    (invitation/request/response/active/completed)
    my_did: the DID this agent is using in the connection
    their_did: the DID the other agent in the connection is using
    their_label: a connection label provided by the other agent
    their_role: a role assigned to the other agent in the connection
    inbound_connection_id: a connection identifier for the related inbound routing connection
    initiator: self / external / multiuse
    invitation_key: a verification key used to identify the source connection invitation
    request_id: the @id property from the connection request message
    routing_state: none / request / active / error
    accept: manual / auto
    error_msg: the most recent error message
    invitation_mode: once / multi
    alias: a local alias for the connection record
     */

    @JsonProperty("connection_id")
    private String connectionId;

    @JsonProperty("state")
    private String state;

    @JsonProperty("my_did")
    private String myId;

    @JsonProperty("their_did")
    private String theirId;

    @JsonProperty("their_label")
    private String theirLabel;

    @JsonProperty("their_role")
    private String theirRole;

    @JsonProperty("inbound_connection_id")
    private String inboundConnectionId;

    @JsonProperty("initiator")
    private String initiator;

    @JsonProperty("invitation_key")
    private String invitationKey;

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("routing_state")
    private String routingState;

    @JsonProperty("accept")
    private String accept;

    @JsonProperty("error_msg")
    private String errorMessage;

    @JsonProperty("invitation_mode")
    private String invitationMode;

    @JsonProperty("alias")
    private String alias;

    /**
     * Gets connection id.
     *
     * @return the connection id
     */
    public String getConnectionId() {
        return connectionId;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Gets my id.
     *
     * @return the my id
     */
    public String getMyId() {
        return myId;
    }

    /**
     * Gets their id.
     *
     * @return the their id
     */
    public String getTheirId() {
        return theirId;
    }

    /**
     * Gets their label.
     *
     * @return the their label
     */
    public String getTheirLabel() {
        return theirLabel;
    }

    /**
     * Gets their role.
     *
     * @return the their role
     */
    public String getTheirRole() {
        return theirRole;
    }

    /**
     * Gets inbound connection id.
     *
     * @return the inbound connection id
     */
    public String getInboundConnectionId() {
        return inboundConnectionId;
    }

    /**
     * Gets initiator.
     *
     * @return the initiator
     */
    public String getInitiator() {
        return initiator;
    }

    /**
     * Gets invitation key.
     *
     * @return the invitation key
     */
    public String getInvitationKey() {
        return invitationKey;
    }

    /**
     * Gets request id.
     *
     * @return the request id
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Gets routing state.
     *
     * @return the routing state
     */
    public String getRoutingState() {
        return routingState;
    }

    /**
     * Gets accept.
     *
     * @return the accept
     */
    public String getAccept() {
        return accept;
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Gets invitation mode.
     *
     * @return the invitation mode
     */
    public String getInvitationMode() {
        return invitationMode;
    }

    /**
     * Gets alias.
     *
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }
}
