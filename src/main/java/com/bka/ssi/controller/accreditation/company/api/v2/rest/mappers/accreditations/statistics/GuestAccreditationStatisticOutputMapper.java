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

@Service
public class GuestAccreditationStatisticOutputMapper {

    private final Logger logger;
    private final GuestAccreditationOutputDtoMapper mapper;

    public GuestAccreditationStatisticOutputMapper(Logger logger,
        GuestAccreditationOutputDtoMapper mapper) {
        this.logger = logger;
        this.mapper = mapper;
    }

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
