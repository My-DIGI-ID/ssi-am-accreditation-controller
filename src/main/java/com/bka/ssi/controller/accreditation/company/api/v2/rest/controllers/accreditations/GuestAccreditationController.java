/*
 * Copyright 2021 Bundesrepublik Deutschland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bka.ssi.controller.accreditation.company.api.v2.rest.controllers.accreditations;

import com.bka.ssi.controller.accreditation.company.aop.configuration.security.ApiKeyConfiguration;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.CompletionStatusOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationOpenOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationPrivateOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationQrCodeOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations.GuestAccreditationOutputDtoMapper;
import com.bka.ssi.controller.accreditation.company.application.security.authentication.AuthenticationService;
import com.bka.ssi.controller.accreditation.company.application.security.facade.APIKeyProtectedTransaction;
import com.bka.ssi.controller.accreditation.company.application.security.facade.GuestProtectedTransaction;
import com.bka.ssi.controller.accreditation.company.application.security.facade.SSOProtectedTransaction;
import com.bka.ssi.controller.accreditation.company.application.security.utilities.BearerTokenParser;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.accreditations.GuestAccreditationPrivateInfoInputDto;
import com.bka.ssi.controller.accreditation.company.application.services.strategies.accreditations.GuestAccreditationService;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.enums.GuestAccreditationStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

/**
 * The type Guest accreditation controller.
 */
@Tag(name = "Guest accreditation controller", description = "Managing guest accreditation process")
@RestController()
@RequestMapping(value = {"/api/v2/accreditation/guest", "/api/accreditation/guest"})
public class GuestAccreditationController {

    private final GuestAccreditationService guestAccreditationService;
    private final GuestAccreditationOutputDtoMapper mapper;
    private final AuthenticationService authenticationService;
    private final BearerTokenParser bearerTokenParser;
    private final Logger logger;

    /**
     * Instantiates a new Guest accreditation controller.
     *
     * @param guestAccreditationService         the guest accreditation service
     * @param guestAccreditationOutputDtoMapper the guest accreditation output dto mapper
     * @param authenticationService             the authentication service
     * @param bearerTokenParser                 the bearer token parser
     * @param logger                            the logger
     */
    public GuestAccreditationController(
        GuestAccreditationService guestAccreditationService,
        GuestAccreditationOutputDtoMapper guestAccreditationOutputDtoMapper,
        AuthenticationService authenticationService, BearerTokenParser bearerTokenParser,
        Logger logger) {
        this.guestAccreditationService = guestAccreditationService;
        this.mapper = guestAccreditationOutputDtoMapper;
        this.authenticationService = authenticationService;
        this.bearerTokenParser = bearerTokenParser;
        this.logger = logger;
    }

