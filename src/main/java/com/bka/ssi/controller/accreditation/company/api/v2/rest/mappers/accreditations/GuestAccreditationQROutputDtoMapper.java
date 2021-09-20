package com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationQROutputDto;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class GuestAccreditationQROutputDtoMapper {

    private final Logger logger;

    public GuestAccreditationQROutputDtoMapper(Logger logger) {
        this.logger = logger;
    }

    public GuestAccreditationQROutputDto entityToOutputDto(GuestAccreditation accreditation) {
        if (accreditation == null) {
            return null;
        } else {
            GuestAccreditationQROutputDto output = new GuestAccreditationQROutputDto();

            output.setConnectionQrCode(accreditation.getConnectionQrCode());

            return output;
        }
    }
}
