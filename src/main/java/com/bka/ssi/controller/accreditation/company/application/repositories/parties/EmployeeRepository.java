package com.bka.ssi.controller.accreditation.company.application.repositories.parties;

import com.bka.ssi.controller.accreditation.company.application.repositories.PartyRepository;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends PartyRepository<Employee> {
}
