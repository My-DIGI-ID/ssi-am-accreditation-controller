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

import java.util.List;

/**
 * The type Contact information.
 */
public class ContactInformation {

    private List<String> emails;
    private List<String> phoneNumbers;

    /**
     * Instantiates a new Contact information.
     *
     * @param emails       the emails
     * @param phoneNumbers the phone numbers
     */
    public ContactInformation(List<String> emails, List<String> phoneNumbers) {
        this.emails = emails;
        this.phoneNumbers = phoneNumbers;
    }

    /**
     * Instantiates a new Contact information.
     *
     * @param emails the emails
     */
    public ContactInformation(List<String> emails) {
        this.emails = emails;
    }

    /**
     * Gets emails.
     *
     * @return the emails
     */
    public List<String> getEmails() {
        return emails;
    }

    /**
     * Gets phone numbers.
     *
     * @return the phone numbers
     */
    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }
}
