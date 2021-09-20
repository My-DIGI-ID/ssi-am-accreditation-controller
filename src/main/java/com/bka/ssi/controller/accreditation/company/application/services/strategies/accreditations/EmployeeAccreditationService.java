package com.bka.ssi.controller.accreditation.company.application.services.strategies.accreditations;

import com.bka.ssi.controller.accreditation.company.application.repositories.accreditations.EmployeeAccreditationRepository;
import com.bka.ssi.controller.accreditation.company.application.services.AccreditationService;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.DefaultAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeAccreditationService
    extends AccreditationService<DefaultAccreditation<Employee>> {

    public EmployeeAccreditationService(
        EmployeeAccreditationRepository employeeAccreditationRepository) {
        this.setCrudRepository(employeeAccreditationRepository);
    }
}
