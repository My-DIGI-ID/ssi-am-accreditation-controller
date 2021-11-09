package com.bka.ssi.controller.accreditation.company.application.repositories;

import com.bka.ssi.controller.accreditation.company.domain.entities.Party;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartyRepository<T extends Party> extends BaseRepository<T, String> {

    default Optional<T> findByIdAndCreatedBy(String id, String createdBy)
        throws Exception {
        throw new UnsupportedOperationException();
    }

    default List<T> findAllByCreatedBy(String createdBy) throws Exception {
        throw new UnsupportedOperationException();
    }
}
