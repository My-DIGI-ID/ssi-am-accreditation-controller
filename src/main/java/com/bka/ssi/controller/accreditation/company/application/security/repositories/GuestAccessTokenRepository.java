package com.bka.ssi.controller.accreditation.company.application.security.repositories;

import com.bka.ssi.controller.accreditation.company.application.security.authentication.dto.GuestToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestAccessTokenRepository extends CrudRepository<GuestToken, String> {

    void deleteByAccreditationId(String accreditationId);
}
