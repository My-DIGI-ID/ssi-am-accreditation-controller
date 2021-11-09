package com.bka.ssi.controller.accreditation.company.domain.entities.accreditations;

import com.bka.ssi.controller.accreditation.company.domain.entities.Accreditation;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import com.bka.ssi.controller.accreditation.company.domain.enums.EmployeeAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidAccreditationInitialStateException;
import com.bka.ssi.controller.accreditation.company.domain.specifications.accreditations.AccreditationInitialStateSpecification;
import com.bka.ssi.controller.accreditation.company.domain.values.Correlation;

import java.time.ZonedDateTime;

public class EmployeeAccreditation extends Accreditation<Employee, EmployeeAccreditationStatus> {

    private Correlation employeeCredentialIssuanceCorrelation;

    public EmployeeAccreditation(Employee party, EmployeeAccreditationStatus status,
        String invitedBy, ZonedDateTime invitedAt) {
        super(party, status, invitedBy, invitedAt);
    }

    public EmployeeAccreditation(String id, Employee party, EmployeeAccreditationStatus status,
        String invitedBy, ZonedDateTime invitedAt) {
        super(id, party, status, invitedBy, invitedAt);
    }

    public EmployeeAccreditation(String id, Employee party, EmployeeAccreditationStatus status,
        String invitedBy, ZonedDateTime invitedAt, String invitationUrl, String invitationEmail,
        String invitationQrCode) {
        super(id, party, status, invitedBy, invitedAt, invitationUrl, invitationEmail,
            invitationQrCode);
    }

    public EmployeeAccreditation(String id, Employee party, EmployeeAccreditationStatus status,
        String invitedBy, ZonedDateTime invitedAt, String invitationUrl, String invitationEmail,
        String invitationQrCode, Correlation employeeCredentialIssuanceCorrelation) {
        super(id, party, status, invitedBy, invitedAt, invitationUrl, invitationEmail,
            invitationQrCode);
        this.employeeCredentialIssuanceCorrelation = employeeCredentialIssuanceCorrelation;
    }

    public EmployeeAccreditation initiateAccreditation(String invitationUrl,
        String invitationEmail, String invitationQrCode, String connectionId)
        throws InvalidAccreditationInitialStateException {
        if (!new AccreditationInitialStateSpecification().isSatisfiedBy(this)) {
            throw new InvalidAccreditationInitialStateException();
        }

        this.invitationUrl = invitationUrl;
        this.invitationEmail = invitationEmail;
        this.invitationQrCode = invitationQrCode;

        this.employeeCredentialIssuanceCorrelation = new Correlation(connectionId);

        return this;
    }

    public EmployeeAccreditation offerAccreditation(Correlation correlation) {
        this.employeeCredentialIssuanceCorrelation = correlation;
        this.status = EmployeeAccreditationStatus.PENDING;

        return this;
    }

    public EmployeeAccreditation completeAccreditation(Correlation correlation, String issuedBy) {
        this.employeeCredentialIssuanceCorrelation = correlation;
        this.status = EmployeeAccreditationStatus.ACCEPTED;
        this.getParty().addIssuedByAndIssuedAtToCredentialMetadata(issuedBy);

        return this;
    }

    public EmployeeAccreditation deletePersonalData() {
        this.invitationEmail = "deleted";
        this.getParty().removeCredentialFromCredentialOffer();

        return this;
    }

    public EmployeeAccreditation revokeAccreditation() {
        this.status = EmployeeAccreditationStatus.REVOKED;

        return this;
    }

    public Correlation getEmployeeCredentialIssuanceCorrelation() {
        return employeeCredentialIssuanceCorrelation;
    }
}
