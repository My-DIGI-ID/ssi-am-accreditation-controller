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

package com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations.statistics;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationOpenOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.statistics.GuestAccreditationStatisticOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations.GuestAccreditationOutputDtoMapper;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.enums.GuestAccreditationStatus;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Guest accreditation statistic output mapper.
 */
@Service
public class GuestAccreditationStatisticOutputMapper {

    private final Logger logger;
    private final GuestAccreditationOutputDtoMapper mapper;

    /**
     * Instantiates a new Guest accreditation statistic output mapper.
     *
     * @param logger the logger
     * @param mapper the mapper
     */
    public GuestAccreditationStatisticOutputMapper(Logger logger,
        GuestAccreditationOutputDtoMapper mapper) {
        this.logger = logger;
        this.mapper = mapper;
    }

    /**
     * Values to dto guest accreditation statistic output dto.
     *
     * @param accreditationsGroupedByStatus        the accreditations grouped by status
     * @param countOfAccreditationsGroupedByStatus the count of accreditations grouped by status
     * @return the guest accreditation statistic output dto
     */
    public GuestAccreditationStatisticOutputDto valuesToDto(
        Map<GuestAccreditationStatus, List<GuestAccreditation>> accreditationsGroupedByStatus,
        Map<GuestAccreditationStatus, Long> countOfAccreditationsGroupedByStatus) {
        logger.debug("mapping statistic values to GuestAccreditationStatisticOutputDto");

        GuestAccreditationStatisticOutputDto dto = new GuestAccreditationStatisticOutputDto();

        Map<String, List<GuestAccreditationOpenOutputDto>> dtoAccreditationsGroupedByStatus =
            new HashMap<>();
        for (Map.Entry<GuestAccreditationStatus, List<GuestAccreditation>> entry : accreditationsGroupedByStatus
            .entrySet()) {

            List<GuestAccreditationOpenOutputDto> guestAccreditationOpenOutputDtos =
                new ArrayList<>();
            for (GuestAccreditation guestAccreditation : entry.getValue()) {
                guestAccreditationOpenOutputDtos
                    .add(this.mapper.entityToOpenDto(guestAccreditation));
            }

            dtoAccreditationsGroupedByStatus.put(entry.getKey().getName(),
                guestAccreditationOpenOutputDtos);
        }

        Map<String, Long> dtoCountOfAccreditationsGroupedByStatus = new HashMap<>();
        for (Map.Entry<GuestAccreditationStatus, Long> entry : countOfAccreditationsGroupedByStatus
            .entrySet()) {

            dtoCountOfAccreditationsGroupedByStatus.put(entry.getKey().getName(), entry.getValue());
        }

        dto.setAccreditationsGroupedByStatus(dtoAccreditationsGroupedByStatus);
        dto.setCountOfAccreditationsGroupedByStatus(dtoCountOfAccreditationsGroupedByStatus);

        return dto;
    }
}
