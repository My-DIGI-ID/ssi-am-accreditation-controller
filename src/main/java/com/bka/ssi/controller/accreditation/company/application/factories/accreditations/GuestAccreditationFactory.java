package com.bka.ssi.controller.accreditation.company.application.factories.accreditations;

import com.bka.ssi.controller.accreditation.company.application.factories.AccreditationFactory;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.enums.GuestAccreditationStatus;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class GuestAccreditationFactory implements AccreditationFactory<Guest, GuestAccreditation> {

    private final Logger logger;

    public GuestAccreditationFactory(Logger logger) {
        this.logger = logger;
    }

    @Override
    public GuestAccreditation create(Guest guest, String userName) {
        return new GuestAccreditation(guest, GuestAccreditationStatus.OPEN, userName,
            ZonedDateTime.now());
    }
}
