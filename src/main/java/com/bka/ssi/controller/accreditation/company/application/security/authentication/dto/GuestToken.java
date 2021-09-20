package com.bka.ssi.controller.accreditation.company.application.security.authentication.dto;

import java.util.Date;

public class GuestToken {
    String id;
    String accreditationId;
    Date expiring;

    public GuestToken(String id, String accreditationId, Date expiring) {
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

    public Date getExpiring() {
        return expiring;
    }
}
