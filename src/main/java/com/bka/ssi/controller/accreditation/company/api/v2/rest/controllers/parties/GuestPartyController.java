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

package com.bka.ssi.controller.accreditation.company.api.v2.rest.controllers.parties;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.GuestOpenOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.parties.GuestOutputDtoMapper;
import com.bka.ssi.controller.accreditation.company.application.security.facade.SSOProtectedTransaction;
import com.bka.ssi.controller.accreditation.company.application.security.utilities.BearerTokenParser;
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

/**
 * The type Guest party controller.
 */
@Tag(name = "Guest Party Controller", description = "Handle creation and retrieval of guest as a party")
@RestController()
@SecurityRequirement(name = "oauth2_accreditation_party_api")
@RequestMapping(value = {"/api/v2/party/guest", "/api/party/guest"})
public class GuestPartyController {

    private final GuestPartyService guestPartyService;
    private final GuestOutputDtoMapper mapper;
    private final BearerTokenParser bearerTokenParser;
    private final Logger logger;

    /**
     * Instantiates a new Guest party controller.
     *
     * @param guestPartyService    the guest party service
     * @param bearerTokenParser    the bearer token parser
     * @param guestOutputDtoMapper the guest output dto mapper
     * @param logger               the logger
     */
    public GuestPartyController(
        GuestPartyService guestPartyService,
        BearerTokenParser bearerTokenParser, GuestOutputDtoMapper guestOutputDtoMapper,
        Logger logger) {
        this.guestPartyService = guestPartyService;
        this.mapper = guestOutputDtoMapper;
        this.bearerTokenParser = bearerTokenParser;
        this.logger = logger;
    }

    /**
     * Create guest response entity.
     *
     * @param inputDto the input dto
     * @return the response entity
     * @throws Exception the exception
     */
    @Operation(summary = "Create guest as a party")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created new guest as a party",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestOpenOutputDto.class))})
    })
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @SSOProtectedTransaction(scope = "scope:create", resource = "res:guest")
    public ResponseEntity<GuestOpenOutputDto> createGuest(@Valid @RequestBody
        GuestInputDto inputDto) throws Exception {
        logger.info("start: storing new guest as a party");

        String userName = bearerTokenParser.parseUserFromJWTToken();

        Guest guest = this.guestPartyService.createParty(inputDto, userName);
        GuestOpenOutputDto outputDto = mapper.entityToOpenDto(guest);

        logger.info("end: storing new guest as a party");
        return ResponseEntity.status(201).body(outputDto);
    }

    /**
     * Create guests from csv response entity.
     *
     * @param file the file
     * @return the response entity
     * @throws Exception the exception
     */
    /* TODO - BKAACMGT-165 - Currently content type of response is \*\/\* */
    @Operation(summary = "Create guests as parties by CSV")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created new guests as parties by CSV")
    })
    @PostMapping(path = "/csv", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @SSOProtectedTransaction(scope = "scope:create", resource = "res:guest")
    public ResponseEntity<List<GuestOpenOutputDto>> createGuestsFromCsv(
        @RequestBody MultipartFile file)
        throws Exception {
        logger.info("start: storing new guests as a parties from csv");

        String userName = bearerTokenParser.parseUserFromJWTToken();

        List<GuestOpenOutputDto> outputDtos = new ArrayList<>();
        this.guestPartyService.createParty(file, userName).forEach(guest -> {
            GuestOpenOutputDto outputDto = mapper.entityToOpenDto(guest);
            outputDtos.add(outputDto);
        });

        logger.info("end: storing new guests as a parties from csv");
        return ResponseEntity.status(201).body(outputDtos);
    }

    /**
     * Gets all guests.
     *
     * @return the all guests
     * @throws Exception the exception
     */
    /* TODO - BKAACMGT-165 - Currently content type of response is \*\/\* */
    @Operation(summary = "Get all guests as parties")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved all guests as parties")
    })
    @GetMapping
    @SSOProtectedTransaction(scope = "scope:view", resource = "res:guest")
    public ResponseEntity<List<GuestOpenOutputDto>> getAllGuests()
        throws Exception {
        logger.info("start: getting all guests as parties");

        String userName = bearerTokenParser.parseUserFromJWTToken();

        List<GuestOpenOutputDto> outputDtos = new ArrayList<>();
        this.guestPartyService.getAllParties(userName).forEach(guest -> {
            GuestOpenOutputDto outputDto = mapper.entityToOpenDto(guest);
            outputDtos.add(outputDto);
        });

        logger.info("end: getting all guests as parties");
        return ResponseEntity.ok(outputDtos);
    }

    /**
     * Gets guest by id.
     *
     * @param guestId the guest id
     * @return the guest by id
     * @throws Exception the exception
     */
    @Operation(summary = "Get guest as a party by Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved guest as a party",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestOpenOutputDto.class))})
    })
    @GetMapping(path = "/{guestId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @SSOProtectedTransaction(scope = "scope:view", resource = "res:guest")
    public ResponseEntity<GuestOpenOutputDto> getGuestById(@PathVariable String guestId)
        throws Exception {
        logger.info("start: getting guest as a party by ID");

        String userName = bearerTokenParser.parseUserFromJWTToken();

        Guest guest = guestPartyService.getPartyById(guestId, userName);

        GuestOpenOutputDto outputDto = mapper.entityToOpenDto(guest);

        logger.info("end: getting guest as a party by ID");
        return ResponseEntity.ok(outputDto);
    }

    /**
     * Update guest response entity.
     *
     * @param inputDto the input dto
     * @param guestId  the guest id
     * @return the response entity
     * @throws Exception the exception
     */
    @Operation(summary = "Update guest as a party")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated guest as a party",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestOpenOutputDto.class))})
    })
    @PutMapping(path = "/{guestId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @SSOProtectedTransaction(scope = "scope:update", resource = "res:guest")
    public ResponseEntity<GuestOpenOutputDto> updateGuest(@Valid @RequestBody
        GuestInputDto inputDto, @PathVariable String guestId)
        throws Exception {
        logger.info("start: updating existing guest as a party");

        String userName = bearerTokenParser.parseUserFromJWTToken();

        Guest guest = this.guestPartyService.updateParty(inputDto, guestId, userName);
        GuestOpenOutputDto updatedGuestOutputDTO =
            mapper.entityToOpenDto(guest);

        logger.info("end: updating existing guest as a party");
        return ResponseEntity.status(200).body(updatedGuestOutputDTO);
    }

    /**
     * Delete guest response entity.
     *
     * @param guestId the guest id
     * @return the response entity
     * @throws Exception the exception
     */
    @Operation(summary = "Delete guest party")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Delete guest party", content = @Content)
    })
    @DeleteMapping(path = "/{guestId}")
    @SSOProtectedTransaction(scope = "scope:delete", resource = "res:guest")
    public ResponseEntity<Void> deleteGuest(@PathVariable String guestId) throws Exception {
        logger.info("start: deleting guest party");

        this.guestPartyService.deleteParty(guestId);

        logger.info("end: deleting guest party");
        return ResponseEntity.status(200).build();
    }
}
