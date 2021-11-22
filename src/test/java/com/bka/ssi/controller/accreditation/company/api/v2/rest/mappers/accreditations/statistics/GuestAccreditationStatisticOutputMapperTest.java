package com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.statistics.GuestAccreditationStatisticOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations.GuestAccreditationOutputDtoMapper;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.parties.GuestOutputDtoMapper;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.GuestAccreditation;
import com.bka.ssi.controller.accreditation.company.domain.enums.GuestAccreditationStatus;
import com.bka.ssi.controller.accreditation.company.domain.exceptions.InvalidValidityTimeframeException;
import com.bka.ssi.controller.accreditation.company.testutilities.accreditation.guest.GuestAccreditationBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuestAccreditationStatisticOutputMapperTest {

    private static final Logger logger =
        LoggerFactory.getLogger(GuestAccreditationStatisticOutputMapperTest.class);
    private static GuestAccreditationStatisticOutputMapper guestAccreditationStatisticOutputMapper;
    private static GuestAccreditationOutputDtoMapper guestAccreditationOutputDtoMapper;
    private static GuestOutputDtoMapper guestOutputDtoMapper;

    private static GuestAccreditationBuilder guestAccreditationBuilder;

    private static final String INVITEDBY = "unittest";

    private Map<GuestAccreditationStatus, List<GuestAccreditation>> accreditationsGroupedByStatus;
    private List<GuestAccreditation> guestAccreditationList;
    private Map<GuestAccreditationStatus, Long> countOfAccreditationsGroupedByStatus;

    @BeforeAll
    static void init() throws InvalidValidityTimeframeException {
        guestOutputDtoMapper = new GuestOutputDtoMapper(logger);
        guestAccreditationOutputDtoMapper =
            new GuestAccreditationOutputDtoMapper(guestOutputDtoMapper, logger);
        guestAccreditationStatisticOutputMapper =
            new GuestAccreditationStatisticOutputMapper(logger, guestAccreditationOutputDtoMapper);
        guestAccreditationBuilder = new GuestAccreditationBuilder();
    }

    @BeforeEach
    void setUp() throws InvalidValidityTimeframeException {
        GuestAccreditation guestAccreditation = guestAccreditationBuilder.buildGuestAccreditation();

        guestAccreditationList = new ArrayList<>();
        guestAccreditationList.add(guestAccreditation);

        accreditationsGroupedByStatus = new HashMap<>();
        accreditationsGroupedByStatus.put(GuestAccreditationStatus.OPEN, guestAccreditationList);

        countOfAccreditationsGroupedByStatus = new HashMap<>();
        countOfAccreditationsGroupedByStatus
            .put(GuestAccreditationStatus.OPEN, (long) guestAccreditationList.size());
    }

    @Test
    public void shouldMapValuesToDto() {
        // when
        GuestAccreditationStatisticOutputDto dto = guestAccreditationStatisticOutputMapper
            .valuesToDto(accreditationsGroupedByStatus, countOfAccreditationsGroupedByStatus);

        // then
        assertEquals(guestAccreditationList.size(),
            dto.getCountOfAccreditationsGroupedByStatus().values().size());
    }
}
