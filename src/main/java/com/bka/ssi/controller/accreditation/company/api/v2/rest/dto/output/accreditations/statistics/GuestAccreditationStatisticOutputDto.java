package com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.statistics;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationOpenOutputDto;

import java.util.List;
import java.util.Map;

public class GuestAccreditationStatisticOutputDto {

    private Map<String, List<GuestAccreditationOpenOutputDto>> accreditationsGroupedByStatus;

    private Map<String, Long> countOfAccreditationsGroupedByStatus;

    public GuestAccreditationStatisticOutputDto() {
    }

    public Map<String, List<GuestAccreditationOpenOutputDto>> getAccreditationsGroupedByStatus() {
        return accreditationsGroupedByStatus;
    }

    public void setAccreditationsGroupedByStatus(
        Map<String, List<GuestAccreditationOpenOutputDto>> accreditationsGroupedByStatus) {
        this.accreditationsGroupedByStatus = accreditationsGroupedByStatus;
    }

    public Map<String, Long> getCountOfAccreditationsGroupedByStatus() {
        return countOfAccreditationsGroupedByStatus;
    }

    public void setCountOfAccreditationsGroupedByStatus(
        Map<String, Long> countOfAccreditationsGroupedByStatus) {
        this.countOfAccreditationsGroupedByStatus = countOfAccreditationsGroupedByStatus;
    }
}
