package com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.EmployeeAccreditationOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations.statistics.GuestAccreditationStatisticOutputMapperTest;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.parties.EmployeeOutputDtoMapper;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.testutilities.accreditation.employee.EmployeeAccreditationBuilder;
import com.bka.ssi.controller.accreditation.company.testutilities.party.employee.EmployeeBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeAccreditationOutputDtoMapperTest {
    private static final Logger logger =
            LoggerFactory.getLogger(GuestAccreditationStatisticOutputMapperTest.class);
    private Employee employee;
    private EmployeeAccreditation employeeAccreditation;
    private static EmployeeAccreditationOutputDtoMapper employeeAccreditationOutputDtoMapper;

    @BeforeAll
    static void init() {
        // Mapper
        EmployeeOutputDtoMapper employeeOutputDtoMapper = new EmployeeOutputDtoMapper(logger);
        employeeAccreditationOutputDtoMapper =
                new EmployeeAccreditationOutputDtoMapper(employeeOutputDtoMapper, logger);
    }

    @BeforeEach
    void setUp() {
        // Initiate employee accreditation
        EmployeeBuilder employeeBuilder = new EmployeeBuilder();
        employee = employeeBuilder.buildEmployee();

        EmployeeAccreditationBuilder employeeAccreditationBuilder = new EmployeeAccreditationBuilder();
        EmployeeAccreditationBuilder.employee = employee;
        employeeAccreditation = employeeAccreditationBuilder.build();
    }

    @Test
    public void shouldMapEntityToDto() {
        // when
        EmployeeAccreditationOutputDto employeeAccreditationOutputDto =
                employeeAccreditationOutputDtoMapper.entityToDto(employeeAccreditation);

        // then
        assertEquals(employeeAccreditationOutputDto.getEmployee().getEmployeeId(), employee.getId());
        assertEquals(employeeAccreditationOutputDto.getInvitedBy(), "unit test");
        assertEquals(employeeAccreditationOutputDto.getStatus(), "OPEN");
    }
}
