package com.bka.ssi.controller.accreditation.company.domain.values;

import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.credential.Credential;

public class CredentialOffer<T extends Credential> {

    private CredentialMetadata credentialMetadata;
    private T credential;

    /* TODO - Values invariants checks to be added as part of functional stories */
    public CredentialOffer(CredentialMetadata credentialMetadata, T credential) {
        this.credentialMetadata = credentialMetadata;
        this.credential = credential;
    }

    public CredentialMetadata getCredentialMetadata() {
        return credentialMetadata;
    }

    public T getCredential() {
        return credential;
    }
}
