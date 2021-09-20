package com.bka.ssi.controller.accreditation.company.api.v2.rest.controllers.accreditations;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Employee accreditation controller", description = "")
@RestController()
@SecurityRequirement(name = "oauth2_accreditation_party_api")
@RequestMapping(value = {"/api/v2/accreditation/employee", "/api/accreditation/employee"})
public class EmployeeAccreditationController {
    /* Out of MVP Scope */
}
