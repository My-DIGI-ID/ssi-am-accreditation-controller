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

package com.bka.ssi.controller.accreditation.company.application.utilities;

import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.values.BasisIdPresentation;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * The type Basis id presentation utility.
 */
@Component
public class BasisIdPresentationUtility {

    private final Logger logger;
    private final FuzzyMatcher matcher;

    /**
     * Instantiates a new Basis id presentation utility.
     *
     * @param logger  the logger
     * @param matcher the matcher
     */
    public BasisIdPresentationUtility(Logger logger, FuzzyMatcher matcher) {
        this.logger = logger;
        this.matcher = matcher;
    }

    /**
     * Is presentation and guest match boolean.
     *
     * @param presentation the presentation
     * @param guest        the guest
     * @return the boolean
     */
    public Boolean isPresentationAndGuestMatch(BasisIdPresentation presentation,
        Guest guest) {
        logger.debug(
            "Matching expected firstname and lastname with actual firstname and lastname of " +
                "guest with id {}", guest.getId());

        String firstNameExpected =
            guest.getCredentialOffer().getCredential().getPersona().getFirstName();
        String lastNameExpected =
            guest.getCredentialOffer().getCredential().getPersona().getLastName();

        String firstNameActual = presentation.getFirstName();
        String lastNameActual = presentation.getFamilyName();

        if (firstNameExpected == null || lastNameExpected == null ||
            firstNameActual == null || lastNameActual == null) {
            return false;
        }

        return firstNameActual.equals(firstNameExpected) && lastNameActual.equals(lastNameExpected);
    }

    /**
     * Is presentation and guest match loosely boolean.
     *
     * @param presentation the presentation
     * @param guest        the guest
     * @return the boolean
     */
    public Boolean isPresentationAndGuestMatchLoosely(BasisIdPresentation presentation,
        Guest guest) {
        logger.debug(
            "Loosely matching expected firstname and lastname with actual firstname and lastname " +
                "of guest with id {}", guest.getId());

        String firstNameExpected =
            guest.getCredentialOffer().getCredential().getPersona().getFirstName();
        String lastNameExpected =
            guest.getCredentialOffer().getCredential().getPersona().getLastName();

        String firstNameActual = presentation.getFirstName();
        String lastNameActual = presentation.getFamilyName();

        if (firstNameExpected == null || lastNameExpected == null ||
            firstNameActual == null || lastNameActual == null) {
            return false;
        }

        String fullExpected = new String(
            (firstNameExpected + lastNameExpected)
                .toLowerCase()
                .strip()
                .replaceAll("\\s+", "")
                .getBytes(), StandardCharsets.UTF_8);
        String fullActual = new String(
            (firstNameActual + lastNameActual)
                .toLowerCase()
                .strip()
                .replaceAll("\\s+", "")
                .getBytes(), StandardCharsets.UTF_8);

        return this.matcher.isMatch(fullActual, fullExpected);
    }
}
