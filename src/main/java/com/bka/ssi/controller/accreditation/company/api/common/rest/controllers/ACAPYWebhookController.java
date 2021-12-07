package com.bka.ssi.controller.accreditation.company.api.common.rest.controllers;

import com.bka.ssi.controller.accreditation.company.aop.configuration.agents.ACAPYConfiguration;
import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.WebhookServiceFactory;
import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input.ACAPYConnectionDto;
import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input.ACAPYIssueCredentialDto;
import com.bka.ssi.controller.accreditation.company.application.agent.webhooks.dto.input.ACAPYPresentProofDto;
import com.bka.ssi.controller.accreditation.company.application.security.facade.APIKeyProtectedTransaction;
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
    /**
     * Note that this ACAPY Webhook Controller only implements endpoints for v1.0 of ACAPY.
     * It does not provide endpoints for v2.0 of ACAPY, e.g. /issue_credential_v2_0,
     * /issue_credential_v2_0_indy, /issue_credential_v2_0_dif, etc.
     */

    private final Logger logger;
    private final WebhookServiceFactory webhookServiceFactory;

    public ACAPYWebhookController(Logger logger,
        WebhookServiceFactory webhookService) {
        this.logger = logger;
        this.webhookServiceFactory = webhookService;
    }

    @Operation(summary = "Pairwise Connection Record Updated")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204",
            description = "Nothing to return to ACAPY agent",
            content = @Content)
    })
    @PostMapping("/connections")
    @APIKeyProtectedTransaction(id = ACAPYConfiguration.API_KEY_ID)
    public ResponseEntity<Void> onConnection(
        @RequestBody ACAPYConnectionDto inputDto) throws Exception {
        logger.info("start: onConnection");

        this.webhookServiceFactory.handleOnConnection(inputDto);

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
    @APIKeyProtectedTransaction(id = ACAPYConfiguration.API_KEY_ID)
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
    @APIKeyProtectedTransaction(id = ACAPYConfiguration.API_KEY_ID)
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
    @APIKeyProtectedTransaction(id = ACAPYConfiguration.API_KEY_ID)
    public ResponseEntity<Void> onIssueCredential(
        @RequestBody ACAPYIssueCredentialDto inputDto) throws Exception {
        logger.info("start: onIssueCredential, state: " + inputDto.getState());

        this.webhookServiceFactory.handleOnIssueCredential(inputDto);

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
    @APIKeyProtectedTransaction(id = ACAPYConfiguration.API_KEY_ID)
    public ResponseEntity<Void> onPresentProof(
        @RequestBody ACAPYPresentProofDto inputDto) throws Exception {
        logger.info("start: onPresentProof");

        this.webhookServiceFactory.handleOnPresentProof(inputDto);

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
    @APIKeyProtectedTransaction(id = ACAPYConfiguration.API_KEY_ID)
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
    @APIKeyProtectedTransaction(id = ACAPYConfiguration.API_KEY_ID)
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
    @APIKeyProtectedTransaction(id = ACAPYConfiguration.API_KEY_ID)
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
    @APIKeyProtectedTransaction(id = ACAPYConfiguration.API_KEY_ID)
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
    @APIKeyProtectedTransaction(id = ACAPYConfiguration.API_KEY_ID)
    public ResponseEntity<Void> onProblemReport(
        @RequestBody Object object) throws Exception {
        /* Out of Scope for MVP and beyond */
        throw new UnsupportedOperationException("No support planned");
    }
}
