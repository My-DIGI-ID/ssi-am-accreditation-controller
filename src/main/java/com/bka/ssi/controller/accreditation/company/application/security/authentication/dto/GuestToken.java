package com.bka.ssi.controller.accreditation.company.application.security.authentication.dto;

import java.time.ZonedDateTime;

public class GuestToken {
    String id;
    String accreditationId;
    ZonedDateTime expiring;

    public GuestToken(String id, String accreditationId, ZonedDateTime expiring) {
        this.id = id;
        this.accreditationId = accreditationId;
        this.expiring = expiring;
    }

    public String getId() {
        return id;
    }

    public String getAccreditationId() {
        return accreditationId;
    }

    public ZonedDateTime getExpiring() {
        return expiring;
    }
}
