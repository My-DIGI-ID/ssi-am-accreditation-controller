package com.bka.ssi.controller.accreditation.company.api.v2.rest.controllers.accreditations;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.CompletionStatusOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationOpenOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationPrivateOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationQROutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations.GuestAccreditationOutputDtoMapper;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations.GuestAccreditationQROutputDtoMapper;
import com.bka.ssi.controller.accreditation.company.application.security.authentication.AuthenticationService;
import com.bka.ssi.controller.accreditation.company.application.security.facade.GuestProtectedTransaction;
import com.bka.ssi.controller.accreditation.company.application.security.facade.SSOProtectedTransaction;
import com.bka.ssi.controller.accreditation.company.application.security.utilities.BearerTokenParser;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.accreditations.GuestAccreditationPrivateInfoInputDto;
import com.bka.ssi.controller.accreditation.company.application.services.strategies.accreditations.GuestAccreditationService;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import java.net.URLDecoder;
import java.util.Date;
import javax.validation.Valid;

@Tag(name = "Guest accreditation controller", description = "Managing Guest accreditation process")
@RestController()
@SecurityRequirement(name = "oauth2_accreditation_party_api")
@RequestMapping(value = {"/api/v2/accreditation/guest", "/api/accreditation/guest"})
public class GuestAccreditationController {

    private final GuestAccreditationService guestAccreditationService;
    private final GuestAccreditationOutputDtoMapper guestAccreditationOutputDtoMapper;
    private final GuestAccreditationQROutputDtoMapper guestAccreditationQROutputDtoMapper;
    private final AuthenticationService authenticationService;
    private final BearerTokenParser bearerTokenParser;
    private final Logger logger;

    public GuestAccreditationController(
        GuestAccreditationService guestAccreditationService,
        GuestAccreditationOutputDtoMapper guestAccreditationOutputDtoMapper,
        GuestAccreditationQROutputDtoMapper guestAccreditationQROutputDtoMapper,
        AuthenticationService authenticationService, BearerTokenParser bearerTokenParser,
        Logger logger) {
        this.guestAccreditationService = guestAccreditationService;
        this.guestAccreditationOutputDtoMapper = guestAccreditationOutputDtoMapper;
        this.guestAccreditationQROutputDtoMapper = guestAccreditationQROutputDtoMapper;
        this.authenticationService = authenticationService;
        this.bearerTokenParser = bearerTokenParser;
        this.logger = logger;
    }

