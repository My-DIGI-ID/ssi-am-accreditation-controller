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
