package com.bka.ssi.controller.accreditation.company.application.services.strategies.parties;

import com.bka.ssi.controller.accreditation.company.application.factories.parties.EmployeeFactory;
import com.bka.ssi.controller.accreditation.company.application.repositories.accreditations.EmployeeAccreditationRepository;
import com.bka.ssi.controller.accreditation.company.application.repositories.parties.EmployeeRepository;
import com.bka.ssi.controller.accreditation.company.application.services.PartyService;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.EmployeeInputDto;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EmployeePartyService
    extends PartyService<Employee, EmployeeInputDto, EmployeeAccreditation> {

    public EmployeePartyService(Logger logger,
                                @Qualifier("employeeMongoDbFacade") EmployeeRepository employeeRepository,
                                EmployeeFactory employeeFactory,
                                @Qualifier("employeeAccreditationMongoDbFacade") EmployeeAccreditationRepository employeeAccreditationRepository) {
        super(logger, employeeRepository, employeeFactory, employeeAccreditationRepository);
    }
}