    @Operation(summary = "Client API - Create draft accreditation with invitation email for guest")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created draft accreditation with "
            + "invitation email for guest",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationOpenOutputDto.class))})
    })
    @PostMapping(path = "/initiate/invitation-email/{partyId}", produces =
        {MediaType.APPLICATION_JSON_VALUE})
    @SSOProtectedTransaction(scope = "scope:create", resource = "guest")
    public ResponseEntity<GuestAccreditationOpenOutputDto> initiateAccreditationWithInvitationEmail(
        @PathVariable String partyId)
        throws Exception {
        logger.info("start: initiating new accreditation for guest via link in the email");

        String userName = bearerTokenParser.parseUserFromJWTToken();

        GuestAccreditation guestAccreditation =
            guestAccreditationService.initiateAccreditationWithInvitationEmail(partyId, userName);

        GuestAccreditationOpenOutputDto guestAccreditationOpenOutputDto =
            guestAccreditationOutputDtoMapper
                .toGuestAccreditationOpenOutputDto(guestAccreditation);

        logger.info("end: initiating new accreditation for guest via link in the email");
        return ResponseEntity.status(201).body(guestAccreditationOpenOutputDto);
    }

    /* Out of MVP Scope */
    @Operation(summary = "Client API - Create draft accreditation directly with QR code for guest")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created draft accreditation with "
            + "QR code for guest",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationOpenOutputDto.class))})
    })
    @PostMapping(path = "/initiate/qr-code/{partyId}", produces =
        {MediaType.APPLICATION_JSON_VALUE})
    @SSOProtectedTransaction(scope = "scope:create", resource = "guest")
    public ResponseEntity<GuestAccreditationOpenOutputDto> initiateAccreditationWithQrCode(
        @PathVariable String partyId)
        throws UnsupportedOperationException {
        /* This endpoint is kept for API overview - out of scope for MVP */
        throw new UnsupportedOperationException(
            "Operation initiateAccreditationWithQrCode is not supported for MVP version of Guest "
                + "Accreditation");
    }

    @Operation(summary = "Client API - Proceed with accreditation by generating QR code to "
        + "establish connection with guest's wallet")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Created QR code for guest to proceed with accreditation",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationQROutputDto.class))})
    })
    @PatchMapping(path = "/proceed/qr-code/{accreditationId}", produces =
        {MediaType.APPLICATION_JSON_VALUE})
    /* ! Public API */
    public ResponseEntity<GuestAccreditationQROutputDto> proceedAccreditationWithQrCode(
        @PathVariable String accreditationId)
        throws Exception {
        logger.info("start: proceeding with accreditation for guest via qr code connection offer");

        GuestAccreditation guestAccreditation =
            guestAccreditationService.proceedAccreditationWithQrCode(accreditationId);

        GuestAccreditationQROutputDto qrOutputDto =
            guestAccreditationQROutputDtoMapper.entityToOutputDto(guestAccreditation);

        logger.info("end: proceeding with accreditation for guest via qr code connection offer");
        return ResponseEntity.status(200).body(qrOutputDto);
    }

    /* Out of MVP Scope */
    @Operation(summary = "Client API - Proceed with accreditation by generating deep link to "
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

    @Operation(summary = "Client API - Polling endpoint to check if BasisId processing is complete")
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

        CompletionStatusOutputDto status;

        if (basisIdProcessCompleted) {
            String actionToken =
                this.authenticationService.issueGuestAccessToken(accreditationId).getId();

            status = new CompletionStatusOutputDto(
                "basisIdProcessCompleted",
                true,
                actionToken
            );
        } else {
            status = new CompletionStatusOutputDto(
                "basisIdProcessCompleted",
                false,
                null
            );
        }

        logger.info("polled BasisId process completion status. Completed: " + status.getStatus());
        return ResponseEntity.status(200).body(status);
    }

    @Operation(summary = "Client API - Adding proprietary information by guest to accreditation")
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
        @Valid @RequestBody
            GuestAccreditationPrivateInfoInputDto guestAccreditationPrivateInfoInputDto)
        throws Exception {
        logger.info("start: adding proprietary information by the guest");

        GuestAccreditation guestAccreditation =
            guestAccreditationService
                .appendWithProprietaryInformationFromGuest(accreditationId,
                    guestAccreditationPrivateInfoInputDto);

        GuestAccreditationPrivateOutputDto guestAccreditationPrivateOutputDto =
            guestAccreditationOutputDtoMapper
                .toGuestAccreditationPrivateOutputDto(guestAccreditation);

        logger.info("end: adding proprietary information by the guest");
        return ResponseEntity.status(200).body(guestAccreditationPrivateOutputDto);
    }

    /* Out of MVP Scope */
    @Operation(summary = "Client API - Consent terms and conditions by guest")
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

    @Operation(summary = "Client API - Offering accreditation to guest")
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

        guestAccreditationService.offerAccreditation(accreditationId);

        logger.info("end: offering accreditation to the guest");
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Client API - Polling endpoint to check if accreditation is complete")
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
            guestAccreditationService.isGuestAccreditationProcessCompleted(accreditationId);

        CompletionStatusOutputDto status = new CompletionStatusOutputDto(
            "accreditationProcessCompleted",
            accreditationProcessCompleted
        );

        logger.info(
            "polled Accreditation process completion status. Completed: " + status.getStatus());
        return ResponseEntity.status(200).body(status);
    }

    @Operation(summary = "Client API - Get open accreditation instance by Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved open accreditation instance",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationOpenOutputDto.class))})
    })
    @GetMapping(path = "/open/{accreditationId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @SSOProtectedTransaction(scope = "scope:view", resource = "guest")
    public ResponseEntity<GuestAccreditationOpenOutputDto> getOpenAccreditationById(
        @PathVariable String accreditationId)
        throws Exception {
        logger.info("start: getting open accreditation instance by ID");

        GuestAccreditation guestAccreditation =
            guestAccreditationService.getAccreditationById(accreditationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        GuestAccreditationOpenOutputDto guestAccreditationOpenOutputDto =
            guestAccreditationOutputDtoMapper
                .toGuestAccreditationOpenOutputDto(guestAccreditation);

        logger.info("end: getting open accreditation instance by ID");
        return ResponseEntity.ok(guestAccreditationOpenOutputDto);
    }

    @Operation(summary = "Client API - Get private accreditation instance by Id")
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
        logger.info("start: getting private accreditation instance by ID");

        GuestAccreditation guestAccreditation =
            guestAccreditationService.getAccreditationById(accreditationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        GuestAccreditationPrivateOutputDto guestAccreditationPrivateOutputDto =
            guestAccreditationOutputDtoMapper
                .toGuestAccreditationPrivateOutputDto(guestAccreditation);

        logger.info("end: getting accreditation instance by ID");
        return ResponseEntity.status(200).body(guestAccreditationPrivateOutputDto);
    }


    /* Warning! Technical debt.
     * In target architecture this endpoint will not be needed - it is a workaround.
     * It is required for Verification Controller to get information about specific accreditation
     * without knowing accrediationId, in order to check if accreditation is still valid.
     * Target design would imply storing accreditationId on credential, removing redundant
     * information from credentials, implement accreditation validity check in this controller,
     * instead of verification controller, trigger revoke from this controller when accreditation
     * no longer valid, verification controller just to rely on revoke status.
     */
    @Operation(summary = "Client API - Query unique accreditation instance by query parameters")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved unique accreditation instance",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationPrivateOutputDto.class))})
    })
    @GetMapping(path = "/private", produces = {MediaType.APPLICATION_JSON_VALUE})
    /* Protected with !ACAPY webhook API key */
    public ResponseEntity<GuestAccreditationPrivateOutputDto> getUniqueAccreditationByPartyParams(
        /* TODO - Minimum set of query parameters - remove redundant if possible */
        @RequestParam String referenceBasisId,
        @RequestParam String firstName, @RequestParam String lastName,
        @RequestParam String dateOfBirth, @RequestParam String companyName,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date validFromDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date validUntilDate,
        @RequestParam String invitedBy)
        throws Exception {
        logger.info("start: query unique accreditation by party query parameters");

        GuestAccreditation guestAccreditation =
            guestAccreditationService
                .getUniqueAccreditationByPartyParams(URLDecoder.decode(referenceBasisId, "UTF-8"),
                    URLDecoder.decode(firstName, "UTF-8"), URLDecoder.decode(lastName, "UTF-8"),
                    dateOfBirth, URLDecoder.decode(companyName, "UTF-8"), validFromDate,
                    validUntilDate, URLDecoder.decode(invitedBy, "UTF-8"))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        GuestAccreditationPrivateOutputDto guestAccreditationPrivateOutputDto =
            guestAccreditationOutputDtoMapper
                .toGuestAccreditationPrivateOutputDto(guestAccreditation);

        logger.info("end: query unique accreditation by party query parameters");
        return ResponseEntity.status(200).body(guestAccreditationPrivateOutputDto);
    }

    /* Warning! Technical debt.
     * In target architecture this endpoint will not be needed - it is a workaround.
     * It is required for Verification Controller to cleanup personal information about guest
     * after checkout occurs
     */
    @Operation(summary = "Client API - Cleanup party personal information by accreditationId")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved clean accreditation instance",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationPrivateOutputDto.class))})
    })
    @PatchMapping(path = "/append/clean-guest-information/{accreditationId}", produces =
        {MediaType.APPLICATION_JSON_VALUE})
    /* Protected with !ACAPY webhook API key */
    public ResponseEntity<GuestAccreditationPrivateOutputDto> cleanupGuestInformationOnCheckout(
        @PathVariable String accreditationId) throws Exception {
        logger.info("start: cleanup party personal information by accreditationId");

        GuestAccreditation guestAccreditation =
            guestAccreditationService.cleanGuestInformationOnCheckout(accreditationId);

        GuestAccreditationPrivateOutputDto guestAccreditationPrivateOutputDto =
            guestAccreditationOutputDtoMapper
                .toGuestAccreditationPrivateOutputDto(guestAccreditation);

        logger.info("end: cleanup party personal information by accreditationId");
        return ResponseEntity.status(200).body(guestAccreditationPrivateOutputDto);
    }
}
