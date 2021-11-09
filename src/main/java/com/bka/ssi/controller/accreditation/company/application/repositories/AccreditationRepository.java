package com.bka.ssi.controller.accreditation.company.application.repositories;

import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import com.bka.ssi.controller.accreditation.company.domain.enums.AccreditationStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccreditationRepository<T extends Accreditation>
    extends BaseRepository<T, String> {

    default Optional<T> findByPartyId(String partyId)
        throws Exception {
        throw new UnsupportedOperationException();
    }

    default List<T> findAllByPartyId(String partyId) throws Exception {
        throw new UnsupportedOperationException();
    }

    default <R extends AccreditationStatus> List<T> findAllByStatus(R status) throws Exception {
        throw new UnsupportedOperationException();
    }

    default <R extends AccreditationStatus> List<T> findAllByStatusIsNot(R status)
        throws Exception {
        throw new UnsupportedOperationException();
    }

    default <R extends AccreditationStatus> long countByStatus(R status) {
        throw new UnsupportedOperationException();
    }

    default <R extends AccreditationStatus> long countByStatusIsNot(R status) {
        throw new UnsupportedOperationException();
    }

    default Optional<T> findByIdAndInvitedBy(String id, String invitedBy) throws Exception {
        throw new UnsupportedOperationException();
    }

    default List<T> findAllByInvitedBy(String invitedBy) throws Exception {
        throw new UnsupportedOperationException();
    }

    default <R extends AccreditationStatus> List<T> findAllByInvitedByAndValidStatus(
        String invitedBy, List<R> status) throws Exception {
        throw new UnsupportedOperationException();
    }
}
