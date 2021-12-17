/*
 * Copyright 2021 Bundesrepublik Deutschland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
