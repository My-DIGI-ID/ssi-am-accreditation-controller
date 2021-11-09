package com.bka.ssi.controller.accreditation.company.domain.specifications.parties;

import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.specifications.Specification;
import com.bka.ssi.controller.accreditation.company.domain.specifications.common.ValidityTimeframeSpecification;
import com.bka.ssi.controller.accreditation.company.domain.specifications.common.ValidityTimeframeTodaySpecification;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;

public class GuestValidityTimeframeTodaySpecification implements Specification<Guest> {

    @Override
    public Boolean isSatisfiedBy(Guest guest) {
        ValidityTimeframe validityTimeframe =
            guest.getCredentialOffer().getCredential().getValidityTimeframe();

        return new ValidityTimeframeSpecification().isSatisfiedBy(validityTimeframe) &&
            new ValidityTimeframeTodaySpecification().isSatisfiedBy(validityTimeframe);
    }
}
