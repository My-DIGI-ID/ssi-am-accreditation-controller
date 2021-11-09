package com.bka.ssi.controller.accreditation.company.api.v1.rest.dto.output;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class IssuedCredentialOutputDTO {

    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String id;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime issuanceDate;
}
