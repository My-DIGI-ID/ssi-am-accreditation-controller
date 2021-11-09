package com.bka.ssi.controller.accreditation.company.domain.values;

import java.time.ZonedDateTime;

public class ValidityTimeframe {

    private ZonedDateTime validFrom;
    private ZonedDateTime validUntil;

    public ValidityTimeframe(ZonedDateTime validFrom, ZonedDateTime validUntil) {
        this.validFrom = validFrom;
        this.validUntil = validUntil;
    }

    public ZonedDateTime getValidFrom() {
        return validFrom;
    }

    public ZonedDateTime getValidUntil() {
        return validUntil;
    }
}