    /**
     * Initiate accreditation with invitation email response entity.
     *
     * @param partyId the party id
     * @return the response entity
     * @throws Exception the exception
     */
    @Operation(summary = "Create accreditation with invitation email for guest",
        security = @SecurityRequirement(name = "oauth2_accreditation_party_api"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Created draft accreditation with invitation email for guest",
            content = @Content(schema = @Schema(type = "file")))
    })
    @PostMapping(path = "/initiate/invitation-email/{partyId}")
    @SSOProtectedTransaction(scope = "scope:create", resource = "res:guest-accreditation")
    public ResponseEntity<InputStreamResource> initiateAccreditationWithInvitationEmail(
        @PathVariable String partyId) throws Exception {
        logger.info("start: initiating new accreditation for guest via link in the email");

        String userName = bearerTokenParser.parseUserFromJWTToken();

        GuestAccreditation accreditation =
            guestAccreditationService.initiateAccreditation(partyId, userName);

        byte[] email =
            guestAccreditationService
                .generateAccreditationWithEmailAsMessage(accreditation.getId());

        try (InputStream inputStream = new ByteArrayInputStream(email);) {
            logger.info("end: initiating new accreditation for guest via link in the email");
            return ResponseEntity.status(201)
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(email.length)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + accreditation.getId() + "-invitation.eml\"")
                .body(new InputStreamResource(inputStream));
        }
    }

    /**
     * Initiate accreditation with qr code response entity.
     *
     * @param partyId the party id
     * @return the response entity
     * @throws UnsupportedOperationException the unsupported operation exception
     */
    /* Out of MVP Scope */
    @Operation(summary = "Create accreditation directly with QR code for guest",
        security = @SecurityRequirement(name = "oauth2_accreditation_party_api"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created draft accreditation with "
            + "QR code for guest",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationOpenOutputDto.class))})
    })
    @PostMapping(path = "/initiate/qr-code/{partyId}", produces =
        {MediaType.APPLICATION_JSON_VALUE})
    @SSOProtectedTransaction(scope = "scope:create", resource = "res:guest-accreditation")
    public ResponseEntity<GuestAccreditationOpenOutputDto> initiateAccreditationWithQrCode(
        @PathVariable String partyId)
        throws UnsupportedOperationException {
        /* This endpoint is kept for API overview - out of scope for MVP */
        throw new UnsupportedOperationException(
            "Operation initiateAccreditationWithQrCode is not supported for MVP version of Guest "
                + "Accreditation");
    }

    /**
     * Proceed accreditation with qr code response entity.
     *
     * @param accreditationId the accreditation id
     * @return the response entity
     * @throws Exception the exception
     */
    @Operation(summary = "Proceed with accreditation by generating QR code to "
        + "establish connection with guest's wallet")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Created QR code for guest to proceed with accreditation",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationQrCodeOutputDto.class))})
    })
    @PatchMapping(path = "/proceed/qr-code/{accreditationId}", produces =
        {MediaType.APPLICATION_JSON_VALUE})
    /* ! Public API */
    public ResponseEntity<GuestAccreditationQrCodeOutputDto> proceedAccreditationWithQrCode(
        @PathVariable String accreditationId)
        throws Exception {
        logger.info("start: proceeding with accreditation for guest via qr code connection offer");

        GuestAccreditation guestAccreditation =
            guestAccreditationService.proceedWithAccreditation(accreditationId);

        GuestAccreditationQrCodeOutputDto outputDto = mapper.entityToQrCodeDto(guestAccreditation);

        logger.info("end: proceeding with accreditation for guest via qr code connection offer");
        return ResponseEntity.status(200).body(outputDto);
    }

    /**
     * Proceed accreditation with deep link response entity.
     *
     * @return the response entity
     * @throws UnsupportedOperationException the unsupported operation exception
     */
    /* Out of MVP Scope */
    @Operation(summary = "Proceed with accreditation by generating deep link to "
        + "establish connection with guest's wallet")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Created draft accreditation with "
            + "invitation email for guest",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationOpenOutputDto.class))})
    })
    @PatchMapping(path = "/proceed/deep-link", produces =
        {MediaType.APPLICATION_JSON_VALUE})
    /* ! Public API */
    public ResponseEntity<GuestAccreditationOpenOutputDto> proceedAccreditationWithDeepLink()
        throws UnsupportedOperationException {
        /* This endpoint is kept for API overview - out of scope for MVP */
        throw new UnsupportedOperationException(
            "Operation proceedAccreditationWithDeepLink is not supported for MVP version of Guest "
                + "Accreditation");
    }

    /**
     * Validate basis id process completion response entity.
     *
     * @param accreditationId the accreditation id
     * @return the response entity
     * @throws Exception the exception
     */
    @Operation(summary = "Polling endpoint to check if BasisId processing is complete")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "BasisId process status validated",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = CompletionStatusOutputDto.class))})
    })
    @GetMapping(path = "/validate/basis-id-process-completion/{accreditationId}", produces =
        {MediaType.APPLICATION_JSON_VALUE})
    /* ! Public API */
    public ResponseEntity<CompletionStatusOutputDto> validateBasisIdProcessCompletion(
        @PathVariable String accreditationId)
        throws Exception {

        boolean basisIdProcessCompleted =
            guestAccreditationService.isGuestBasisIdValidationCompleted(accreditationId);

        CompletionStatusOutputDto outputDto;

        if (basisIdProcessCompleted) {
            String actionToken =
                this.authenticationService.issueGuestAccessToken(accreditationId).getId();

            outputDto = new CompletionStatusOutputDto(
                "basisIdProcessCompleted",
                true,
                actionToken
            );
        } else {
            outputDto = new CompletionStatusOutputDto(
                "basisIdProcessCompleted",
                false,
                null
            );
        }

        logger.info("polled BasisId process completion status. Completed: " + outputDto.isStatus());
        return ResponseEntity.status(200).body(outputDto);
    }

    /**
     * Add proprietary information from guest response entity.
     *
     * @param accreditationId the accreditation id
     * @param inputDto        the input dto
     * @return the response entity
     * @throws Exception the exception
     */
    @Operation(summary = "Adding proprietary information by guest to accreditation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Appended additional information about "
            + "guest to accreditation",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationPrivateOutputDto.class))})
    })
    @PatchMapping(path = "/append/guest-proprietary-information/{accreditationId}",
        produces = {MediaType.APPLICATION_JSON_VALUE})
    @GuestProtectedTransaction
    public ResponseEntity<GuestAccreditationPrivateOutputDto> addProprietaryInformationFromGuest(
        @PathVariable String accreditationId,
        @Valid @RequestBody GuestAccreditationPrivateInfoInputDto inputDto)
        throws Exception {
        logger.info("start: adding proprietary information by the guest");

        GuestAccreditation guestAccreditation = guestAccreditationService
            .appendWithProprietaryInformationFromGuest(accreditationId, inputDto);

        GuestAccreditationPrivateOutputDto outputDto = mapper
            .entityToPrivateDto(guestAccreditation);

        logger.info("end: adding proprietary information by the guest");
        return ResponseEntity.status(200).body(outputDto);
    }

    /**
     * Accept accreditation terms and conditions response entity.
     *
     * @param accreditationId the accreditation id
     * @return the response entity
     * @throws UnsupportedOperationException the unsupported operation exception
     */
    @Operation(summary = "Consent terms and conditions by guest")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated accreditation according to "
            + "terms and conditions concession by guest",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationOpenOutputDto.class))})
    })
    @PatchMapping(path = "/append/consent-terms-and-conditions/{accreditationId}", produces =
        {MediaType.APPLICATION_JSON_VALUE})
    @GuestProtectedTransaction
    public ResponseEntity<GuestAccreditationOpenOutputDto> acceptAccreditationTermsAndConditions(
        @PathVariable String accreditationId)
        throws UnsupportedOperationException {
        /* This endpoint is kept for API overview - out of scope for MVP */
        throw new UnsupportedOperationException(
            "Operation acceptAccreditationTermsAndConditions is not supported");
    }

    /**
     * Offer accreditation response entity.
     *
     * @param accreditationId the accreditation id
     * @return the response entity
     * @throws Exception the exception
     */
    @Operation(summary = "Offering accreditation to guest")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Accreditation offered",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationOpenOutputDto.class))})
    })
    @PatchMapping(path = "/offer/{accreditationId}", produces =
        {MediaType.APPLICATION_JSON_VALUE})
    @GuestProtectedTransaction
    public ResponseEntity<GuestAccreditationOpenOutputDto> offerAccreditation(
        @PathVariable String accreditationId) throws Exception {
        logger.info("start: offering accreditation to the guest");

        GuestAccreditation guestAccreditation =
            guestAccreditationService.offerAccreditation(accreditationId);

        GuestAccreditationOpenOutputDto outputDto = mapper
            .entityToOpenDto(guestAccreditation);

        logger.info("end: offering accreditation to the guest");
        return ResponseEntity.ok(outputDto);
    }

    /**
     * Validate accreditation process completion response entity.
     *
     * @param accreditationId the accreditation id
     * @return the response entity
     * @throws Exception the exception
     */
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
            guestAccreditationService.isAccreditationCompleted(accreditationId);

        CompletionStatusOutputDto outputDto = new CompletionStatusOutputDto(
            "accreditationProcessCompleted",
            accreditationProcessCompleted
        );

        logger.info(
            "polled Accreditation process completion status. Completed: " + outputDto.isStatus());
        return ResponseEntity.status(200).body(outputDto);
    }

    /**
     * Gets open accreditation by id.
     *
     * @param accreditationId the accreditation id
     * @return the open accreditation by id
     * @throws Exception the exception
     */
    @Operation(summary = "Get open accreditation instance by Id",
        security = @SecurityRequirement(name = "oauth2_accreditation_party_api"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved open accreditation instance",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationOpenOutputDto.class))})
    })
    @GetMapping(path = "/open/{accreditationId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @SSOProtectedTransaction(scope = "scope:view", resource = "res:guest-accreditation")
    public ResponseEntity<GuestAccreditationOpenOutputDto> getOpenAccreditationById(
        @PathVariable String accreditationId)
        throws Exception {
        logger.info("start: getting open accreditation instance by Id");

        String userName = bearerTokenParser.parseUserFromJWTToken();

        GuestAccreditation guestAccreditation =
            guestAccreditationService.getAccreditationById(accreditationId, userName);

        GuestAccreditationOpenOutputDto outputDto = mapper
            .entityToOpenDto(guestAccreditation);

        logger.info("end: getting open accreditation instance by Id");
        return ResponseEntity.ok(outputDto);
    }

    /**
     * Gets private accreditation by id.
     *
     * @param accreditationId the accreditation id
     * @return the private accreditation by id
     * @throws Exception the exception
     */
    @Operation(summary = "Get private accreditation instance by Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved private accreditation instance",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationPrivateOutputDto.class))})
    })
    @GetMapping(path = "/private/{accreditationId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @GuestProtectedTransaction
    public ResponseEntity<GuestAccreditationPrivateOutputDto> getPrivateAccreditationById(
        @PathVariable String accreditationId)
        throws Exception {
        logger.info("start: getting private accreditation instance by Id");

        GuestAccreditation guestAccreditation =
            guestAccreditationService.getAccreditationById(accreditationId);

        GuestAccreditationPrivateOutputDto outputDto =
            mapper
                .entityToPrivateDto(guestAccreditation);

        logger.info("end: getting accreditation instance by Id");
        return ResponseEntity.status(200).body(outputDto);
    }

    /**
     * Gets unique accreditation by party params.
     *
     * @param referenceBasisId the reference basis id
     * @param firstName        the first name
     * @param lastName         the last name
     * @param dateOfBirth      the date of birth
     * @param companyName      the company name
     * @param validFrom        the valid from
     * @param validUntil       the valid until
     * @param invitedBy        the invited by
     * @return the unique accreditation by party params
     * @throws Exception the exception
     */
    /* Warning! Technical debt.
     * In target architecture this endpoint will not be needed - it is a workaround.
     * It is required for Verification Controller to get information about specific accreditation
     * without knowing accrediationId, in order to check if accreditation is still valid.
     * Target design would imply storing accreditationId on credential, removing redundant
     * information from credentials, implement accreditation validity check in this controller,
     * instead of verification controller, trigger revoke from this controller when accreditation
     * no longer valid, verification controller just to rely on revoke status.
     */
    @Operation(summary = "Query unique accreditation instance by query parameters",
        security = @SecurityRequirement(name = "api_key_accr_veri_api"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved unique accreditation instance",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationPrivateOutputDto.class))})
    })
    @GetMapping(path = "/private", produces = {MediaType.APPLICATION_JSON_VALUE})
    @APIKeyProtectedTransaction(id = ApiKeyConfiguration.API_KEY_ID)
    public ResponseEntity<GuestAccreditationPrivateOutputDto> getUniqueAccreditationByPartyParams(
        /* TODO - Minimum set of query parameters - remove redundant if possible */
        @RequestParam String referenceBasisId,
        @RequestParam String firstName, @RequestParam String lastName,
        @RequestParam String dateOfBirth, @RequestParam String companyName,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            ZonedDateTime validFrom,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime validUntil,
        @RequestParam String invitedBy)
        throws Exception {
        logger.info("start: query unique accreditation by party query parameters");

        GuestAccreditation guestAccreditation =
            guestAccreditationService
                .getUniqueAccreditationByPartyParams(URLDecoder.decode(referenceBasisId, "UTF-8"),
                    URLDecoder.decode(firstName, "UTF-8"), URLDecoder.decode(lastName, "UTF-8"),
                    dateOfBirth, URLDecoder.decode(companyName, "UTF-8"), validFrom,
                    validUntil, URLDecoder.decode(invitedBy, "UTF-8"));

        GuestAccreditationPrivateOutputDto outputDto = mapper
            .entityToPrivateDto(guestAccreditation);

        logger.info("end: query unique accreditation by party query parameters");
        return ResponseEntity.status(200).body(outputDto);
    }

    /**
     * Cleanup guest information on checkout response entity.
     *
     * @param accreditationId the accreditation id
     * @return the response entity
     * @throws Exception the exception
     */
    /* Warning! Technical debt.
     * In target architecture this endpoint will not be needed - it is a workaround.
     * It is required for Verification Controller to cleanup personal information about guest
     * after checkout occurs
     */
    @Operation(summary = "Cleanup party personal information by accreditationId",
        security = @SecurityRequirement(name = "api_key_accr_veri_api"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved clean accreditation instance",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationPrivateOutputDto.class))})
    })
    @PatchMapping(path = "/append/clean-guest-information/{accreditationId}", produces =
        {MediaType.APPLICATION_JSON_VALUE})
    @APIKeyProtectedTransaction(id = ApiKeyConfiguration.API_KEY_ID)
    public ResponseEntity<GuestAccreditationPrivateOutputDto> cleanupGuestInformationOnCheckout(
        @PathVariable String accreditationId) throws Exception {
        logger.info("start: cleanup party personal information by accreditationId");

        GuestAccreditation guestAccreditation =
            guestAccreditationService.cleanGuestInformationOnCheckout(accreditationId);

        GuestAccreditationPrivateOutputDto outputDto = mapper
            .entityToPrivateDto(guestAccreditation);

        logger.info("end: cleanup party personal information by accreditationId");
        return ResponseEntity.status(200).body(outputDto);
    }

    /**
     * Revoke accreditation response entity.
     *
     * @param accreditationId the accreditation id
     * @return the response entity
     * @throws Exception the exception
     */
    /* Technical Debt.: endpoints /revoke/checkout/{accreditationId} and /revoke/{accreditationId}
     * only differ by the security guard. /revoke/{accreditationId} is protected by keycloak as IAM
     * and /revoke/checkout/{accreditationId} by API Key.
     */
    @Operation(summary = "Revoke accreditation of guest", security = @SecurityRequirement(name =
        "oauth2_accreditation_party_api"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Accreditation revoked",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationOpenOutputDto.class))})
    })
    @PatchMapping(path = "/revoke/{accreditationId}", produces =
        {MediaType.APPLICATION_JSON_VALUE})
    @SSOProtectedTransaction(scope = "scope:delete", resource = "res:guest-accreditation")
    public ResponseEntity<GuestAccreditationOpenOutputDto> revokeAccreditation(
        @PathVariable String accreditationId) throws Exception {
        logger.info("start: revoking accreditation of guest");

        GuestAccreditation guestAccreditation =
            this.guestAccreditationService.revokeAccreditation(accreditationId);

        GuestAccreditationOpenOutputDto outputDto = mapper.entityToOpenDto(guestAccreditation);

        logger.info("end: revoking accreditation of guest");
        return ResponseEntity.ok(outputDto);
    }

    /**
     * Revoke accreditation on checkout response entity.
     *
     * @param accreditationId the accreditation id
     * @return the response entity
     * @throws Exception the exception
     */
    @Operation(summary = "Revoke accreditation of guest",
        security = @SecurityRequirement(name = "api_key_accr_veri_api"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Accreditation revoked",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationOpenOutputDto.class))})
    })
    @PatchMapping(path = "/revoke/checkout/{accreditationId}", produces =
        {MediaType.APPLICATION_JSON_VALUE})
    @APIKeyProtectedTransaction(id = ApiKeyConfiguration.API_KEY_ID)
    public ResponseEntity<GuestAccreditationOpenOutputDto> revokeAccreditationOnCheckout(
        @PathVariable String accreditationId) throws Exception {
        logger.info("start: revoking accreditation of guest");

        GuestAccreditation guestAccreditation =
            this.guestAccreditationService.revokeAccreditation(accreditationId);

        GuestAccreditationOpenOutputDto outputDto = mapper.entityToOpenDto(guestAccreditation);

        logger.info("end: revoking accreditation of guest");
        return ResponseEntity.ok(outputDto);
    }

    /**
     * Gets all accreditations.
     *
     * @return the all accreditations
     * @throws Exception the exception
     */
    /* TODO - BKAACMGT-165 - Currently content type of response is \*\/\* */
    @Operation(summary = "Get all guest accreditations",
        security = @SecurityRequirement(name = "oauth2_accreditation_party_api"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved all guest accreditations")
    })
    @GetMapping
    @SSOProtectedTransaction(scope = "scope:view", resource = "res:guest-accreditation")
    public ResponseEntity<List<GuestAccreditationOpenOutputDto>> getAllAccreditations()
        throws Exception {
        logger.info("start: getting all guest accreditations");

        String userName = bearerTokenParser.parseUserFromJWTToken();

        List<GuestAccreditationOpenOutputDto> outputDtos = new ArrayList<>();
        this.guestAccreditationService.getAllAccreditations(userName).forEach(accreditation -> {
            GuestAccreditationOpenOutputDto outputDto = mapper.entityToOpenDto(accreditation);
            outputDtos.add(outputDto);
        });

        logger.info("end: getting all guest accreditations");
        return ResponseEntity.ok(outputDtos);
    }


    /**
     * Proceed accreditation with qr code response entity.
     *
     * @param accreditationId the accreditation id
     * @param status          the status
     * @return the response entity
     * @throws Exception the exception
     */
    @Operation(summary = "Update accreditation status on checkin",
        security = @SecurityRequirement(name = "api_key_accr_veri_api"))
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Updated accreditation status",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationOpenOutputDto.class))})
    })
    @PatchMapping(path = "/update/{accreditationId}", produces =
        {MediaType.APPLICATION_JSON_VALUE})
    @APIKeyProtectedTransaction(id = ApiKeyConfiguration.API_KEY_ID)
    public ResponseEntity<GuestAccreditationOpenOutputDto> proceedAccreditationWithQrCode(
        @PathVariable String accreditationId, @RequestParam GuestAccreditationStatus status)
        throws Exception {
        logger.info("start: updating accreditation status for guest");

        GuestAccreditation guestAccreditation =
            guestAccreditationService.updateAccreditationStatus(accreditationId, status);
        GuestAccreditationOpenOutputDto outputDto = mapper
            .entityToPrivateDto(guestAccreditation);

        logger.info("end: proceeding with accreditation for guest via qr code connection offer");
        return ResponseEntity.status(200).body(outputDto);
    }

}
