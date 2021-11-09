package com.bka.ssi.controller.accreditation.company.domain.entities;

import java.time.ZonedDateTime;

import com.bka.ssi.controller.accreditation.company.domain.entities.abstractions.common.Entity;
import com.bka.ssi.controller.accreditation.company.domain.enums.AccreditationStatus;

public abstract class Accreditation<T extends Party, R extends AccreditationStatus>
    extends Entity {

    protected R status;
    protected String invitedBy;
    protected ZonedDateTime invitedAt;

    /* ToDo -Have following invitation fields in an invitation value object, see mongodb document */
    protected String invitationUrl;
    protected String invitationEmail;
    protected String invitationQrCode;
    private T party;

    public Accreditation(T party, R status, String invitedBy, ZonedDateTime invitedAt) {
        super(null);

        this.party = party;
        this.status = status;
        this.invitedBy = invitedBy;
        this.invitedAt = invitedAt;
    }

    public Accreditation(String id, T party, R status, String invitedBy, ZonedDateTime invitedAt) {
        super(id);

        this.party = party;
        this.status = status;
        this.invitedBy = invitedBy;
        this.invitedAt = invitedAt;
    }

    public Accreditation(String id, T party, R status, String invitedBy, ZonedDateTime invitedAt,
        String invitationUrl, String invitationEmail, String invitationQrCode) {
        super(id);
        this.party = party;
        this.status = status;
        this.invitationUrl = invitationUrl;
        this.invitationEmail = invitationEmail;
        this.invitationQrCode = invitationQrCode;
        this.invitedBy = invitedBy;
        this.invitedAt = invitedAt;
    }

    public T getParty() {
        return this.party;
    }

    public R getStatus() {
        return this.status;
    }

    public String getInvitedBy() {
        return invitedBy;
    }

    public ZonedDateTime getInvitedAt() {
        return invitedAt;
    }

    public String getInvitationUrl() {
        return invitationUrl;
    }

    public String getInvitationEmail() {
        return invitationEmail;
    }

    public String getInvitationQrCode() {
        return invitationQrCode;
    }
}
