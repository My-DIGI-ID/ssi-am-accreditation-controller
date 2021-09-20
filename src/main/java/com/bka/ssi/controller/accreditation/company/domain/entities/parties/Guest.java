package com.bka.ssi.controller.accreditation.company.domain.entities.parties;

import com.bka.ssi.controller.accreditation.company.domain.entities.Party;
import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.GuestCredential;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;
import com.bka.ssi.controller.accreditation.company.domain.values.GuestPrivateInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;

import java.util.ArrayList;
import java.util.Date;

public class Guest extends Party<GuestCredential> {

    public Guest(CredentialOffer<GuestCredential> credentialOffer) {
        super(credentialOffer);
    }

    public Guest(String id, CredentialOffer<GuestCredential> credentialOffer) {
        super(id, credentialOffer);
    }

    @Override
    public GuestCredential createEmptyCredentialForDataCleanup() {
        GuestCredential cleanGuestCredentials = new GuestCredential(
            new ValidityTimeframe(
                new Date(),
                new Date(),
                new Date(),
                new Date()
            ),
            new Persona(
                "deleted",
                "deleted",
                "deleted"
            ),
            new ContactInformation(
                new ArrayList<>(),
                new ArrayList<>()
            ),
            "deleted",
            "deleted",
            "deleted",
            "deleted",
            "deleted",
            new GuestPrivateInformation(
                "deleted",
                "deleted",
                "deleted",
                "deleted",
                "deleted",
                "deleted"
            )
        );

        return cleanGuestCredentials;
    }

    public Guest addInformationAboutInvitingPerson(String invitedBy) {
        this.getCredentialOffer().getCredential()
            .addInvitingPersonInformationToCredential(invitedBy);
        
        return this;
    }
}
