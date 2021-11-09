package com.bka.ssi.controller.accreditation.company.application.factories.accreditations;

import com.bka.ssi.controller.accreditation.company.application.factories.AccreditationFactory;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.enums.EmployeeAccreditationStatus;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class EmployeeAccreditationFactory
    implements AccreditationFactory<Employee, EmployeeAccreditation> {

    private final Logger logger;

    public EmployeeAccreditationFactory(Logger logger) {
        this.logger = logger;
    }

    @Override
    public EmployeeAccreditation create(Employee employee, String userName) {
        return new EmployeeAccreditation(employee, EmployeeAccreditationStatus.OPEN, userName,
            ZonedDateTime.now());
    }
}
