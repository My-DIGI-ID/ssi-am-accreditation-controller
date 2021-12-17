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

package com.bka.ssi.controller.accreditation.company.domain.specifications.parties;

import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.specifications.Specification;
import com.bka.ssi.controller.accreditation.company.domain.specifications.common.ValidityTimeframeSpecification;
import com.bka.ssi.controller.accreditation.company.domain.specifications.common.ValidityTimeframeTodaySpecification;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;

/**
 * The type Guest validity timeframe today specification.
 */
public class GuestValidityTimeframeTodaySpecification implements Specification<Guest> {

    @Override
    public Boolean isSatisfiedBy(Guest guest) {
        ValidityTimeframe validityTimeframe =
            guest.getCredentialOffer().getCredential().getValidityTimeframe();

        return new ValidityTimeframeSpecification().isSatisfiedBy(validityTimeframe) &&
            new ValidityTimeframeTodaySpecification().isSatisfiedBy(validityTimeframe);
    }
}
