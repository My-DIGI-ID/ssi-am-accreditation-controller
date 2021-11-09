package com.bka.ssi.controller.accreditation.company.testutilities.accreditation.guest;

import com.bka.ssi.controller.accreditation.company.domain.specifications.common.ValidityTimeframeSpecification;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;

import java.time.ZonedDateTime;

public class ValidTimeframeBuilder {

    private final ValidityTimeframeSpecification specification;
    private ZonedDateTime fromDate;
    private ZonedDateTime untilDate;


    public ValidTimeframeBuilder() {
        this.specification = new ValidityTimeframeSpecification();


        fromDate = ZonedDateTime.now();
        untilDate = ZonedDateTime.now().plusMinutes(5);

    }

    public ValidityTimeframe build() {
        return new ValidityTimeframe(this.fromDate, this.untilDate);
    }
}
