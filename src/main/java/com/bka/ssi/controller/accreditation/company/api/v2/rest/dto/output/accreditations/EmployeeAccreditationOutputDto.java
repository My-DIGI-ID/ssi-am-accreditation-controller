package com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.EmployeeOutputDto;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EmployeeAccreditationOutputDto {

    @NotEmpty
    @NotNull
    private String id;

    @NotEmpty
    @NotNull
    private EmployeeOutputDto employee;

    @NotEmpty
    @NotNull
    private String status;

    @NotEmpty
    @NotNull
    private String invitedBy;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime invitedAt;

    @NotEmpty
    @NotNull
    private String invitationUrl;

    @NotEmpty
    @NotNull
    private String invitationEmail;

    @NotEmpty
    @NotNull
    private String invitationQrCode;

    public EmployeeAccreditationOutputDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EmployeeOutputDto getEmployee() {
        return employee;
    }

    public void setEmployee(
        EmployeeOutputDto employee) {
        this.employee = employee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(String invitedBy) {
        this.invitedBy = invitedBy;
    }

    public ZonedDateTime getInvitedAt() {
        return invitedAt;
    }

    public void setInvitedAt(ZonedDateTime invitedAt) {
        this.invitedAt = invitedAt;
    }

    public String getInvitationUrl() {
        return invitationUrl;
    }

    public void setInvitationUrl(String invitationUrl) {
        this.invitationUrl = invitationUrl;
    }

    public String getInvitationEmail() {
        return invitationEmail;
    }

    public void setInvitationEmail(String invitationEmail) {
        this.invitationEmail = invitationEmail;
    }

    public String getInvitationQrCode() {
        return invitationQrCode;
    }

    public void setInvitationQrCode(String invitationQrCode) {
        this.invitationQrCode = invitationQrCode;
    }
}
