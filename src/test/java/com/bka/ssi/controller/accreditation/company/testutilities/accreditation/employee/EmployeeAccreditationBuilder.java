package com.bka.ssi.controller.accreditation.company.testutilities.accreditation.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.enums.EmployeeAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;
import com.bka.ssi.controller.accreditation.company.testutilities.party.employee.EmployeeBuilder;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

public class EmployeeAccreditationBuilder {
    public static Employee employee;
    public String id;
    public Correlation correlation;
    public EmployeeAccreditationStatus status;

    public EmployeeAccreditationBuilder() {
        EmployeeBuilder builder = new EmployeeBuilder();
        employee = builder.build();
    }

    public EmployeeAccreditation build() {
        if (this.status == null) {
            this.status = EmployeeAccreditationStatus.OPEN;
        }
        return new EmployeeAccreditation(id, employee, this.status,
            "unit test",
            ZonedDateTime.now(), "url", "email", "qrCode", correlation);
    }

    public void reset() {
        employee = null;
    }

    @Test
    private void buildEmployeeAccreditation() {
        employee = new EmployeeBuilder().buildEmployee();
        EmployeeAccreditation employeeAccreditation = this.build();
        assertEquals(employeeAccreditation.getStatus(), EmployeeAccreditationStatus.OPEN);
        assertEquals(employeeAccreditation.getInvitedBy(), "unit test");
        assertNull(employeeAccreditation.getId());
        this.reset();
    }
}
