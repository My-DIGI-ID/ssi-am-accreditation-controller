package com.bka.ssi.controller.accreditation.company.application.repositories.accreditations;

import com.bka.ssi.controller.accreditation.company.application.repositories.AccreditationRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GuestAccreditationRepository extends AccreditationRepository<GuestAccreditation> {

    Optional<GuestAccreditation> findByBasisIdVerificationCorrelationConnectionId(
        String connectionId);

    Optional<GuestAccreditation> findByBasisIdVerificationCorrelationThreadId(
        String threadId);

    Optional<GuestAccreditation> findByBasisIdVerificationCorrelationPresentationExchangeId(
        String presentationExchangeId);

    Optional<GuestAccreditation> findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadId(
        String connectionId,
        String threadId) throws Exception;

    Optional<GuestAccreditation> findByBasisIdVerificationCorrelationConnectionIdAndBasisIdVerificationCorrelationThreadIdAndBasisIdVerificationCorrelationPresentationExchangeId(
        String connectionId,
        String threadId,
        String presentationExchangeId);

    Optional<GuestAccreditation> findByGuestCredentialIssuanceCorrelationConnectionId(
        String connectionId) throws Exception;

    Optional<GuestAccreditation> findByGuestCredentialIssuanceCorrelationThreadId(
        String threadId);

    Optional<GuestAccreditation> findByGuestCredentialIssuanceCorrelationPresentationExchangeId(
        String presentationExchangeId);

    Optional<GuestAccreditation> findByGuestCredentialIssuanceCorrelationConnectionIdAndGuestCredentialIssuanceCorrelationThreadId(
        String connectionId,
        String threadId) throws Exception;

    Optional<GuestAccreditation> findByGuestCredentialIssuanceCorrelationConnectionIdAndGuestCredentialIssuanceCorrelationThreadIdAndGuestCredentialIssuanceCorrelationPresentationExchangeId(
        String connectionId,
        String threadId,
        String presentationExchangeId);

    default Optional<GuestAccreditation> findByPartyParams(String referenceBasisId,
        String firstName,
        String lastName, String dateOfBirth,
        String companyName, ZonedDateTime validFrom, ZonedDateTime validUntil,
        String invitedBy) throws Exception {
        throw new UnsupportedOperationException();
    }

    default List<GuestAccreditation> findAllByCredentialInvitedBy(String invitedBy)
        throws Exception {
        throw new UnsupportedOperationException();
    }
}
