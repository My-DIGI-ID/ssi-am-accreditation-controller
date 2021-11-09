package com.bka.ssi.controller.accreditation.company.infra.agent.acapy.exceptions;

public class ACAPYConnectionInvitationException extends Exception {
    public ACAPYConnectionInvitationException() {
        super("Exception trying to call connectionApi.connectionsCreateInvitationPost");
    }
}
