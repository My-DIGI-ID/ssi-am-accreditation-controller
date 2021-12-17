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

package com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.statistics;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.GuestAccreditationOpenOutputDto;

import java.util.List;
import java.util.Map;

/**
 * The type Guest accreditation statistic output dto.
 */
public class GuestAccreditationStatisticOutputDto {

    private Map<String, List<GuestAccreditationOpenOutputDto>> accreditationsGroupedByStatus;

    private Map<String, Long> countOfAccreditationsGroupedByStatus;

    /**
     * Instantiates a new Guest accreditation statistic output dto.
     */
    public GuestAccreditationStatisticOutputDto() {
    }

    /**
     * Gets accreditations grouped by status.
     *
     * @return the accreditations grouped by status
     */
    public Map<String, List<GuestAccreditationOpenOutputDto>> getAccreditationsGroupedByStatus() {
        return accreditationsGroupedByStatus;
    }

    /**
     * Sets accreditations grouped by status.
     *
     * @param accreditationsGroupedByStatus the accreditations grouped by status
     */
    public void setAccreditationsGroupedByStatus(
        Map<String, List<GuestAccreditationOpenOutputDto>> accreditationsGroupedByStatus) {
        this.accreditationsGroupedByStatus = accreditationsGroupedByStatus;
    }

    /**
     * Gets count of accreditations grouped by status.
     *
     * @return the count of accreditations grouped by status
     */
    public Map<String, Long> getCountOfAccreditationsGroupedByStatus() {
        return countOfAccreditationsGroupedByStatus;
    }

    /**
     * Sets count of accreditations grouped by status.
     *
     * @param countOfAccreditationsGroupedByStatus the count of accreditations grouped by status
     */
    public void setCountOfAccreditationsGroupedByStatus(
        Map<String, Long> countOfAccreditationsGroupedByStatus) {
        this.countOfAccreditationsGroupedByStatus = countOfAccreditationsGroupedByStatus;
    }
}
