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

@Component
public class EmailBuilder {

    // ToDo - follow best practice of constructor-initialized fields via @Value enables
    //  convenient unit and integration testing
    private final Resource defaultEmailTemplate;
    private final String guestEmailTemplatePath;
    private final String employeeEmailTemplatePath;

    private final Logger logger;

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

    public String buildEmployeeInvitationEmail(Employee employee, String qrCode) {
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
            .replace("{{QR_CODE}}", qrCode)
            .replace("\r\n", " ")
            .replace("\n", " ")
            .replace("\"", "'");

        return invitation;
    }

    public byte[] buildEmployeeInvitationEmailAsMessage(Employee employee, String qrCode)
        throws EmailBuildException {
        logger.debug(
            "Building employee invitation email as message from template with path {}",
            this.employeeEmailTemplatePath);

        String invitation = this.buildEmployeeInvitationEmail(employee, qrCode);

        List<String> emailAddresses =
            employee.getCredentialOffer().getCredential().getContactInformation().getEmails();

        return this.buildInvitationEmailAsMessage(
            emailAddresses,
            "Invitation for Employee Credential",
            invitation
        );
    }

    public byte[] buildEmployeeInvitationEmailAsMessage(Employee employee, byte[] qrCode)
        throws EmailBuildException {
        logger.debug(
            "Building employee invitation email as message from template with path {}",
            this.employeeEmailTemplatePath);

        String contentId = "qr-code";
        String contentIdHtml = "<img src=\"cid:qr-code\">";
        String invitation = this.buildEmployeeInvitationEmail(employee, contentIdHtml);

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
            DataSource source = new ByteArrayDataSource(qrCode, MediaType.IMAGE_PNG_VALUE);
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
