package com.bka.ssi.controller.accreditation.company.domain.entities;

import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.common.Entity;
import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.credential.Credential;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.UpdatingPartyWithoutIdentityException;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;

import java.time.ZonedDateTime;

public abstract class Party<T extends Credential> extends Entity {

    protected CredentialOffer<T> credentialOffer;
    protected String createdBy;
    protected ZonedDateTime createdAt;

    abstract public Party<T> removeCredentialFromCredentialOffer();

    public Party(String id, CredentialOffer<T> credentialOffer, String createdBy,
        ZonedDateTime createdAt) {
        super(id);

        this.credentialOffer = credentialOffer;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public Party(CredentialOffer<T> credentialOffer, String createdBy, ZonedDateTime createdAt) {
        super(null);

        this.credentialOffer = credentialOffer;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public Party<T> updateWithNewPartyData(Party<T> newParty)
        throws UpdatingPartyWithoutIdentityException {
        if (this.id == null) {
            throw new UpdatingPartyWithoutIdentityException();
        }
        this.credentialOffer = newParty.credentialOffer;
        this.createdBy = newParty.createdBy;
        this.createdAt = newParty.createdAt;

        return this;
    }

    public CredentialOffer<T> getCredentialOffer() {
        return credentialOffer;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
