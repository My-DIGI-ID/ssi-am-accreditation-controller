package com.bka.ssi.controller.accreditation.company.application.security;

import com.bka.ssi.controller.accreditation.company.application.security.authentication.dto.GuestToken;
import org.springframework.data.repository.CrudRepository;

public interface GuestAccessTokenRepository extends CrudRepository<GuestToken, String> {

    default void deleteByAccreditationId(String id) {
        throw new UnsupportedOperationException();
    }
}
