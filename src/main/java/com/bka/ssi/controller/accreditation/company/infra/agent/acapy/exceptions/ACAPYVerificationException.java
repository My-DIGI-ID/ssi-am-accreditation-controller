package com.bka.ssi.controller.accreditation.company.infra.agent.acapy.exceptions;

public class ACAPYVerificationException extends Exception {
    public ACAPYVerificationException() {
        super("Exception trying to call presentProofV10Api.presentProofSendRequestPost");
    }
}
