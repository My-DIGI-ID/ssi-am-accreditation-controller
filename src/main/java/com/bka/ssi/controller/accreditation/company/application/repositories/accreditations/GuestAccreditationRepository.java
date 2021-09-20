package com.bka.ssi.controller.accreditation.company.application.repositories.accreditations;

import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public interface GuestAccreditationRepository extends
    CrudRepository<GuestAccreditation, String> {

    default Optional<GuestAccreditation> findByConnectionIdAndThreadId(String correlationId,
        String threadId) {
        throw new UnsupportedOperationException();
    }
    
    default Optional<GuestAccreditation> findByIssuanceConnectionIdAndThreadId(String correlationId,
            String threadId) {
            throw new UnsupportedOperationException();
    }

    default Optional<GuestAccreditation> findByVerificationQueryParameters(
        String referenceBasisId, String firstName, String lastName, String dateOfBirth,
        String companyName, Date validFromDate, Date validUntilDate, String invitedBy) throws
        NotFoundException {
        throw new UnsupportedOperationException();
    }
}
