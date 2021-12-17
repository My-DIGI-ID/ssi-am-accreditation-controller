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

import com.bka.ssi.controller.accreditation.company.aop.utilities.ResourceReader;
import com.bka.ssi.controller.accreditation.company.application.exceptions.EmailBuildException;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 * The type Email builder.
 */
@Component
public class EmailBuilder {

    // ToDo - follow best practice of constructor-initialized fields via @Value enables
    //  convenient unit and integration testing
    private final Resource defaultEmailTemplate;
    private final String guestEmailTemplatePath;
    private final String employeeEmailTemplatePath;

    private final Logger logger;

    /**
     * Instantiates a new Email builder.
     *
     * @param defaultEmailTemplate      the default email template
     * @param guestEmailTemplatePath    the guest email template path
     * @param employeeEmailTemplatePath the employee email template path
     * @param logger                    the logger
     */
    public EmailBuilder(
        @Value("classpath:templates/email/invitation/default-invitation-email-template.html")
            Resource defaultEmailTemplate,
        @Value("${template.email.invitation.guest.path}") String guestEmailTemplatePath,
        @Value("${template.email.invitation.employee.path}") String employeeEmailTemplatePath,
        Logger logger) {
        this.defaultEmailTemplate = defaultEmailTemplate;
        this.guestEmailTemplatePath = guestEmailTemplatePath;
        this.employeeEmailTemplatePath = employeeEmailTemplatePath;
        this.logger = logger;
    }

    private String getTemplateOrDefault(String path) {
        Resource emailTemplateResource;
        if (path == null) {
            emailTemplateResource = this.defaultEmailTemplate;
        } else {
            emailTemplateResource = new FileSystemResource(path);
            if (!emailTemplateResource.exists()) {
                emailTemplateResource = this.defaultEmailTemplate;
            }
        }

        String emailTemplate = ResourceReader.asString(emailTemplateResource);
        return emailTemplate;
    }

    /**
     * Build invitation email as message byte [ ].
     *
     * @param emailAddresses the email addresses
     * @param subject        the subject
     * @param content        the content
     * @return the byte [ ]
     * @throws EmailBuildException the email build exception
     */
    public byte[] buildInvitationEmailAsMessage(List<String> emailAddresses, String subject,
        String content) throws EmailBuildException {
        // ToDo - parse all emailAddresses
        if (emailAddresses == null || emailAddresses.isEmpty() || emailAddresses.get(0) == null) {
            throw new EmailBuildException("No email address available.");
        }

        try {
            InternetAddress[] addresses = {};
            addresses =
                InternetAddress.parse(emailAddresses.get(0));

            Message message = new MimeMessage(Session.getInstance(System.getProperties()));
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(subject);
            message.setHeader("X-Unsent", "1");

            Multipart multipart = new MimeMultipart();
            BodyPart messagePart = new MimeBodyPart();
            messagePart.setContent(content, "text/html; charset=utf-8");
            multipart.addBodyPart(messagePart);

            message.setContent(multipart);

            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                message.writeTo(byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            }
        } catch (MessagingException | IOException e) {
            throw new EmailBuildException(e.getMessage());
        }
    }

    /**
     * Build guest invitation email string.
     *
     * @param guest       the guest
     * @param redirectUrl the redirect url
     * @return the string
     */
    public String buildGuestInvitationEmail(Guest guest, String redirectUrl) {
        logger.debug(
            "Building guest invitation email from template with path {}",
            this.guestEmailTemplatePath);

        String emailTemplate = this.getTemplateOrDefault(this.guestEmailTemplatePath);

        String firstName =
            guest.getCredentialOffer().getCredential().getPersona().getFirstName();
        String lastName = guest.getCredentialOffer().getCredential().getPersona().getLastName();

        String invitation = emailTemplate
            .replace("{{FIRSTNAME}}", firstName)
            .replace("{{LASTNAME}}", lastName)
            .replace("{{REDIRECT_URL}}", redirectUrl)
            .replace("\r\n", " ")
            .replace("\n", " ")
            .replace("\"", "'");

        return invitation;
    }

    /**
     * Build guest invitation email as message byte [ ].
     *
     * @param guest       the guest
     * @param redirectUrl the redirect url
     * @return the byte [ ]
     * @throws EmailBuildException the email build exception
     */
    public byte[] buildGuestInvitationEmailAsMessage(Guest guest, String redirectUrl)
        throws EmailBuildException {
        logger.debug(
            "Building guest invitation email as message from template with path {}",
            this.guestEmailTemplatePath);

        String invitation = this.buildGuestInvitationEmail(guest, redirectUrl);

        List<String> emailAddresses =
            guest.getCredentialOffer().getCredential().getContactInformation().getEmails();

        return this.buildInvitationEmailAsMessage(
            emailAddresses,
            "Invitation for Guest Credential",
            invitation
        );
    }

