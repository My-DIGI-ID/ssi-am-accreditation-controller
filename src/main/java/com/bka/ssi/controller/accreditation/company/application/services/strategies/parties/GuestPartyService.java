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

@Service
public class GuestPartyService extends PartyService<Guest, GuestInputDto, GuestAccreditation> {

    public GuestPartyService(Logger logger,
                             @Qualifier("guestMongoDbFacade") GuestRepository guestRepository,
                             GuestFactory guestFactory,
                             @Qualifier("guestAccreditationMongoDbFacade") GuestAccreditationRepository guestAccreditationRepository) {
        super(logger, guestRepository, guestFactory, guestAccreditationRepository);
    }
}
