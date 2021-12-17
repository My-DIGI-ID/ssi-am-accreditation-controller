/*
 * Copyright 2021 Bundesrepublik Deutschland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bka.ssi.controller.accreditation.company.application.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.testutilities.party.employee.EmployeeBuilder;
import com.bka.ssi.controller.accreditation.company.testutilities.party.guest.GuestBuilder;
import com.mongodb.assertions.Assertions;
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
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class EmailBuilderTest {

    private final static Logger logger = LoggerFactory.getLogger(EmailBuilderTest.class);
    private static EmailBuilder emailBuilder;
    private static QrCodeGenerator qrCodeGenerator;
    private final static int qrSize = 300;
    private static EmployeeBuilder employeeBuilder;
    private static GuestBuilder guestBuilder;

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
        employeeBuilder = new EmployeeBuilder();
        guestBuilder = new GuestBuilder();

        employee = employeeBuilder.buildEmployee();
        guest = guestBuilder.buildGuest();
    }

    @Test
    void buildGuestInvitationEmail() {
        String expectedInvitationEmail = null;
        try {
            expectedInvitationEmail =
                Files.readString(Paths.get(expectedTestGuestInvitationEmailPath),
                    StandardCharsets.UTF_8);
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
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        String qrCodeSvg;
        byte[] qrCodePng;
        String qrCodeSvgBase64 = null;
        String qrCodePngBase64 = null;
        try {
            qrCodeSvg = QrCodeGenerator.generateQrCodeSvg(redirectUrl, qrSize, qrSize);
            qrCodeSvgBase64 = Base64.getEncoder().encodeToString(qrCodeSvg.getBytes(StandardCharsets.UTF_8));
            qrCodePng = QrCodeGenerator.generateQrCodePng(redirectUrl, qrSize, qrSize);
            qrCodePngBase64 = Base64.getEncoder().encodeToString(qrCodePng);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        String actualInvitationEmail =
            emailBuilder.buildEmployeeInvitationEmail(employee, qrCodeSvgBase64, qrCodePngBase64, qrSize);

        assertEquals(
            expectedInvitationEmail.replace("\r\n", " ").replace("\n", " ").replace("\"", "'"),
            actualInvitationEmail);
    }

    @Test
    void buildInvitationEmailAsMessage() {
        List<String> emailAddresses =
            guest.getCredentialOffer().getCredential().getContactInformation().getEmails();
        String subject = "Headline";

        String content = null;
        try {
            content =
                Files.readString(Paths.get(expectedTestGuestInvitationEmailPath),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        byte[] email = null;
        try {
            email = emailBuilder.buildInvitationEmailAsMessage(emailAddresses, subject, content);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        String emailAsString = new String(email);
        Assertions.assertNotNull(email);
        Assertions.assertTrue(emailAsString.contains(content));
        Assertions.assertTrue(emailAsString.contains("To: " + emailAddresses.get(0)));
        Assertions.assertTrue(emailAsString.contains("Subject: " + subject));
        Assertions.assertTrue(emailAsString.contains("MIME-Version: 1.0"));
        Assertions.assertTrue(emailAsString.contains("Content-Type: multipart/mixed"));
        Assertions.assertTrue(emailAsString.contains("X-Unsent: 1"));
        Assertions.assertTrue(emailAsString.contains("Content-Type: text/html; charset=utf-8"));
    }

    @Test
    void buildEmployeeInvitationEmailAsMessageQrCode() {
        String qrCodeSvg = null;
        String qrCodePng = null;
        try {
            qrCodeSvg = QrCodeGenerator.generateQrCodeSvg(redirectUrl, qrSize, qrSize);
            qrCodePng = Arrays.toString(QrCodeGenerator.generateQrCodePng(redirectUrl, qrSize, qrSize));
        } catch (Exception e) {
            fail(e.getMessage());
        }

        byte[] email = null;
        try {
            email = emailBuilder.buildEmployeeInvitationEmailAsMessage(employee, qrCodeSvg, qrCodePng, qrSize);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        String emailAsString = new String(email);
        Assertions.assertNotNull(email);
        Assertions.assertTrue(emailAsString.contains("To: " +
            employee.getCredentialOffer().getCredential().getContactInformation().getEmails()
                .get(0)));
        Assertions
            .assertTrue(emailAsString.contains("Subject: Invitation for Employee Credential"));
        Assertions.assertTrue(emailAsString.contains("MIME-Version: 1.0"));
        Assertions.assertTrue(emailAsString.contains("Content-Type: multipart/mixed"));
        Assertions.assertTrue(emailAsString.contains("X-Unsent: 1"));
        Assertions.assertTrue(emailAsString.contains("Content-Type: text/html; charset=utf-8"));
    }

    @Test
    void buildGuestInvitationEmailAsMessage() {
        byte[] email = null;
        try {
            email = emailBuilder.buildGuestInvitationEmailAsMessage(guest, redirectUrl);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        String emailAsString = new String(email);
        Assertions.assertNotNull(email);
        Assertions.assertTrue(emailAsString.contains("To: " +
            guest.getCredentialOffer().getCredential().getContactInformation().getEmails()
                .get(0)));
        Assertions
            .assertTrue(emailAsString.contains("Subject: Invitation for Guest Credential"));
        Assertions.assertTrue(emailAsString.contains("MIME-Version: 1.0"));
        Assertions.assertTrue(emailAsString.contains("Content-Type: multipart/mixed"));
        Assertions.assertTrue(emailAsString.contains("X-Unsent: 1"));
        Assertions.assertTrue(emailAsString.contains("Content-Type: text/html; charset=utf-8"));
    }
}
