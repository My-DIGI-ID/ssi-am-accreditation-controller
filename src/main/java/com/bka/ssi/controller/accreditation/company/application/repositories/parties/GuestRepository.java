package com.bka.ssi.controller.accreditation.company.application.repositories.parties;

import com.bka.ssi.controller.accreditation.company.application.repositories.PartyRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends PartyRepository<Guest> {

    default Optional<Guest> findByPartyParams(
        String referenceBasisId,
        String firstName, String lastName, String dateOfBirth, String companyName,
        ZonedDateTime validFrom, ZonedDateTime validUntil, String invitedBy) throws Exception {
        throw new UnsupportedOperationException();
    }

    default List<Guest> findAllByCredentialInvitedBy(String invitedBy) throws Exception {
        throw new UnsupportedOperationException();
    }
}
