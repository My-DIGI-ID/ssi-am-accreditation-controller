package com.bka.ssi.controller.accreditation.company.api.v2.rest.controllers.parties;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.EmployeeOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.parties.EmployeeOutputDtoMapper;
import com.bka.ssi.controller.accreditation.company.application.security.facade.SSOProtectedTransaction;
import com.bka.ssi.controller.accreditation.company.application.security.utilities.BearerTokenParser;
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

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

@Tag(name = "Employee party controller",
    description = "Handle creation and retrieval of employee as a party")
@RestController()
@SecurityRequirement(name = "oauth2_accreditation_party_api")
@RequestMapping(value = {"/api/v2/party/employee", "/api/party/employee"})
public class EmployeePartyController {

    private final EmployeePartyService employeePartyService;
    private final EmployeeOutputDtoMapper mapper;
    private final BearerTokenParser bearerTokenParser;
    private final Logger logger;

    public EmployeePartyController(
        EmployeePartyService employeePartyService, BearerTokenParser bearerTokenParser,
        EmployeeOutputDtoMapper employeeOutputDtoMapper, Logger logger) {
        this.employeePartyService = employeePartyService;
        this.mapper = employeeOutputDtoMapper;
        this.bearerTokenParser = bearerTokenParser;
        this.logger = logger;
    }

    @Operation(summary = "Create employee as a party")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created new employee as a party",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = EmployeeOutputDto.class))})
    })
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @SSOProtectedTransaction(scope = "scope:create", resource = "res:employee")
    public ResponseEntity<EmployeeOutputDto> createEmployee(@Valid @RequestBody
        EmployeeInputDto inputDto) throws Exception {
        logger.info("start: storing new employee as a party");

        String userName = bearerTokenParser.parseUserFromJWTToken();

        Employee employee = this.employeePartyService.createParty(inputDto, userName);
        EmployeeOutputDto outputDto = mapper.entityToDto(employee);

        logger.info("end: storing new employee as a party");
        return ResponseEntity.status(201).body(outputDto);
    }

    /* TODO - BKAACMGT-165 - Currently content type of response is \*\/\* */
    @Operation(summary = "Create employees as parties by CSV")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created new employees as parties by CSV")
    })
    @PostMapping(path = "/csv", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @SSOProtectedTransaction(scope = "scope:create", resource = "res:employee")
    public ResponseEntity<List<EmployeeOutputDto>> createEmployeeByCsv(
        @RequestBody MultipartFile file) throws Exception {
        logger.info("start: storing new employees as a parties from csv");

        String userName = bearerTokenParser.parseUserFromJWTToken();

        List<EmployeeOutputDto> outputDtos = new ArrayList<>();
        this.employeePartyService.createParty(file, userName).forEach(employee -> {
            EmployeeOutputDto outputDto = mapper.entityToDto(employee);
            outputDtos.add(outputDto);
        });

        logger.info("end: storing new employees as a parties from csv");
        return ResponseEntity.ok(outputDtos);
    }

    /* TODO - BKAACMGT-165 - Currently content type of response is \*\/\* */
    @Operation(summary = "Get all employees as parties")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved all employees as parties")
    })
    @GetMapping
    @SSOProtectedTransaction(scope = "scope:view", resource = "res:employee")
    public ResponseEntity<List<EmployeeOutputDto>> getAllEmployees()
        throws Exception {
        logger.info("start: getting all employees as parties");

        List<EmployeeOutputDto> outputDtos = new ArrayList<>();
        this.employeePartyService.getAllParties().forEach(employee -> {
            EmployeeOutputDto outputDto = mapper.entityToDto(employee);
            outputDtos.add(outputDto);
        });

        logger.info("end: getting all employees as parties");
        return ResponseEntity.ok(outputDtos);
    }

    @Operation(summary = "Get employee as a party by Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved employee as a party",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = EmployeeOutputDto.class))})
    })
    @GetMapping(path = "/{employeeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @SSOProtectedTransaction(scope = "scope:view", resource = "res:employee")
    public ResponseEntity<EmployeeOutputDto> getEmployeeById(@PathVariable String employeeId)
        throws Exception {
        logger.info("start: getting employee as a party by ID");

        Employee employee = employeePartyService.getPartyById(employeeId);

        EmployeeOutputDto outputDto = mapper.entityToDto(employee);

        logger.info("end: getting employee as a party by ID");
        return ResponseEntity.ok(outputDto);
    }

    @Operation(summary = "Update employee as a party")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated employee as a party",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = EmployeeOutputDto.class))})
    })
    @PutMapping(path = "/{employeeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @SSOProtectedTransaction(scope = "scope:update", resource = "res:employee")
    public ResponseEntity<EmployeeOutputDto> updateEmployee(@Valid @RequestBody
        EmployeeInputDto inputDto, @PathVariable String employeeId)
        throws Exception {
        logger.info("start: updating existing employee as a party");

        String userName = bearerTokenParser.parseUserFromJWTToken();

        Employee employee = this.employeePartyService.updateParty(inputDto, employeeId, userName);
        EmployeeOutputDto updatedEmployeeOutputDTO =
            mapper.entityToDto(employee);

        logger.info("end: updating existing employee as a party");
        return ResponseEntity.status(200).body(updatedEmployeeOutputDTO);
    }

    @Operation(summary = "Delete employee party")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Delete employee party", content = @Content)
    })
    @DeleteMapping(path = "/{employeeId}")
    @SSOProtectedTransaction(scope = "scope:delete", resource = "res:employee")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String employeeId) throws Exception {
        logger.info("start: deleting employee party");

        this.employeePartyService.deleteParty(employeeId);

        logger.info("end: deleting employee party");
        return ResponseEntity.status(200).build();
    }
}
