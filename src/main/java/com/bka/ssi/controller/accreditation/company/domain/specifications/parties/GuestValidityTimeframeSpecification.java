package com.bka.ssi.controller.accreditation.company.domain.specifications.parties;

import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.specifications.Specification;
import com.bka.ssi.controller.accreditation.company.domain.specifications.common.ValidityTimeframeSpecification;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class GuestValidityTimeframeSpecification implements Specification<Guest> {

    @Override
    public Boolean isSatisfiedBy(Guest guest) {
        ValidityTimeframe validityTimeframe =
            guest.getCredentialOffer().getCredential().getValidityTimeframe();

        if (validityTimeframe == null) {
            return false;
        }

        ZonedDateTime validFrom =
            validityTimeframe.getValidFrom();
        ZonedDateTime validUntil =
            validityTimeframe.getValidUntil();

        LocalDate validFromDate = validFrom.toLocalDate();
        LocalDate validUntilDate = validUntil.toLocalDate();

        return validFromDate.isEqual(validUntilDate) &&
            new ValidityTimeframeSpecification().isSatisfiedBy(validityTimeframe);
    }
}
