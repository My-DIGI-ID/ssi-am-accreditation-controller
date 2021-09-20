package com.bka.ssi.controller.accreditation.company.application.services.strategies.parties;

import com.bka.ssi.controller.accreditation.company.application.factories.parties.EmployeeFactory;
import com.bka.ssi.controller.accreditation.company.application.repositories.parties.EmployeeRepository;
import com.bka.ssi.controller.accreditation.company.application.services.PartyService;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EmployeePartyService extends PartyService<Employee> {

    public final static String PERMISSION_RESOURCE_IDENTIFIER = "employee";

    public EmployeePartyService(
        @Qualifier("employeeMongoDbFacade") EmployeeRepository employeeRepository,
        EmployeeFactory employeeFactory) {
        this.setCrudRepository(employeeRepository);
        this.setFactory(employeeFactory);
    }
}
