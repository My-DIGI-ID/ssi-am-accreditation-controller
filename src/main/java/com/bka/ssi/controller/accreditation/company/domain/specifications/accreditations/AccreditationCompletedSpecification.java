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

package com.bka.ssi.controller.accreditation.company.domain.specifications.accreditations;

import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import com.bka.ssi.controller.accreditation.company.domain.enums.EmployeeAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.enums.GuestAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.specifications.Specification;

/**
 * The type Accreditation completed specification.
 */
public class AccreditationCompletedSpecification implements Specification<Accreditation> {

    @Override
    public Boolean isSatisfiedBy(Accreditation accreditation) {
        switch (accreditation.getParty().getCredentialOffer().getCredentialMetadata()
            .getCredentialType()) {
            case EMPLOYEE:
                return accreditation.getStatus() == EmployeeAccreditationStatus.ACCEPTED;
            case GUEST:
                return accreditation.getStatus() == GuestAccreditationStatus.ACCEPTED;
            default:
                throw new IllegalArgumentException("Undefined Credential Type");
        }
    }
}
