package com.bka.ssi.controller.accreditation.company.domain.specifications.accreditations;

import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import com.bka.ssi.controller.accreditation.company.domain.enums.EmployeeAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.enums.GuestAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.specifications.Specification;

public class AccreditationCompletedSpecification implements Specification<Accreditation> {

    @Override
    public Boolean isSatisfiedBy(Accreditation accreditation) {
        switch (accreditation.getParty().getCredentialOffer().getCredentialMetadata()
            .getCredentialType()) {
            case EMPLOYEE:
                return accreditation.getStatus() == EmployeeAccreditationStatus.ACCEPTED;
            case GUEST:
                return accreditation.getStatus() == GuestAccreditationStatus.ACCEPTED;
            default:
                throw new IllegalArgumentException("Undefined Credential Type");
        }
    }
}
