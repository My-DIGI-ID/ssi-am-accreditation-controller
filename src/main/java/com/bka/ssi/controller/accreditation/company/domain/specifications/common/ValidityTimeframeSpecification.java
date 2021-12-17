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

import java.time.ZonedDateTime;

import com.bka.ssi.controller.accreditation.company.domain.specifications.Specification;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;

/**
 * The type Validity timeframe specification.
 */
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
