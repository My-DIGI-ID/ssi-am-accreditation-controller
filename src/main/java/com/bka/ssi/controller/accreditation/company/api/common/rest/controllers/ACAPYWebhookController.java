package com.bka.ssi.controller.accreditation.company.api.common.rest.controllers;

import com.bka.ssi.controller.accreditation.company.application.services.dto.input.common.ACAPYConnectionDto;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.common.ACAPYIssueCredentialDto;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.common.ACAPYPresentProofDto;
import com.bka.ssi.controller.accreditation.company.application.services.strategies.accreditations.GuestAccreditationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ACAPY Webhook Controller", description = "Handling requests from ACAPY agent")
@RestController()
@SecurityRequirement(name = "api_key_webhook_api")
@RequestMapping(value = "/topic")
public class ACAPYWebhookController {

    /* ToDo - API needs to be guarded by knowledge of API key */

    /**
     * Note that this ACAPY Webhook Controller only implements endpoints for v1.0 of ACAPY.
     * It does not provide endpoints for v2.0 of ACAPY, e.g. /issue_credential_v2_0,
     * /issue_credential_v2_0_indy, /issue_credential_v2_0_dif, etc.
     */

    private final Logger logger;

    /**
     * Warning! Technical debt.
     * GuestAccreditationService is directly injected into ACAPYWebhookController since guest is
     * the only party in the accreditation process at the moment. Indeed, some service layer
     * component must maintain and handle webhook requests of this controller for different parties.
     */
    private final GuestAccreditationService guestAccreditationService;

    public ACAPYWebhookController(Logger logger,
        GuestAccreditationService guestAccreditationService) {
        this.logger = logger;
        this.guestAccreditationService = guestAccreditationService;
    }

