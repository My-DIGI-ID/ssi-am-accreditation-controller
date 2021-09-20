package com.bka.ssi.controller.accreditation.company.application.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidCsvFileException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.InvalidCsvFileFormatException;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.EmployeeInputDto;
import com.bka.ssi.controller.accreditation.company.application.testutils.BuildEmployeeCompositeInputDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CsvBuilderTest {

    private final static Logger logger = LoggerFactory.getLogger(CsvBuilderTest.class);
    private static CsvBuilder csvBuilder;
    private static BuildEmployeeCompositeInputDTO employeeCompositeInputDTOBuilder;
    private final List<String> header = Arrays
        .asList("title", "firstName", "lastName", "email", "primaryPhoneNumber",
            "secondaryPhoneNumber", "employeeId", "employeeState", "position", "companyName",
            "companyStreet", "companyPostalCode", "companyCity");
    private final String validCsvSeparatedByCommaWithColumnName =
        "title,firstName,lastName,email,primaryPhoneNumber,secondaryPhoneNumber,employeeId,employeeState,position,companyName,companyStreet,companyPostalCode,companyCity\n" +
            "Mr.,Test,Dummy,test@example.com,123456789,987654321,asdfgljhlh-123445,INTERNAL,Consultant,TheCompany,BakerStreet,10001,London";
    private final String validCsvSeparatedByCommaWithColumnNameCaseInsensitive =
        "TITLE,FIRSTNAME,LASTNAME,EMAIL,PRIMARYPHONENUMBER,SECONDARYPHONENUMBER,EMPLOYEEID,EMPLOYEESTATE,POSITION,COMPANYNAME,COMPANYSTREET,COMPANYPOSTALCODE,COMPANYCITY\n" +
            "Mr.,Test,Dummy,test@example.com,123456789,987654321,asdfgljhlh-123445,INTERNAL,Consultant,TheCompany,BakerStreet,10001,London";
    private final String invalidCsvSeparatedByCommaWithColumnWithNameRequiredFieldEmpty =
        "title,firstName,lastName,email,primaryPhoneNumber,secondaryPhoneNumber,employeeId,employeeState,position,companyName,companyStreet,companyPostalCode,companyCity\n" +
            "Mr.,Test,Dummy,test@example.com,123456789,987654321,asdfgljhlh-123445,INTERNAL,Consultant,TheCompany,BakerStreet,10001";
    private final String validCsvSeparatedByCommaWithDifferentColumnName =
        "title,First Name,lastName,email,primaryPhoneNumber,secondaryPhoneNumber,employeeId,employeeState,position,companyName,companyStreet,companyPostalCode,companyCity\n" +
            "Mr.,Test,Dummy,test@example.com,123456789,987654321,asdfgljhlh-123445,INTERNAL,Consultant,TheCompany,BakerStreet,10001,London";
    private final String validCsvSeparatedByCommaWithMissingColumeName =
        "title,firstName,lastName,email,primaryPhoneNumber,secondaryPhoneNumber,employeeId,employeeState,position,companyName,companyStreet,companyPostalCode\n" +
            "Mr.,Test,Dummy,test@example.com,123456789,987654321,asdfgljhlh-123445,INTERNAL,Consultant,TheCompany,BakerStreet,10001,London";
    private final String validCsvSeparatedByCommaWithMissingHeader =
        "Mr.,Test,Dummy,test@example.com,123456789,987654321,asdfgljhlh-123445,INTERNAL,Consultant,TheCompany,BakerStreet,10001,London";

    @BeforeAll
    static void setUp() {
        csvBuilder = new CsvBuilder(logger);
        employeeCompositeInputDTOBuilder = new BuildEmployeeCompositeInputDTO();
    }

    @Test
    void readValidCsvSeparatedByCommaToListByColumnName() {
        InputStream inputStream =
            new ByteArrayInputStream(validCsvSeparatedByCommaWithColumnName.getBytes());

        List<EmployeeInputDto> employees = new LinkedList<>();

        try {
            employees = csvBuilder.readCsvToListByColumnName(inputStream,
                EmployeeInputDto.class, ',');
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(1, employees.size());
        assertEquals("Mr.", employees.get(0).getTitle());
        assertEquals("Test", employees.get(0).getFirstName());
        assertEquals("Dummy", employees.get(0).getLastName());
        assertEquals("test@example.com", employees.get(0).getEmail());
        assertEquals("123456789", employees.get(0).getPrimaryPhoneNumber());
        assertEquals("987654321", employees.get(0).getSecondaryPhoneNumber());
        assertEquals("asdfgljhlh-123445", employees.get(0).getEmployeeId());
        assertEquals("INTERNAL", employees.get(0).getEmployeeState());
        assertEquals("Consultant", employees.get(0).getPosition());
        assertEquals("TheCompany", employees.get(0).getCompanyName());
        assertEquals("BakerStreet", employees.get(0).getCompanyStreet());
        assertEquals("10001", employees.get(0).getCompanyPostalCode());
        assertEquals("London", employees.get(0).getCompanyCity());
    }


    @Test
    void readValidCsvSeparatedByCommaCaseInsensitiveToListByColumnName() {
        InputStream inputStream =
            new ByteArrayInputStream(
                validCsvSeparatedByCommaWithColumnNameCaseInsensitive.getBytes());

        List<EmployeeInputDto> employees = new LinkedList<>();

        try {
            employees = csvBuilder.readCsvToListByColumnName(inputStream,
                EmployeeInputDto.class, ',');
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(1, employees.size());
        assertEquals("Mr.", employees.get(0).getTitle());
        assertEquals("Test", employees.get(0).getFirstName());
        assertEquals("Dummy", employees.get(0).getLastName());
        assertEquals("test@example.com", employees.get(0).getEmail());
        assertEquals("123456789", employees.get(0).getPrimaryPhoneNumber());
        assertEquals("987654321", employees.get(0).getSecondaryPhoneNumber());
        assertEquals("asdfgljhlh-123445", employees.get(0).getEmployeeId());
        assertEquals("INTERNAL", employees.get(0).getEmployeeState());
        assertEquals("Consultant", employees.get(0).getPosition());
        assertEquals("TheCompany", employees.get(0).getCompanyName());
        assertEquals("BakerStreet", employees.get(0).getCompanyStreet());
        assertEquals("10001", employees.get(0).getCompanyPostalCode());
        assertEquals("London", employees.get(0).getCompanyCity());
    }


    @Test
    void readValidCsvSeparatedByCommaToListByColumnPosition() {
        InputStream inputStream =
            new ByteArrayInputStream(validCsvSeparatedByCommaWithColumnName.getBytes());

        List<EmployeeInputDto> employees = new LinkedList<>();

        try {
            employees = csvBuilder.readCsvToListByColumnPosition(inputStream,
                EmployeeInputDto.class, ',');
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(1, employees.size());
        assertEquals("Mr.", employees.get(0).getTitle());
        assertEquals("Test", employees.get(0).getFirstName());
        assertEquals("Dummy", employees.get(0).getLastName());
        assertEquals("test@example.com", employees.get(0).getEmail());
        assertEquals("123456789", employees.get(0).getPrimaryPhoneNumber());
        assertEquals("987654321", employees.get(0).getSecondaryPhoneNumber());
        assertEquals("asdfgljhlh-123445", employees.get(0).getEmployeeId());
        assertEquals("INTERNAL", employees.get(0).getEmployeeState());
        assertEquals("Consultant", employees.get(0).getPosition());
        assertEquals("TheCompany", employees.get(0).getCompanyName());
        assertEquals("BakerStreet", employees.get(0).getCompanyStreet());
        assertEquals("10001", employees.get(0).getCompanyPostalCode());
        assertEquals("London", employees.get(0).getCompanyCity());
    }


    @Test
    void readInvalidCsvSeparatedByCommaWithNameRequiredFieldEmptyToListByColumn() {
        InputStream inputStream =
            new ByteArrayInputStream(
                invalidCsvSeparatedByCommaWithColumnWithNameRequiredFieldEmpty.getBytes());

        List<EmployeeInputDto> employees = new LinkedList<>();

        assertThrows(InvalidCsvFileException.class, () ->
        {
            csvBuilder.readCsvToListByColumnName(inputStream,
                EmployeeInputDto.class, ',');
        });
    }

    @Test
    void getHeaderFromCsv() {
        InputStream inputStream =
            new ByteArrayInputStream(
                validCsvSeparatedByCommaWithColumnName.getBytes());

        try {
            List<String> header = csvBuilder.getHeaderFromCsv(inputStream);

            assertEquals(13, header.size());
            assertEquals("title", header.get(0));
            assertEquals("firstName", header.get(1));
            assertEquals("lastName", header.get(2));
            assertEquals("email", header.get(3));
            assertEquals("primaryPhoneNumber", header.get(4));
            assertEquals("secondaryPhoneNumber", header.get(5));
            assertEquals("employeeId", header.get(6));
            assertEquals("employeeState", header.get(7));
            assertEquals("position", header.get(8));
            assertEquals("companyName", header.get(9));
            assertEquals("companyStreet", header.get(10));
            assertEquals("companyPostalCode", header.get(11));
            assertEquals("companyCity", header.get(12));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }


    @Test
    void getHeaderFromEmptyCsv() {
        String csv = "";
        InputStream inputStream =
            new ByteArrayInputStream(csv.getBytes());

        assertThrows(InvalidCsvFileFormatException.class, () ->
        {
            csvBuilder.getHeaderFromCsv(inputStream);
        });
    }

    @Test
    void getHeaderFromDto() {
        employeeCompositeInputDTOBuilder.reset();

        EmployeeInputDto employeeInputDto =
            employeeCompositeInputDTOBuilder.build();

        try {
            List<String> header =
                csvBuilder
                    .getHeaderFromDto(EmployeeInputDto.class, employeeInputDto);

            assertEquals(13, header.size());
            assertTrue(header.contains("TITLE"));
            assertTrue(header.contains("FIRSTNAME"));
            assertTrue(header.contains("LASTNAME"));
            assertTrue(header.contains("EMAIL"));
            assertTrue(header.contains("PRIMARYPHONENUMBER"));
            assertTrue(header.contains("SECONDARYPHONENUMBER"));
            assertTrue(header.contains("EMPLOYEEID"));
            assertTrue(header.contains("EMPLOYEESTATE"));
            assertTrue(header.contains("POSITION"));
            assertTrue(header.contains("COMPANYNAME"));
            assertTrue(header.contains("COMPANYSTREET"));
            assertTrue(header.contains("COMPANYPOSTALCODE"));
            assertTrue(header.contains("COMPANYCITY"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void validateHeader() {
        InputStream inputStream =
            new ByteArrayInputStream(
                validCsvSeparatedByCommaWithColumnName.getBytes());

        try {
            assertTrue(csvBuilder.validateHeader(header, csvBuilder.getHeaderFromCsv(inputStream)));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void validateHeaderOfValidCsvSeparatedByCommaWithDifferentColumnName() {
        InputStream inputStream =
            new ByteArrayInputStream(
                validCsvSeparatedByCommaWithDifferentColumnName.getBytes());

        assertThrows(InvalidCsvFileFormatException.class, () ->
        {
            csvBuilder.validateHeader(header, csvBuilder.getHeaderFromCsv(inputStream));
        });
    }

    @Test
    void validateHeaderOfValidCsvSeparatedByCommaWithMissingColumeName() {
        InputStream inputStream =
            new ByteArrayInputStream(
                validCsvSeparatedByCommaWithMissingColumeName.getBytes());

        assertThrows(InvalidCsvFileFormatException.class, () ->
        {
            csvBuilder.validateHeader(header, csvBuilder.getHeaderFromCsv(inputStream));
        });
    }

    @Test
    void validateHeaderOfValidCsvSeparatedByCommaWithMissingHeader() {
        InputStream inputStream =
            new ByteArrayInputStream(
                validCsvSeparatedByCommaWithMissingHeader.getBytes());

        assertThrows(InvalidCsvFileFormatException.class, () ->
        {
            csvBuilder.validateHeader(header, csvBuilder.getHeaderFromCsv(inputStream));
        });
    }
}
