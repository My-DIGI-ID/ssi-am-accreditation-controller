package com.bka.ssi.controller.accreditation.company.domain.entities.parties;

import com.bka.ssi.controller.accreditation.company.domain.entities.Party;
import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.EmployeeCredential;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;

public class Employee extends Party<EmployeeCredential> {

    public Employee(String id,
        CredentialOffer<EmployeeCredential> credentialOffer) {
        super(id, credentialOffer);
    }

    public Employee(CredentialOffer<EmployeeCredential> credentialOffer) {
        super(credentialOffer);
    }

    @Override
    protected EmployeeCredential createEmptyCredentialForDataCleanup() {
        return null;
    }
}
