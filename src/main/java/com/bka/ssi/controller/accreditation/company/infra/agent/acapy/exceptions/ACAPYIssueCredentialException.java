package com.bka.ssi.controller.accreditation.company.infra.agent.acapy.exceptions;

public class ACAPYIssueCredentialException extends Exception {
    public ACAPYIssueCredentialException() {
        super("Exception trying to call issueCredentialV10Api.issueCredentialSendOfferPost");
    }
}
