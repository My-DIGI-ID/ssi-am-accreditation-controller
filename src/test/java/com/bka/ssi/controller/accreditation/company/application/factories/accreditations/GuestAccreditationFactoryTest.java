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

package com.bka.ssi.controller.accreditation.company.application.factories.accreditations;

import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.enums.GuestAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.testutilities.party.guest.GuestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuestAccreditationFactoryTest {

    private GuestAccreditationFactory guestAccreditationFactory;

    private GuestBuilder guestBuilder;

    private Logger logger = LoggerFactory.getLogger(GuestAccreditationFactoryTest.class);

    @BeforeEach
    void setUp() {
        guestAccreditationFactory = new GuestAccreditationFactory(logger);
        guestBuilder = new GuestBuilder();
    }

    @Test
    void create() throws InvalidValidityTimeframeException {
        Guest guest = guestBuilder.buildGuest();

        GuestAccreditation accreditation =
            guestAccreditationFactory.create(guest, guest.getCreatedBy());

        Assertions.assertEquals(accreditation.getParty(), guest);
        Assertions.assertEquals(GuestAccreditationStatus.OPEN, accreditation.getStatus());
        Assertions.assertEquals(guest.getCreatedBy(), accreditation.getInvitedBy());
        Assertions.assertNull(accreditation.getInvitationUrl());
        Assertions.assertNull(accreditation.getInvitationEmail());
        Assertions.assertNull(accreditation.getInvitationQrCode());
    }
}
