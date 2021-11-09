package com.bka.ssi.controller.accreditation.company.application.repositories.accreditations;

import com.bka.ssi.controller.accreditation.company.application.repositories.AccreditationRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeAccreditationRepository
    extends AccreditationRepository<EmployeeAccreditation> {

    Optional<EmployeeAccreditation> findByEmployeeCredentialIssuanceCorrelationConnectionId(
        String connectionId);
}
