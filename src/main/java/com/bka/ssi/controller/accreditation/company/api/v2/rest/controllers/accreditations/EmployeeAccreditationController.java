package com.bka.ssi.controller.accreditation.company.api.v2.rest.controllers.accreditations;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.CompletionStatusOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.EmployeeAccreditationOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations.EmployeeAccreditationOutputDtoMapper;
import com.bka.ssi.controller.accreditation.company.application.security.facade.SSOProtectedTransaction;
import com.bka.ssi.controller.accreditation.company.application.security.utilities.BearerTokenParser;
import com.bka.ssi.controller.accreditation.company.application.services.strategies.accreditations.EmployeeAccreditationService;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Employee accreditation controller",
    description = "Managing employee accreditation process")
@RestController()
@RequestMapping(value = {"/api/v2/accreditation/employee", "/api/accreditation/employee"})
public class EmployeeAccreditationController {

    private final EmployeeAccreditationService employeeAccreditationService;
    private final EmployeeAccreditationOutputDtoMapper mapper;
    private final BearerTokenParser bearerTokenParser;
    private final Logger logger;

    public EmployeeAccreditationController(
        EmployeeAccreditationService employeeAccreditationService,
        EmployeeAccreditationOutputDtoMapper mapper,
        BearerTokenParser bearerTokenParser,
        Logger logger) {
        this.employeeAccreditationService = employeeAccreditationService;
        this.mapper = mapper;
        this.bearerTokenParser = bearerTokenParser;
        this.logger = logger;
    }

    @Operation(summary = "Create accreditation with invitation email for employee",
        security = @SecurityRequirement(name = "oauth2_accreditation_party_api"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created draft accreditation with "
            + "invitation email for guest",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = EmployeeAccreditationOutputDto.class))})
    })
    @PostMapping(path = "/initiate/invitation-email/{partyId}", produces =
        {MediaType.APPLICATION_JSON_VALUE})
    @SSOProtectedTransaction(scope = "scope:create", resource = "res:employee-accreditation")
    public ResponseEntity<EmployeeAccreditationOutputDto> initiateAccreditationWithInvitationEmail(
        @PathVariable String partyId) throws Exception {
        logger.info("start: initiating new accreditation for employee via QR code in the email");

        String userName = bearerTokenParser.parseUserFromJWTToken();

        EmployeeAccreditation accreditation =
            employeeAccreditationService.initiateAccreditation(partyId, userName);

        EmployeeAccreditationOutputDto outputDto = this.mapper.entityToDto(accreditation);

        logger.info("end: initiating new accreditation for employee via QR code in the email");
        return ResponseEntity.status(201).body(outputDto);
    }

    @Operation(summary = "Offering accreditation to employee",
        security = @SecurityRequirement(name = "oauth2_accreditation_party_api"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Accreditation offered",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = EmployeeAccreditationOutputDto.class))})
    })
    @PatchMapping(path = "/offer/{accreditationId}")
    @SSOProtectedTransaction(scope = "scope:create", resource = "res:employee-accreditation")
    public ResponseEntity<EmployeeAccreditationOutputDto> offerAccreditation(
        @PathVariable String accreditationId
    ) throws Exception {
        logger.info("start: offering accreditation to the employee");

        EmployeeAccreditation accreditation =
            employeeAccreditationService.offerAccreditation(accreditationId);

        EmployeeAccreditationOutputDto outputDto = this.mapper.entityToDto(accreditation);

        logger.info("end: offering accreditation to the employee");
        return ResponseEntity.ok(outputDto);
    }

    /* TODO - BKAACMGT-165 - Currently content type of response is \*\/\* */
    @Operation(summary = "Get all employee accreditations",
        security = @SecurityRequirement(name = "oauth2_accreditation_party_api"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved all employee accreditations")
    })
    @GetMapping
    @SSOProtectedTransaction(scope = "scope:view", resource = "res:employee-accreditation")
    public ResponseEntity<List<EmployeeAccreditationOutputDto>> getAllAccreditations()
        throws Exception {
        logger.info("start: getting all employee accreditations");

        List<EmployeeAccreditationOutputDto> outputDtos = new ArrayList<>();
        this.employeeAccreditationService.getAllAccreditations().forEach(accreditation -> {
            EmployeeAccreditationOutputDto outputDto = this.mapper.entityToDto(accreditation);
            outputDtos.add(outputDto);
        });

        logger.info("end: getting all employee accreditations");
        return ResponseEntity.ok(outputDtos);
    }

    @Operation(summary = "Get accreditation instance by Id",
        security = @SecurityRequirement(name = "oauth2_accreditation_party_api"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved accreditation instance",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = EmployeeAccreditationOutputDto.class))})
    })
    @GetMapping(path = "/{accreditationId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @SSOProtectedTransaction(scope = "scope:view", resource = "res:employee-accreditation")
    public ResponseEntity<EmployeeAccreditationOutputDto> getAccreditationById(
        @PathVariable String accreditationId)
        throws Exception {
        logger.info("start: getting accreditation instance by Id");

        EmployeeAccreditation accreditation =
            this.employeeAccreditationService.getAccreditationById(accreditationId);

        EmployeeAccreditationOutputDto outputDto = this.mapper.entityToDto(accreditation);

        logger.info("end: getting accreditation instance by Id");
        return ResponseEntity.ok(outputDto);
    }

    @Operation(summary = "Polling endpoint to check if accreditation is complete")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Accreditation completed",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = CompletionStatusOutputDto.class))})
    })
    @GetMapping(path = "/validate/accreditation-process-completion/{accreditationId}", produces =
        {MediaType.APPLICATION_JSON_VALUE})
    /* ! Public API */
    public ResponseEntity<CompletionStatusOutputDto> validateAccreditationProcessCompletion(
        @PathVariable String accreditationId)
        throws Exception {

        boolean accreditationProcessCompleted =
            this.employeeAccreditationService.isAccreditationCompleted(accreditationId);

        CompletionStatusOutputDto outputDto = new CompletionStatusOutputDto(
            "accreditationProcessCompleted",
            accreditationProcessCompleted
        );

        logger.info(
            "polled Accreditation process completion status. Completed: " + outputDto.isStatus());
        return ResponseEntity.status(200).body(outputDto);
    }

    @Operation(summary = "Revoke accreditation of employee",
        security = @SecurityRequirement(name = "oauth2_accreditation_party_api"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Accreditation revoked",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = EmployeeAccreditationOutputDto.class))})
    })
    @PatchMapping(path = "/revoke/{accreditationId}",
        produces = {MediaType.APPLICATION_JSON_VALUE})
    @SSOProtectedTransaction(scope = "scope:delete", resource = "res:employee-accreditation")
    public ResponseEntity<EmployeeAccreditationOutputDto> revokeAccreditation(
        @PathVariable String accreditationId) throws Exception {
        logger.info("start: revoking accreditation of employee");

        EmployeeAccreditation accreditation =
            this.employeeAccreditationService.revokeAccreditation(accreditationId);

        EmployeeAccreditationOutputDto outputDto = mapper.entityToDto(accreditation);

        logger.info("end: revoking accreditation of employee");
        return ResponseEntity.ok(outputDto);
    }
}
