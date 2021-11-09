package com.bka.ssi.controller.accreditation.company.application.utilities;

import com.bka.ssi.controller.accreditation.company.aop.utilities.ResourceReader;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

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
}
