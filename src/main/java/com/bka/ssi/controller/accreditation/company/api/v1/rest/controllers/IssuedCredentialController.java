package com.bka.ssi.controller.accreditation.company.api.v1.rest.controllers;

import com.bka.ssi.controller.accreditation.company.api.v1.rest.dto.output.IssuedCredentialOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Issued credential controller v1", description = "Handle retrieval and revocation of "
    + "issued credentials")
@RestController("issuedCredentialControllerV1")
@SecurityRequirement(name = "oauth2_accreditation_party_api")
@RequestMapping(value = {"/api/v1/issued-credential", "/api/issued-credential"})
public class IssuedCredentialController {

    private final Logger logger;

    public IssuedCredentialController(Logger logger) {
        this.logger = logger;
    }

    /* TODO - BKAACMGT-165 - Currently content type of response is \*\/\* */
    @Operation(summary = "Get all issued credentials")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved all issued credentials")})
    @GetMapping()
    public ResponseEntity<List<IssuedCredentialOutputDTO>> getAllIssuedCredentials()
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation getAllIssuedCredentials in IssuedCredentialController is not yet "
                + "implemented");
    }

    @Operation(summary = "Get issued credential by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved issued credential", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation =
                IssuedCredentialOutputDTO.class))})})
    @GetMapping("/{issuedCredentialId}")
    public ResponseEntity<IssuedCredentialOutputDTO> getIssuedCredentialById(
        @PathVariable String issuedCredentialId)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation getIssuedCredentialById in IssuedCredentialController is not yet "
                + "implemented");
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Revoked credential", content = @Content)})
    @DeleteMapping("/{issuedCredentialId}")
    public ResponseEntity<Void> deleteIssuedCredentialById(@PathVariable String issuedCredentialId,
        @RequestParam(name = "Revoke credential", required = false, defaultValue = "true")
            Boolean revoke)
        throws Exception {
        throw new UnsupportedOperationException(
            "Operation deleteIssuedCredentialById in IssuedCredentialController is not yet "
                + "implemented");
    }
}
