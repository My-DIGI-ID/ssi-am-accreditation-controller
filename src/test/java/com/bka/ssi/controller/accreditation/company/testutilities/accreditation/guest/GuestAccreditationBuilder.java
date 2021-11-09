package com.bka.ssi.controller.accreditation.company.testutilities.accreditation.guest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.enums.GuestAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.testutilities.party.guest.GuestBuilder;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

public class GuestAccreditationBuilder {
    public static Guest guest;

    public String invitee;

    public GuestAccreditationBuilder() throws InvalidValidityTimeframeException {
        GuestBuilder builder = new GuestBuilder();
        guest = builder.build();
    }

    public GuestAccreditation build() {
        if (invitee == null) {
            invitee = "EmployeeXYZ";
        }
        return new GuestAccreditation(guest, GuestAccreditationStatus.OPEN, invitee,
            ZonedDateTime.now());
    }

    public void reset() {
        guest = null;
    }

    @Test
    private void buildGuestAccreditation() throws InvalidValidityTimeframeException {
        GuestBuilder builder = new GuestBuilder();
        builder.createdBy = "unittest";
        builder.invitedBy = "unittest";
        guest = builder.buildGuest();
        this.invitee = "unittest";
        GuestAccreditation guestAccreditation = this.build();
        assertEquals(guestAccreditation.getStatus(), GuestAccreditationStatus.OPEN);
        assertEquals(guestAccreditation.getInvitedBy(), "unittest");
        this.reset();
    }
}
