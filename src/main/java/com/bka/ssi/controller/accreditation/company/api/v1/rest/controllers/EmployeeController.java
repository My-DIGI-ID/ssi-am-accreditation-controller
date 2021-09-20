package com.bka.ssi.controller.accreditation.company.api.v1.rest.controllers;

import com.bka.ssi.controller.accreditation.company.api.v1.rest.dto.output.EmployeeOutputDTO;
import com.bka.ssi.controller.accreditation.company.api.v1.rest.dto.output.InvitationOutputDTO;
import com.bka.ssi.controller.accreditation.company.api.v1.rest.mappers.EmployeeOutputDTOMapper;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.EmployeeInputDto;
import com.bka.ssi.controller.accreditation.company.application.services.strategies.parties.EmployeePartyService;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

/* ToDo - v1 API needs to be updated according to new design */

@Tag(name = "Employee controller v1", description = "Handle creation and retrieval of employees")
@RestController("employeeControllerV1")
@SecurityRequirement(name = "oauth2_accreditation_party_api")
@RequestMapping(value = {"/api/v1/employee", "/api/employee"})
public class EmployeeController {

    private final EmployeePartyService employeePartyService;
    private final EmployeeOutputDTOMapper employeeOutputDTOMapper;

    private final Logger logger;

    public EmployeeController(EmployeePartyService employeePartyService,
        EmployeeOutputDTOMapper employeeOutputDTOMapper, Logger logger) {
        this.logger = logger;
        this.employeePartyService = employeePartyService;
        this.employeeOutputDTOMapper = employeeOutputDTOMapper;
    }

    @Operation(summary = "Create employee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created new employee",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = EmployeeOutputDTO.class))})
    })
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EmployeeOutputDTO> createEmployee(
        @Valid @RequestBody EmployeeInputDto inputDTO) throws Exception {
        logger.info("start: storing new employee");
        Employee employee = employeePartyService.createParty(inputDTO);
        EmployeeOutputDTO registeredEmployeeOutputDTO =
            employeeOutputDTOMapper.employeeToEmployeeOutputDTO(employee);
        logger.info("end: storing new employee");
        return ResponseEntity.status(201).body(registeredEmployeeOutputDTO);
    }

    /* TODO - BKAACMGT-165 - Currently content type of response is \*\/\* */
    @Operation(summary = "Create employee by CSV")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created new employees by CSV")
    })
    @PostMapping(path = "/csv", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<EmployeeOutputDTO>> createEmployeesByCSV(
        @RequestBody MultipartFile file)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation createEmployeesByCSV in EmployeeController is not yet implemented");
    }

    /* TODO - BKAACMGT-165 - Currently content type of response is \*\/\* */
    @Operation(summary = "Get all employees")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved all employees")
    })
    @GetMapping()
    public ResponseEntity<List<EmployeeOutputDTO>> getAllEmployees() throws Exception {
        logger.info("start: getting all employees");
        List<EmployeeOutputDTO> employeesOutputDTO = new ArrayList<>();
        employeePartyService.getAllParties().forEach(employee -> {
            EmployeeOutputDTO output =
                employeeOutputDTOMapper.employeeToEmployeeOutputDTO(employee);
            employeesOutputDTO.add(output);
        });
        logger.info("end: getting all employees");
        return ResponseEntity.ok(employeesOutputDTO);
    }

    @Operation(summary = "Get employee by Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved employee",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = EmployeeOutputDTO.class))})
    })
    @GetMapping(path = "/{employeeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EmployeeOutputDTO> getEmployeeById(@PathVariable String employeeId)
        throws Exception {
        logger.info("start: getting employee by ID");
        Employee employee = employeePartyService.getPartyById(employeeId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        EmployeeOutputDTO registeredEmployeeOutputDTO =
            employeeOutputDTOMapper.employeeToEmployeeOutputDTO(employee);
        logger.info("end: getting employee by ID");
        return ResponseEntity.ok(registeredEmployeeOutputDTO);
    }

    @Operation(summary = "Update employee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated employee",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = EmployeeOutputDTO.class))})
    })
    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EmployeeOutputDTO> updateEmployee(
        @Valid @RequestBody EmployeeInputDto inputDTO) throws Exception {
        throw new UnsupportedOperationException(
            "Operation updateEmployee in EmployeeController is not yet implemented");
    }


    @Operation(summary = "Delete employee by Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Deleted employee",
            content = @Content)
    })
    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable String employeeId)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation deleteEmployeeById in EmployeeController is not yet implemented");
    }

    @Operation(summary = "Create invitation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Created invitation",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = InvitationOutputDTO.class))})
    })
    @PostMapping(path = "/{employeeId}/create-invitation", produces = {
        MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<InvitationOutputDTO> createInvitation(@PathVariable String employeeId)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation createInvitation in EmployeeController is not yet implemented");
    }

    @Operation(summary = "Create invitation by QR code")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Created invitation QR code",
            content = {@Content(mediaType = "image/png")})
    })
    @PostMapping(path = "/{employeeId}/create-invitation/qr", produces = {
        MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<InputStreamResource> createInvitationByQR(@PathVariable String employeeId)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation createInvitationByQR in EmployeeController is not yet implemented");
    }

    @Operation(summary = "Create invitation by Email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Created invitation Email",
            content = {@Content(mediaType = "text/plain")})
    })
    @PostMapping(path = "/{employeeId}/create-invitation/e-mail", produces = {
        MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<InputStreamResource> createInvitationByEmail(
        @PathVariable String employeeId)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation createInvitationByEmail in EmployeeController is not yet implemented");
    }

    @Operation(summary = "Resend credential")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resend credential",
            content = @Content)
    })
    @PostMapping(path = "/resend-credential")
    public ResponseEntity<Void> resendCredential(
        @Valid @RequestBody EmployeeInputDto inputDTO)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation resendCredential in EmployeeController is not yet implemented");
    }
}
