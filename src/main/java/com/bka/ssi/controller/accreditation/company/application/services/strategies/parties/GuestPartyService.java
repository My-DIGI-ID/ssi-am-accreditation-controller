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

package com.bka.ssi.controller.accreditation.company.application.services.strategies.parties;

import com.bka.ssi.controller.accreditation.company.application.factories.parties.GuestFactory;
import com.bka.ssi.controller.accreditation.company.application.repositories.accreditations.GuestAccreditationRepository;
import com.bka.ssi.controller.accreditation.company.application.repositories.parties.GuestRepository;
import com.bka.ssi.controller.accreditation.company.application.services.PartyService;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.GuestInputDto;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type Guest party service.
 */
@Service
public class GuestPartyService extends PartyService<Guest, GuestInputDto, GuestAccreditation> {

    /**
     * Instantiates a new Guest party service.
     *
     * @param logger                       the logger
     * @param guestRepository              the guest repository
     * @param guestFactory                 the guest factory
     * @param guestAccreditationRepository the guest accreditation repository
     */
    public GuestPartyService(Logger logger,
                             @Qualifier("guestMongoDbFacade") GuestRepository guestRepository,
                             GuestFactory guestFactory,
                             @Qualifier("guestAccreditationMongoDbFacade") GuestAccreditationRepository guestAccreditationRepository) {
        super(logger, guestRepository, guestFactory, guestAccreditationRepository);
    }
}
