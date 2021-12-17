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

package com.bka.ssi.controller.accreditation.company.domain.values;

/**
 * The type Basis id presentation.
 */
public class BasisIdPresentation {

    private String firstName;
    private String familyName;
    private String dateOfBirth;

    /**
     * Instantiates a new Basis id presentation.
     *
     * @param firstName   the first name
     * @param familyName  the family name
     * @param dateOfBirth the date of birth
     */
    public BasisIdPresentation(String firstName, String familyName, String dateOfBirth) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets family name.
     *
     * @return the family name
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Gets date of birth.
     *
     * @return the date of birth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }
}
