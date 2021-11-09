package com.bka.ssi.controller.accreditation.company.infra.agent.acapy.exceptions;

public class ACAPYRevokeCredentialException extends Exception {
    public ACAPYRevokeCredentialException() {
        super("Exception trying to call revocationApi.revocationRevokePost");
    }
}
