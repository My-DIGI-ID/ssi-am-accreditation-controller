package com.bka.ssi.controller.accreditation.company.domain.specifications.accreditations;

import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.specifications.Specification;

public class GuestAccreditationCreatedByAndInvitedBySpecification
    implements Specification<GuestAccreditation> {

    public Boolean isSatisfiedBy(GuestAccreditation guestAccreditation) {
        return guestAccreditation.getParty().getCreatedBy()
            .equals(guestAccreditation.getInvitedBy());
    }
}
