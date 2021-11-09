package com.bka.ssi.controller.accreditation.company.domain.values;

public class ConnectionInvitation {

    private String invitationUrl;
    private String connectionId;

    public ConnectionInvitation(String invitationUrl, String connectionId) {
        this.invitationUrl = invitationUrl;
        this.connectionId = connectionId;
    }

    public String getInvitationUrl() {
        return invitationUrl;
    }

    public String getConnectionId() {
        return connectionId;
    }
}
