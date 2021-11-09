package com.bka.ssi.controller.accreditation.company.infra.agent.acapy.exceptions;

public class ACAPYRevocationCorrelationException extends Exception {
    public ACAPYRevocationCorrelationException() {
        super("Exception trying to call issueCredentialApi.issueCredentialRecordsCredExIdGet");
    }
}