    @Operation(summary = "Pairwise Connection Record Updated")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
            description = "Nothing to return to ACAPY agent",
            content = @Content)
    })
    @PostMapping("/connections")
    public ResponseEntity<Void> onConnection(
        @RequestBody ACAPYConnectionDto acapyConnectionDto) throws Exception {
        logger.info("start: onConnection");

        switch (acapyConnectionDto.getState()) {
            case "init":
            case "invitation":
            case "request":
            case "active":
            case "inactive":
            case "error":
                logger.debug("Connection state: " + acapyConnectionDto.getState());
                break;
            case "response":
                logger.debug("Connection state: " + acapyConnectionDto.getState());
                /* Normally after connection establishment offerAccreditation will be called,
                however in guest accreditation process we need verificaiton of Basis-Id in an
                extra step (alternative would be to have offerAccredtation as abstract method to
                be implemented by each of the services */
                this.guestAccreditationService.verifyBasisId(acapyConnectionDto);
                break;
            default:
                logger.debug("Connection state: " + acapyConnectionDto.getState());
                break;
        }

        logger.info("end: onConnection");
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Basic Message Received")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
            description = "Nothing to return to ACAPY agent",
            content = @Content)
    })
    @PostMapping("/basicmessages")
    public ResponseEntity<Void> onBasicMessage(
        @RequestBody Object object) throws Exception {
        /* Out of Scope for MVP and beyond */
        throw new UnsupportedOperationException("No support planned");
    }

    @Operation(summary = "Forward Message Received")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
            description = "Nothing to return to ACAPY agent",
            content = @Content)
    })
    @PostMapping("/forward")
    public ResponseEntity<Void> onForward(
        @RequestBody Object object) throws Exception {
        /* Out of Scope for MVP and beyond */
        throw new UnsupportedOperationException("No support planned");
    }

    @Operation(summary = "Credential Exchange Record Updated")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
            description = "Nothing to return to ACAPY agent",
            content = @Content)
    })
    @PostMapping("/issue_credential")
    public ResponseEntity<Void> onIssueCredential(
        @RequestBody ACAPYIssueCredentialDto acapyIssueCredentialDto) throws Exception {
        logger.info("start: onIssueCredential, state: " + acapyIssueCredentialDto.getState());

        switch (acapyIssueCredentialDto.getState()) {
            case "proposal_sent":
            case "proposal_received":
            case "offer_sent":
            case "offer_received":
            case "request_sent":
            case "request_received":
            case "credential_received":
            case "credential_acked":
                logger
                    .debug("Ignoring IssueCredential state: " + acapyIssueCredentialDto.getState());
                break;
            case "credential_issued":
                logger.debug("IssueCredential state: " + acapyIssueCredentialDto.getState());
                this.guestAccreditationService
                    .completeAccreditationProcess(acapyIssueCredentialDto);
                break;
            default:
                logger
                    .debug("Unknown IssueCredential state: " + acapyIssueCredentialDto.getState());
                break;
        }

        logger.info("end: onIssueCredential");
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Presentation Exchange Record Updated")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
            description = "Nothing to return to ACAPY agent",
            content = @Content)
    })
    @PostMapping("/present_proof")
    public ResponseEntity<Void> onPresentProof(
        @RequestBody ACAPYPresentProofDto acapyPresentProofDto) throws Exception {
        /* In Scope of MVP if verification of Basis-id is done in accreditation controller */
        logger.info("start: onPresentProof");

        switch (acapyPresentProofDto.getState()) {
            case "proposal_sent":
            case "proposal_received":
            case "request_sent":
            case "request_received":
            case "presentation_sent":
            case "presentation_received":
                logger.debug("Proof Request state: " + acapyPresentProofDto.getState());
                break;
            case "verified":
                logger.debug("Proof Request state: " + acapyPresentProofDto.getState());
                this.guestAccreditationService
                    .completeVerificationOfBasisId(acapyPresentProofDto);
                break;
            default:
                logger.debug("Proof Request state: " + acapyPresentProofDto.getState());
                logger.debug(acapyPresentProofDto.toString());
                break;
        }

        logger.info("end: onPresentProof");
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Out-of-Band Invitation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
            description = "Nothing to return to ACAPY agent",
            content = @Content)
    })
    @PostMapping("/oob_invitation")
    public ResponseEntity<Void> onOutOfBandInvitation(
        @RequestBody Object object) throws Exception {
        /* Out of Scope for MVP and beyond */
        throw new UnsupportedOperationException("No support planned");
    }

    @Operation(summary = "Ping (debug)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
            description = "Nothing to return to ACAPY agent",
            content = @Content)
    })
    @PostMapping("/ping")
    public ResponseEntity<Void> onPing(
        @RequestBody Object object) throws Exception {
        /* Out of Scope for MVP and beyond */
        throw new UnsupportedOperationException("No support planned");
    }

    @Operation(summary = "Issuer Credential Revocation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
            description = "Nothing to return to ACAPY agent",
            content = @Content)
    })
    @PostMapping("/issuer_cred_rev")
    public ResponseEntity<Void> onIssuerCredRev(
        @RequestBody Object object) throws Exception {
        /* Out of Scope for MVP and beyond */
        throw new UnsupportedOperationException("No support planned");
    }

    @Operation(summary = "Revocation Registry Record Updated")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
            description = "Nothing to return to ACAPY agent",
            content = @Content)
    })
    @PostMapping("/revocation_registry")
    public ResponseEntity<Void> onRevocationRegistry(
        @RequestBody Object object) throws Exception {
        /* Out of Scope for MVP and beyond */
        //throw new UnsupportedOperationException("No support planned");
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Problem Report")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
            description = "Nothing to return to ACAPY agent",
            content = @Content)
    })
    @PostMapping("/problem_report")
    public ResponseEntity<Void> onProblemReport(
        @RequestBody Object object) throws Exception {
        /* Out of Scope for MVP and beyond */
        throw new UnsupportedOperationException("No support planned");
    }
}
