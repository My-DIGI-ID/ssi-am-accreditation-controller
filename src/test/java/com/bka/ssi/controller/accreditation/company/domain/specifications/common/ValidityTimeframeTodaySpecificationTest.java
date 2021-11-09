package com.bka.ssi.controller.accreditation.company.domain.specifications.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ValidityTimeframeTodaySpecificationTest {

    private ValidityTimeframeTodaySpecification specification;

    private ZonedDateTime validFrom;
    private ZonedDateTime validUntil;
    private ZonedDateTime invalidFrom;

    @BeforeEach
    public void init() {
        this.specification = new ValidityTimeframeTodaySpecification();
        this.validFrom = ZonedDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC"));
        this.validUntil = validFrom.plusYears(1);
        this.invalidFrom = validFrom.minusYears(4);
    }

    @Test
    public void shouldReturnFalseOnInvalidValidityTimeframe() {
        ValidityTimeframe validityTimeframe =
            new ValidityTimeframe(this.invalidFrom, this.validUntil);

        assertFalse(this.specification.isSatisfiedBy(validityTimeframe));
    }

    @Test
    public void shouldReturnTrueOnValidValidityTimeframe() {
        ValidityTimeframe validityTimeframe =
            new ValidityTimeframe(this.validFrom, this.validUntil);

        assertTrue(this.specification.isSatisfiedBy(validityTimeframe));
    }
}
