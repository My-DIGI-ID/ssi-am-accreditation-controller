package com.bka.ssi.controller.accreditation.company.application.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.EmployeeCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.credentials.GuestCredential;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.enums.CredentialType;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.domain.values.ContactInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialMetadata;
import com.bka.ssi.controller.accreditation.company.domain.values.CredentialOffer;
import com.bka.ssi.controller.accreditation.company.domain.values.GuestPrivateInformation;
import com.bka.ssi.controller.accreditation.company.domain.values.Persona;
import com.bka.ssi.controller.accreditation.company.domain.values.ValidityTimeframe;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class EmailBuilderTest {

    private final static ZonedDateTime dateTime = ZonedDateTime.of(2018, 5, 30, 12, 0, 0, 0,
        ZoneId.of("UTC"));
    private final static Logger logger = LoggerFactory.getLogger(EmailBuilderTest.class);
    private static EmailBuilder emailBuilder;
    private static QrCodeGenerator qrCodeGenerator;

    private static final String firstname = "firstname";
    private static final String lastname = "lastname";
    private static final String redirectUrl = "http://0.0.0.0";
    private static final String expectedTestGuestInvitationEmailPath =
        "src/test/resources/templates/email/invitation/test-guest-invitation-email.html";
    private static final String expectedTestEmployeeInvitationEmailPath =
        "src/test/resources/templates/email/invitation/test-employee-invitation-email.html";

    private static Guest guest;
    private static Employee employee;

    @BeforeAll
    static void setUp() throws InvalidValidityTimeframeException {
        Resource defaultEmailTemplate = new FileSystemResource(
            "src/main/resources/templates/email/invitation/default-invitation-email-template.html");
        emailBuilder = new EmailBuilder(
            defaultEmailTemplate,
            "src/main/resources/templates/email/invitation/guest-invitation-email-template.html",
            "src/main/resources/templates/email/invitation/employee-invitation-email-template.html",
            logger);
        qrCodeGenerator = new QrCodeGenerator();

        Persona persona = new Persona(
            "title",
            firstname,
            lastname
        );

        ValidityTimeframe validityTimeframe =
            new ValidityTimeframe(
                dateTime,
                dateTime);
        ContactInformation contactInformation = new ContactInformation(null, null);

        GuestPrivateInformation guestPrivateInformation = new GuestPrivateInformation(
            "dateOfBirth",
            "licencePlateNumber",
            "companyStreet",
            "companyCity",
            "companyPostCode",
            "acceptedDocument");

        GuestCredential guestCredential = new GuestCredential(
            validityTimeframe,
            persona,
            contactInformation,
            "companyName",
            "typeOfVisit",
            "location",
            "invitedBy",
            "referenceBasisId",
            guestPrivateInformation
        );

        CredentialMetadata credentialMetadata = new CredentialMetadata(CredentialType.GUEST);

        CredentialOffer<GuestCredential> guestCredentialOffer = new CredentialOffer(
            credentialMetadata,
            guestCredential
        );

        guest = new Guest("123456", guestCredentialOffer, "user1", ZonedDateTime.now());

        EmployeeCredential employeeCredential = new EmployeeCredential("employeeId",
            "employeeState", persona, contactInformation, null, null, null);

        CredentialOffer<EmployeeCredential> employeeCredentialOffer = new CredentialOffer(
            credentialMetadata,
            employeeCredential
        );

        employee = new Employee("123456", employeeCredentialOffer, "user1", ZonedDateTime.now());
    }

    @Test
    void buildGuestInvitationEmail() {
        String expectedInvitationEmail = null;
        try {
            expectedInvitationEmail =
                Files.readString(Paths.get(expectedTestGuestInvitationEmailPath),
                    StandardCharsets.US_ASCII);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        String actualInvitationEmail = emailBuilder.buildGuestInvitationEmail(guest, redirectUrl);

        assertEquals(
            expectedInvitationEmail.replace("\r\n", " ").replace("\n", " ").replace("\"", "'"),
            actualInvitationEmail);
    }

    @Test
    void buildEmployeeInvitationEmail() {
        String expectedInvitationEmail = null;
        try {
            expectedInvitationEmail =
                Files.readString(Paths.get(expectedTestEmployeeInvitationEmailPath),
                    StandardCharsets.US_ASCII);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        String qrCode = null;
        try {
            qrCode = qrCodeGenerator.generateQrCodeSvg(redirectUrl, 300, 300);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        String actualInvitationEmail =
            emailBuilder.buildEmployeeInvitationEmail(employee, qrCode);

        assertEquals(
            expectedInvitationEmail.replace("\r\n", " ").replace("\n", " ").replace("\"", "'"),
            actualInvitationEmail);
    }
}
