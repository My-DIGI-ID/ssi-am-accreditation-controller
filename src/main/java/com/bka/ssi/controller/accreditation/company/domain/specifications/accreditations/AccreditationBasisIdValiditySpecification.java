package com.bka.ssi.controller.accreditation.company.domain.specifications.accreditations;

import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import com.bka.ssi.controller.accreditation.company.domain.enums.GuestAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.specifications.Specification;

public class AccreditationBasisIdValiditySpecification implements Specification<Accreditation> {

    @Override
    public Boolean isSatisfiedBy(Accreditation accreditation) {
        return accreditation.getStatus() == GuestAccreditationStatus.BASIS_ID_VALID;
    }
}
