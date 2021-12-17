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

/**
 * The type Guest initial state specification.
 */
public class GuestInitialStateSpecification implements Specification<Guest> {

    public Boolean isSatisfiedBy(Guest guest) {
        return (
            guest.getCredentialOffer() != null &&
                !guest.getCredentialOffer().getCredential().getPersona().getFirstName().isEmpty() &&
                !guest.getCredentialOffer().getCredential().getPersona().getLastName().isEmpty()
        );
    }
}
