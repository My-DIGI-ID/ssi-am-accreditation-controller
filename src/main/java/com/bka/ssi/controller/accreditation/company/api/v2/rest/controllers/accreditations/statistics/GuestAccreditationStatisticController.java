package com.bka.ssi.controller.accreditation.company.api.v2.rest.controllers.accreditations.statistics;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.statistics.GuestAccreditationStatisticOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations.statistics.GuestAccreditationStatisticOutputMapper;
import com.bka.ssi.controller.accreditation.company.application.security.facade.SSOProtectedTransaction;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(name = "Guest accreditation statistic controller",
    description = "Managing guest accreditation statistics")
@RestController()
@SecurityRequirement(name = "oauth2_accreditation_party_api")
@RequestMapping(value = {"/api/v2/accreditation/guest/statistics",
    "/api/accreditation/guest/statistics"})
public class GuestAccreditationStatisticController {

    private final Logger logger;
    private final GuestAccreditationService service;
    private final GuestAccreditationStatisticOutputMapper mapper;

    public GuestAccreditationStatisticController(Logger logger,
        GuestAccreditationService service,
        GuestAccreditationStatisticOutputMapper mapper) {
        this.logger = logger;
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Get guest accreditation statistic")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved guest accreditation statistic",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = GuestAccreditationStatisticOutputDto.class))})
    })
    @GetMapping
    @SSOProtectedTransaction(scope = "scope:view", resource = "res:guest-accreditation-statistic")
    public ResponseEntity<GuestAccreditationStatisticOutputDto> getGuestAccreditationStatistic()
        throws Exception {
        logger.info("start: getting all guest accreditations by status");

        Map<GuestAccreditationStatus, List<GuestAccreditation>> accreditationsGroupedByStatus =
            this.service.getAllAccreditationsGroupedByStatus();

        Map<GuestAccreditationStatus, Long> countOfAccreditationsGroupedByStatus =
            this.service.countOfAccreditationsGroupedByStatus();

        GuestAccreditationStatisticOutputDto dto =
            this.mapper
                .valuesToDto(accreditationsGroupedByStatus, countOfAccreditationsGroupedByStatus);

        logger.info("end: getting all accreditations by status");
        return ResponseEntity.ok(dto);
    }
}
