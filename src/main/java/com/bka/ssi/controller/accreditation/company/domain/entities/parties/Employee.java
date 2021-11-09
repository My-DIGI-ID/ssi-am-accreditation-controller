package com.bka.ssi.controller.accreditation.company.domain.entities.parties;

import com.bka.ssi.controller.accreditation.company.domain.entities.Party;
import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.EmployeeCredential;
import com.bka.ssi.controller.accreditation.company.domain.enums.CredentialType;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialMetadata;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;

import java.time.ZonedDateTime;

public class Employee extends Party<EmployeeCredential> {

    public Employee(String id,
        CredentialOffer<EmployeeCredential> credentialOffer, String createdBy,
        ZonedDateTime createAt) {
        super(id, credentialOffer, createdBy, createAt);
    }

    public Employee(CredentialOffer<EmployeeCredential> credentialOffer, String createdBy,
        ZonedDateTime createAt) {
        super(credentialOffer, createdBy, createAt);
    }

    @Override
    public Party<EmployeeCredential> removeCredentialFromCredentialOffer() {
        CredentialMetadata newCredentialMetadata = new CredentialMetadata(
            this.credentialOffer.getCredentialMetadata().getIssuedBy(),
            this.credentialOffer.getCredentialMetadata().getIssuedAt(),
            ZonedDateTime.now(),
            CredentialType.EMPLOYEE
        );

        EmployeeCredential cleanedCredential = (EmployeeCredential) this.credentialOffer
            .getCredential().createEmptyCredentialForDataCleanup();

        this.credentialOffer = new CredentialOffer<>(newCredentialMetadata, cleanedCredential);

        return this;
    }

    public Employee addIssuedByAndIssuedAtToCredentialMetadata(String issuedBy) {
        CredentialMetadata credentialMetadata = new CredentialMetadata(
            issuedBy,
            ZonedDateTime.now(),
            CredentialType.EMPLOYEE
        );

        this.credentialOffer = new CredentialOffer<>(credentialMetadata,
            this.credentialOffer.getCredential());

        return this;
    }
}
