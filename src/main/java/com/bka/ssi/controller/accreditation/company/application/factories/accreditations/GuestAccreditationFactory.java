package com.bka.ssi.controller.accreditation.company.application.factories.accreditations;

import com.bka.ssi.controller.accreditation.company.application.factories.Factory;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.accreditations.GuestAccreditationCompositeInputDto;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestAccreditationFactory implements Factory<GuestAccreditationCompositeInputDto,
    GuestAccreditation> {

    @Autowired
    private Logger logger;

    @Override
    public GuestAccreditation create(GuestAccreditationCompositeInputDto input) throws Exception {
        if (input == null) {
            return null;
        } else {
        }
        return null;
    }
}
