package com.bka.ssi.controller.accreditation.company.application.services.dto.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.EmployeeInputDto;
import com.bka.ssi.controller.accreditation.company.application.utilities.CsvBuilderTest;
import com.bka.ssi.controller.accreditation.company.testutilities.party.employee.EmployeeInputDtoBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidationServiceTest {

    private final static Logger logger = LoggerFactory.getLogger(CsvBuilderTest.class);
    private static Validator validator;
    private static ValidationService validationService;
    private static EmployeeInputDtoBuilder employeeBuilder;
    private static ValidatorFactory validatorFactory;

    @BeforeAll
    public static void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        employeeBuilder = new EmployeeInputDtoBuilder();
        validationService = new ValidationService(validator, logger);
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @BeforeEach
    public void resetStrings() {
        this.employeeBuilder.reset();
    }

    @Test
    @Disabled
    /*
    ToDo: Same problem with Mocking of i18nMessageBuilder as in BKAACMGT-173 and BKAACMGT-174
    */
    void validEmployeeCompositeInputDTO() {
        // given:
        employeeBuilder.firstName = "Test";
        employeeBuilder.lastName = "Test";
        employeeBuilder.email = "test@example.com";
        employeeBuilder.companyName = "";
        employeeBuilder.companyCity = "";
        employeeBuilder.companyPostalCode = "";
        employeeBuilder.companyStreet = "";

        EmployeeInputDto employee = employeeBuilder.build();

        // when:
        Boolean isValid = this.validationService.validate(employee);

        // then:
        assertTrue(isValid);
    }

    @Test
    @Disabled
    /*
    ToDo: Same problem with Mocking of i18nMessageBuilder as in BKAACMGT-173 and BKAACMGT-174
    */
    void invalidEmployeeCompositeInputDTO() {
        // given:
        employeeBuilder.firstName = "Test";
        employeeBuilder.lastName = "Test";
        employeeBuilder.email = "test@example.com";
        employeeBuilder.companyName = "";
        employeeBuilder.companyCity = "";
        employeeBuilder.companyPostalCode = "";
        employeeBuilder.companyStreet = "";

        EmployeeInputDto employee = employeeBuilder.build();

        assertThrows(ConstraintViolationException.class, () -> {
            // when:
            Boolean isValid = this.validationService.validate(employee);

            // then:
            assertFalse(isValid);
        });
    }
}
