package com.bka.ssi.controller.accreditation.company.domain.entities;

import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.common.Entity;
import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.credential.Credential;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.UpdatingPartyWithoutIdentityException;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialMetadata;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;

import java.util.Date;

public abstract class Party<T extends Credential> extends Entity {

    protected CredentialOffer<T> credentialOffer;

    abstract protected T createEmptyCredentialForDataCleanup();

    public Party(String id, CredentialOffer<T> credentialOffer) {
        super(id);

        this.credentialOffer = credentialOffer;
    }

    public Party(CredentialOffer<T> credentialOffer) {
        super(null);

        this.credentialOffer = credentialOffer;
    }

    public Party<T> updateWithNewPartyData(Party<T> newPartyDate)
        throws UpdatingPartyWithoutIdentityException {
        if (this.id == null) {
            throw new UpdatingPartyWithoutIdentityException();
        } else {
            this.credentialOffer = newPartyDate.credentialOffer;
        }

        return this;
    }

    /*
     * Personal information should not be stored long term
     * Any correlation information (non-sensitive only) needed to revoke credentials
     * should be stored in CredentialsMetadata
     */
    public void removeCredentialFromCredentialOffer() {
        CredentialMetadata newCredentialMetadata = new CredentialMetadata(
            this.credentialOffer.getCredentialMetadata().getId(),
            this.credentialOffer.getCredentialMetadata().getType(),
            this.credentialOffer.getCredentialMetadata().getDid(),
            this.credentialOffer.getCredentialMetadata().getPartyCreated(),
            new Date()
        );

        T cleanedCredential = this.createEmptyCredentialForDataCleanup();

        this.credentialOffer =
            new CredentialOffer<T>(newCredentialMetadata, cleanedCredential);
    }

    public CredentialOffer<T> getCredentialOffer() {
        return credentialOffer;
    }
}
