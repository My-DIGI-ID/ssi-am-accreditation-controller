package com.bka.ssi.controller.accreditation.company.testutilities.accreditation.guest;

import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ValidityTimeframeBuilder {

    private ZonedDateTime validFrom;
    private ZonedDateTime validUntil;

    public ValidityTimeframeBuilder() {
    }

    public ValidityTimeframe build() {
        return new ValidityTimeframe(this.validFrom, this.validUntil);
    }

    public void reset() {
        this.validFrom = null;
        this.validUntil = null;
    }

    public ValidityTimeframe buildConstantValidityTimeframe() {
        this.validFrom = this.validFrom != null ? this.validFrom :
            ZonedDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC"));
        this.validUntil = this.validUntil != null ? this.validUntil : this.validFrom.plusMinutes(5);

        return this.build();
    }

    public ValidityTimeframe buildDynamicValidityTimeframe() {
        this.validFrom = this.validFrom != null ? this.validFrom : ZonedDateTime.now();
        this.validUntil = this.validUntil != null ? this.validUntil : this.validFrom.plusMinutes(5);

        return this.build();
    }

    @Test
    void buildConstantValidityTimeframeTest() {
        ValidityTimeframe validityTimeframe = this.buildConstantValidityTimeframe();

        Assertions.assertEquals(this.validFrom, validityTimeframe.getValidFrom());
        Assertions.assertEquals(this.validUntil, validityTimeframe.getValidUntil());

        this.reset();
    }

    @Test
    void buildDynamicValidityTimeframeTest() {
        ValidityTimeframe validityTimeframe = this.buildDynamicValidityTimeframe();

        Assertions.assertEquals(this.validFrom, validityTimeframe.getValidFrom());
        Assertions.assertEquals(this.validUntil, validityTimeframe.getValidUntil());

        this.reset();
    }
}