    /**
     * Build employee invitation email string.
     *
     * @param employee the employee
     * @param qrCodeSvg   the qr code svg
     * @param qrCodePng   the qr code png
     * @param qrSize   the qr code png
     * @return the string
     */
    public String buildEmployeeInvitationEmail(Employee employee, String qrCodeSvg, String qrCodePng, int qrSize) {
        logger.debug(
            "Building employee invitation email from template with path {}",
            this.employeeEmailTemplatePath);

        String emailTemplate = this.getTemplateOrDefault(this.employeeEmailTemplatePath);

        String firstName =
            employee.getCredentialOffer().getCredential().getPersona().getFirstName();
        String lastName = employee.getCredentialOffer().getCredential().getPersona().getLastName();

        String invitation = emailTemplate
            .replace("{{FIRSTNAME}}", firstName)
            .replace("{{LASTNAME}}", lastName)
            .replace("{{QR_CODE_SVG}}", qrCodeSvg)
            .replace("{{QR_CODE_PNG}}", qrCodePng)
            .replace("{{QR_CODE_SIZE}}", String.valueOf(qrSize))
            .replace("\r\n", " ")
            .replace("\n", " ")
            .replace("\"", "'");
        return invitation;
    }

    /**
     * Build employee invitation email as message byte [ ].
     *
     * @param employee the employee
     * @param qrCodeSvg   the qr code svg
     * @param qrCodePng   the qr code png
     * @return the byte [ ]
     * @throws EmailBuildException the email build exception
     */
    public byte[] buildEmployeeInvitationEmailAsMessage(Employee employee, String qrCodeSvg, String qrCodePng, int qrSize)
        throws EmailBuildException {
        logger.debug(
            "Building employee invitation email as message from template with path {}",
            this.employeeEmailTemplatePath);

        String invitation = this.buildEmployeeInvitationEmail(employee, qrCodeSvg, qrCodePng, qrSize);

        List<String> emailAddresses =
            employee.getCredentialOffer().getCredential().getContactInformation().getEmails();

        return this.buildInvitationEmailAsMessage(
            emailAddresses,
            "Invitation for Employee Credential",
            invitation
        );
    }

    /**
     * Build employee invitation email as message byte [ ].
     *
     * @param employee the employee
     * @param qrCodeSvg   the qr code svg
     * @param qrCodePng   the qr code png
     * @param qrSize   the qr code size
     * @return the byte [ ]
     * @throws EmailBuildException the email build exception
     */
    public byte[] buildEmployeeInvitationEmailAsMessage(Employee employee, byte[] qrCodeSvg, String qrCodePng, int qrSize)
        throws EmailBuildException {
        logger.debug(
            "Building employee invitation email as message from template with path {}",
            this.employeeEmailTemplatePath);

        String contentId = "qr-code";
        String contentIdHtml = "<img src=\"cid:qr-code\">";
        String invitation = this.buildEmployeeInvitationEmail(employee, contentIdHtml, qrCodePng, qrSize);

        List<String> emails =
            employee.getCredentialOffer().getCredential().getContactInformation().getEmails();
        if (emails == null || emails.isEmpty() || emails.get(0) == null) {
            throw new EmailBuildException("No email address available.");
        }

        try {
            InternetAddress[] addresses = {};
            addresses =
                InternetAddress.parse(emails.get(0));

            Message message = new MimeMessage(Session.getInstance(System.getProperties()));
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject("Invitation for Employee Credential");
            message.setHeader("X-Unsent", "1");

            Multipart multipart = new MimeMultipart();
            BodyPart messagePart = new MimeBodyPart();
            messagePart.setContent(invitation, "text/html; charset=utf-8");
            multipart.addBodyPart(messagePart);

            BodyPart imagePart = new MimeBodyPart();
            DataSource source = new ByteArrayDataSource(qrCodeSvg, MediaType.IMAGE_PNG_VALUE);
            imagePart.setDataHandler(new DataHandler(source));
            imagePart.setHeader("Content-ID", contentId);
            multipart.addBodyPart(imagePart);

            message.setContent(multipart);

            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                message.writeTo(byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            }
        } catch (MessagingException | IOException e) {
            throw new EmailBuildException(e.getMessage());
        }
    }
}
