package com.bka.ssi.controller.accreditation.company.application.repositories.accreditations;

import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.DefaultAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeAccreditationRepository
    extends CrudRepository<DefaultAccreditation<Employee>, String> {
}
