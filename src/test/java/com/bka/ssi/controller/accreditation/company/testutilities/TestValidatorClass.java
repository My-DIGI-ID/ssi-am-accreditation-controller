package com.bka.ssi.controller.accreditation.company.testutilities;

import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.common.NoForbiddenCharacters;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.common.NoWhitespaces;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.common.Password;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.employee.EmployeeId;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.employee.EmployeeState;

public class TestValidatorClass {

    @EmployeeState
    @EmployeeId
    @Password
    @NoWhitespaces
    @NoForbiddenCharacters
    public String field;

    public TestValidatorClass(String field) {
        this.field = field;
    }
}
