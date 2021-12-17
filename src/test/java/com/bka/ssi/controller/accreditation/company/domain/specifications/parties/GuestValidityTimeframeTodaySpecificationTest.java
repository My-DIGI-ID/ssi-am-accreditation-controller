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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.GuestCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GuestValidityTimeframeTodaySpecificationTest {

    private GregorianCalendar calendar;

    private GuestValidityTimeframeSpecification specification;

    private ZonedDateTime validFrom;
    private ZonedDateTime validUntil;
    private ZonedDateTime invalidUntil;

    @BeforeEach
    public void init() {
        this.specification = new GuestValidityTimeframeSpecification();
        this.calendar = new GregorianCalendar(2018, Calendar.JANUARY, 1);
        this.validFrom = ZonedDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC"));
        this.validUntil = validFrom.plusMinutes(60);
        this.invalidUntil = validFrom.minusYears(4);
    }

    @Test
    public void shouldReturnFalseOnGuestWithInvalidValidityTimeframe() {
        ValidityTimeframe validityTimeframe =
            new ValidityTimeframe(this.validFrom, this.invalidUntil);

        GuestCredential credential = new GuestCredential(
            validityTimeframe,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );

        CredentialOffer<GuestCredential> credentialOffer = new CredentialOffer(
            null,
            credential
        );

        assertThrows(InvalidValidityTimeframeException.class, () ->
        {
            Guest guest = new Guest("123456", credentialOffer, "user1", ZonedDateTime.now());

            assertFalse(this.specification.isSatisfiedBy(guest));
        });
    }

    @Test
    public void shouldReturnTrueOnGuestWithValidValidityTimeframe() {
        ValidityTimeframe validityTimeframe =
            new ValidityTimeframe(this.validFrom, this.validUntil);

        GuestCredential credential = new GuestCredential(
            validityTimeframe,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );

        CredentialOffer<GuestCredential> credentialOffer = new CredentialOffer(
            null,
            credential
        );

        try {
            Guest guest = new Guest("123456", credentialOffer, "user1", ZonedDateTime.now());

            assertTrue(this.specification.isSatisfiedBy(guest));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
