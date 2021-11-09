package com.bka.ssi.controller.accreditation.company.domain.values;

import com.bka.ssi.controller.accreditation.company.domain.enums.CredentialType;

import java.time.ZonedDateTime;

/* ToDo - needs refactoring with respect to used properties */
public class CredentialMetadata {

    private String issuedBy;
    private ZonedDateTime issuedAt;
    private ZonedDateTime partyPersonalDataDeleted;
    private CredentialType credentialType;

    public CredentialMetadata(String issuedBy, ZonedDateTime issuedAt,
        ZonedDateTime partyPersonalDataDeleted,
        CredentialType credentialType) {
        this.issuedBy = issuedBy;
        this.issuedAt = issuedAt;
        this.partyPersonalDataDeleted = partyPersonalDataDeleted;
        this.credentialType = credentialType;
    }

    public CredentialMetadata(String issuedBy, ZonedDateTime issuedAt,
        CredentialType credentialType) {
        this.issuedBy = issuedBy;
        this.issuedAt = issuedAt;
        this.credentialType = credentialType;
    }

    public CredentialMetadata(String issuedBy, CredentialType credentialType) {
        this.issuedBy = issuedBy;
        this.credentialType = credentialType;
    }

    public CredentialMetadata(CredentialType credentialType) {
        this.credentialType = credentialType;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public ZonedDateTime getIssuedAt() {
        return issuedAt;
    }

    public ZonedDateTime getPartyPersonalDataDeleted() {
        return partyPersonalDataDeleted;
    }

    public CredentialType getCredentialType() {
        return credentialType;
    }
}
