package com.bka.ssi.controller.accreditation.company.application.factories.accreditations;

import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.enums.EmployeeAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.testutilities.party.employee.EmployeeBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployeeAccreditationFactoryTest {

    private EmployeeAccreditationFactory employeeAccreditationFactory;

    private EmployeeBuilder employeeBuilder;

    private Logger logger = LoggerFactory.getLogger(EmployeeAccreditationFactoryTest.class);

    @BeforeEach
    void setUp() {
        employeeAccreditationFactory = new EmployeeAccreditationFactory(logger);
        employeeBuilder = new EmployeeBuilder();
    }

    @Test
    void create() {
        Employee employee = employeeBuilder.buildEmployee();

        EmployeeAccreditation accreditation = employeeAccreditationFactory.create(employee,
            "username");

        Assertions.assertEquals(employee, accreditation.getParty());
        Assertions.assertEquals(EmployeeAccreditationStatus.OPEN, accreditation.getStatus());
        Assertions.assertEquals("username", accreditation.getInvitedBy());
        Assertions.assertNull(accreditation.getEmployeeCredentialIssuanceCorrelation());
        Assertions.assertNull(accreditation.getInvitationUrl());
        Assertions.assertNull(accreditation.getInvitationEmail());
        Assertions.assertNull(accreditation.getInvitationQrCode());
    }
}
