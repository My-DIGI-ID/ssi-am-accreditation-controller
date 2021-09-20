package com.bka.ssi.controller.accreditation.company.application.factories.parties;

import com.bka.ssi.controller.accreditation.company.application.factories.Factory;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.EmployeeInputDto;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.ValidationService;
import com.bka.ssi.controller.accreditation.company.application.utilities.CsvBuilder;
import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.EmployeeCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.values.Address;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialMetadata;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;
import com.bka.ssi.controller.accreditation.company.domain.values.Employer;
import com.bka.ssi.controller.accreditation.company.domain.values.IdentityManagement;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.Position;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeFactory implements Factory<EmployeeInputDto, Employee> {

    /* ToDo - Set header and separator per env? */
    private final List<String> expectedEmployeeCsvHeader = Arrays.asList("title", "firstName",
        "lastName", "email", "primaryPhoneNumber", "secondaryPhoneNumber", "employeeId",
        "employeeState", "position", "companyName", "companyStreet", "companyPostalCode",
        "companyCity");
    private final char csvSeparator = ',';
    @Autowired
    private CsvBuilder csvBuilder;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private Logger logger;

    /* ToDo - Throw AlreadyExistsException in case employee exists */

    @Override
    public Employee create(EmployeeInputDto input) throws Exception {
        if (input == null) {
            return null;
        } else {
            Persona persona = input.getTitle() == null ?
                new Persona(input.getFirstName(), input.getLastName()) :
                new Persona(input.getTitle(), input.getFirstName(), input.getLastName());

            List<String> emails = Arrays.asList(input.getEmail());
            List<String> phoneNumbers = new ArrayList<>();
            if (input.getPrimaryPhoneNumber() != null) {
                phoneNumbers.add(input.getPrimaryPhoneNumber());
            }
            if (input.getSecondaryPhoneNumber() != null) {
                phoneNumbers.add(input.getSecondaryPhoneNumber());
            }
            ContactInformation contactInformation = new ContactInformation(emails, phoneNumbers);

            /* ToDo - Get reference, username and email by JWT token handler */
            IdentityManagement identityManagement = new IdentityManagement("", "", "");

            /* ToDo - What about houseNumber and doorNumber? */
            Address employerAddress = input.getCompanyCity() == null ?
                new Address(input.getCompanyPostalCode(), input.getCompanyStreet()) :
                new Address(input.getCompanyPostalCode(), input.getCompanyCity(),
                    input.getCompanyStreet());

            /* ToDo - companySubject and proofOfOwnership? */
            Employer employer = new Employer(input.getCompanyName(), employerAddress);

            Position position = input.getPosition() == null ?
                new Position() :
                new Position(input.getPosition());

            EmployeeCredential employeeCredential = input.getEmployeeId() == null ?
                new EmployeeCredential(input.getEmployeeState(),
                    persona, contactInformation, identityManagement, employer, position) :
                new EmployeeCredential(input.getEmployeeId(), input.getEmployeeState(),
                    persona, contactInformation, identityManagement, employer, position);

            /* ToDo - Set credentialMetadata here? */
            CredentialMetadata credentialMetadata = new CredentialMetadata();
            CredentialOffer<EmployeeCredential> credentialOffer =
                new CredentialOffer<>(credentialMetadata, employeeCredential);

            Employee employee = new Employee(credentialOffer);

            return employee;
        }
    }

    @Override
    public List<Employee> create(MultipartFile input) throws Exception {
        if (input == null) {
            return null;
        } else {
            List<Employee> employees = new ArrayList<>();

            List<String> actualEmployeeCsvHeader =
                this.csvBuilder.getHeaderFromCsv(input.getInputStream());

            if (this.csvBuilder.validateHeader(this.expectedEmployeeCsvHeader,
                actualEmployeeCsvHeader)) {
                List<EmployeeInputDto> dtos =
                    this.csvBuilder.readCsvToListByColumnName(input.getInputStream(),
                        EmployeeInputDto.class, this.csvSeparator);

                if (this.validationService.validate(dtos)) {
                    for (EmployeeInputDto dto : dtos) {
                        employees.add(this.create(dto));
                    }
                }
            }

            return employees;
        }
    }
}
