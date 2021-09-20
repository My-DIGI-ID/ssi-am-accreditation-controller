package com.bka.ssi.controller.accreditation.company.application.repositories.parties;

import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, String> {
}
