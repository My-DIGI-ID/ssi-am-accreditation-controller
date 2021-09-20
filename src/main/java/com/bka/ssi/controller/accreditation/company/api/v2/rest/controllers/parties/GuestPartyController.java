package com.bka.ssi.controller.accreditation.company.api.v2.rest.controllers.parties;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.GuestOpenOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.parties.GuestOutputDtoMapper;
import com.bka.ssi.controller.accreditation.company.application.security.facade.SSOProtectedTransaction;
import com.bka.ssi.controller.accreditation.company.application.services.dto.input.parties.GuestInputDto;
import com.bka.ssi.controller.accreditation.company.application.services.strategies.parties.GuestPartyService;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Guest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

@Tag(name = "Guest Party Controller", description = "Handle creation and retrieval of guest as a party")
@RestController()
@SecurityRequirement(name = "oauth2_accreditation_party_api")
@RequestMapping(value = {"/api/v2/party/guest", "/api/party/guest"})
public class GuestPartyController {

    private final GuestPartyService guestPartyService;
    private final GuestOutputDtoMapper guestOutputDtoMapper;
    private final Logger logger;

    public GuestPartyController(
        GuestPartyService guestPartyService,
        GuestOutputDtoMapper guestOutputDtoMapper, Logger logger) {
        this.guestPartyService = guestPartyService;
        this.guestOutputDtoMapper = guestOutputDtoMapper;
        this.logger = logger;
    }

    @Operation(summary = "Create guest as a party")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created new guest as a party",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestOpenOutputDto.class))})
    })
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @SSOProtectedTransaction(scope = "scope:create", resource = "guest")
    public ResponseEntity<GuestOpenOutputDto> createGuest(@Valid @RequestBody
        GuestInputDto inputDTO) throws Exception {
        logger.info("start: storing new guest as a party");

        Guest guest = this.guestPartyService.createParty(inputDTO);
        GuestOpenOutputDto registeredGuestOutputDTO =
            guestOutputDtoMapper.guestToGuestOpenOutputDto(guest);

        logger.info("end: storing new guest as a party");
        return ResponseEntity.status(201).body(registeredGuestOutputDTO);
    }

    /* TODO - BKAACMGT-165 - Currently content type of response is \*\/\* */
    @Operation(summary = "Create guests as parties by CSV")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created new guests as parties by CSV")
    })
    @PostMapping(path = "/csv", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @SSOProtectedTransaction(scope = "scope:create", resource = "guest")
    public ResponseEntity<List<GuestOpenOutputDto>> createGuestsFromCsv(
        @RequestBody MultipartFile file)
        throws Exception {
        logger.info("start: storing new guests as a parties from csv");

        List<GuestOpenOutputDto> guestOpenOutputDto = new ArrayList<>();
        this.guestPartyService.createParty(file).forEach(guest -> {
            GuestOpenOutputDto output =
                guestOutputDtoMapper.guestToGuestOpenOutputDto(guest);
            guestOpenOutputDto.add(output);
        });
        ;

        logger.info("end: storing new guests as a parties from csv");
        return ResponseEntity.status(201).body(guestOpenOutputDto);
    }

    /* TODO - BKAACMGT-165 - Currently content type of response is \*\/\* */
    @Operation(summary = "Get all guests as parties")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved all guests as parties")
    })
    @GetMapping
    @SSOProtectedTransaction(scope = "scope:view", resource = "guest")
    public ResponseEntity<List<GuestOpenOutputDto>> getAllGuests()
        throws Exception {
        logger.info("start: getting all guests as parties");

        List<GuestOpenOutputDto> guestOpenOutputDto = new ArrayList<>();
        this.guestPartyService.getAllParties().forEach(guest -> {
            GuestOpenOutputDto output =
                guestOutputDtoMapper.guestToGuestOpenOutputDto(guest);
            guestOpenOutputDto.add(output);
        });

        logger.info("end: getting all guests as parties");
        return ResponseEntity.ok(guestOpenOutputDto);
    }

    @Operation(summary = "Get guest as a party by Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved guest as a party",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestOpenOutputDto.class))})
    })
    @GetMapping(path = "/{guestId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @SSOProtectedTransaction(scope = "scope:view", resource = "guest")
    public ResponseEntity<GuestOpenOutputDto> getGuestById(@PathVariable String guestId)
        throws Exception {
        logger.info("start: getting guest as a party by ID");

        Guest guest = guestPartyService.getPartyById(guestId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        GuestOpenOutputDto guestOpenOutputDto =
            guestOutputDtoMapper.guestToGuestOpenOutputDto(guest);

        logger.info("end: getting guest as a party by ID");
        return ResponseEntity.ok(guestOpenOutputDto);
    }

    @Operation(summary = "Update guest as a party")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated guest as a party",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestOpenOutputDto.class))})
    })
    @PutMapping(path = "/{guestId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @SSOProtectedTransaction(scope = "scope:update", resource = "guest")
    public ResponseEntity<GuestOpenOutputDto> updateGuest(@Valid @RequestBody
        GuestInputDto inputDTO, @PathVariable String guestId)
        throws Exception {

        throw new UnsupportedOperationException();
        /* WARNING! API implementation is disabled for MVP

        logger.info("start: updating existing guest as a party");

        Guest guest = this.guestPartyService.updateParty(inputDTO, guestId);
        GuestOpenOutputDto updatedGuestOutputDTO =
            guestOutputDtoMapper.guestToGuestOpenOutputDto(guest);

        logger.info("end: updating existing guest as a party");
        return ResponseEntity.status(200).body(updatedGuestOutputDTO);
        */
    }
}
