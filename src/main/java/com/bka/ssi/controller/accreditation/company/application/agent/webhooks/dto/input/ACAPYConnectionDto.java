package com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public String getConnectionId() {
        return connectionId;
    }

    public String getState() {
        return state;
    }

    public String getMyId() {
        return myId;
    }

    public String getTheirId() {
        return theirId;
    }

    public String getTheirLabel() {
        return theirLabel;
    }

    public String getTheirRole() {
        return theirRole;
    }

    public String getInboundConnectionId() {
        return inboundConnectionId;
    }

    public String getInitiator() {
        return initiator;
    }

    public String getInvitationKey() {
        return invitationKey;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getRoutingState() {
        return routingState;
    }

    public String getAccept() {
        return accept;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getInvitationMode() {
        return invitationMode;
    }

    public String getAlias() {
        return alias;
    }
}
