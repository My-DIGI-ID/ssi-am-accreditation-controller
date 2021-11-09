package com.bka.ssi.controller.accreditation.company.domain.specifications.common;

import java.time.ZonedDateTime;

import com.bka.ssi.controller.accreditation.company.domain.specifications.Specification;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;

public class ValidityTimeframeSpecification implements Specification<ValidityTimeframe> {

    @Override
    public Boolean isSatisfiedBy(ValidityTimeframe validityTimeframe) {

        if (validityTimeframe == null) {
            return false;
        }

        ZonedDateTime validFrom = validityTimeframe.getValidFrom();
        ZonedDateTime validUntil = validityTimeframe.getValidUntil();

        if (validFrom == null || validUntil == null) {
            return false;
        }

        return validFrom.isBefore(validUntil) || validFrom.isEqual(validUntil);
    }
}
