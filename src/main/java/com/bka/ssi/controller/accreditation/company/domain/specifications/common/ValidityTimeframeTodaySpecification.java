package com.bka.ssi.controller.accreditation.company.domain.specifications.common;

import com.bka.ssi.controller.accreditation.company.domain.specifications.Specification;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class ValidityTimeframeTodaySpecification implements Specification<ValidityTimeframe> {

    @Override
    public Boolean isSatisfiedBy(
        ValidityTimeframe validityTimeframe) {

        LocalDate today = ZonedDateTime.now().toLocalDate();

        if (validityTimeframe == null) {
            return false;
        }

        ZonedDateTime validFrom = validityTimeframe.getValidFrom();
        ZonedDateTime validUntil = validityTimeframe.getValidUntil();

        if (validFrom == null || validUntil == null) {
            return false;
        }

        LocalDate validFromDate = validFrom.toLocalDate();
        LocalDate validUntilDate = validUntil.toLocalDate();

        return validFromDate.compareTo(today) >= 0 &&
            validUntilDate.compareTo(today) >= 0;
    }
}
