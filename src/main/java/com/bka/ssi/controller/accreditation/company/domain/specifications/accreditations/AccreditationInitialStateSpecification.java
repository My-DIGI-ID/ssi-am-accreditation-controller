package com.bka.ssi.controller.accreditation.company.domain.specifications.accreditations;

import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import com.bka.ssi.controller.accreditation.company.domain.specifications.Specification;

public class AccreditationInitialStateSpecification implements Specification<Accreditation> {

    @Override
    public Boolean isSatisfiedBy(Accreditation accreditation) {
        return accreditation.getParty() != null;
    }
}
