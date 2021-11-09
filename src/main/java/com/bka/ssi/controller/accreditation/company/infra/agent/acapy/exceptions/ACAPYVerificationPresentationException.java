package com.bka.ssi.controller.accreditation.company.infra.agent.acapy.exceptions;

public class ACAPYVerificationPresentationException extends Exception {
    public ACAPYVerificationPresentationException() {
        super("Exception trying to call presentProofV10Api.presentProofRecordsPresExIdGet");
    }
}
