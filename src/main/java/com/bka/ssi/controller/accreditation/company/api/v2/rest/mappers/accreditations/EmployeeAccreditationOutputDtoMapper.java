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

package com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.EmployeeAccreditationOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.EmployeeOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.parties.EmployeeOutputDtoMapper;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * The type Employee accreditation output dto mapper.
 */
@Service
public class EmployeeAccreditationOutputDtoMapper {

    private final EmployeeOutputDtoMapper employeeOutputDtoMapper;
    private final Logger logger;

    /**
     * Instantiates a new Employee accreditation output dto mapper.
     *
     * @param employeeOutputDtoMapper the employee output dto mapper
     * @param logger                  the logger
     */
    public EmployeeAccreditationOutputDtoMapper(
        EmployeeOutputDtoMapper employeeOutputDtoMapper, Logger logger) {
        this.employeeOutputDtoMapper = employeeOutputDtoMapper;
        this.logger = logger;
    }

    /**
     * Entity to dto employee accreditation output dto.
     *
     * @param employeeAccreditation the employee accreditation
     * @return the employee accreditation output dto
     */
    public EmployeeAccreditationOutputDto entityToDto(
        EmployeeAccreditation employeeAccreditation) {
        logger.debug("Mapping EmployeeAccreditation to EmployeeAccreditationOutputDto");

        if (employeeAccreditation == null) {
            // throw instead
            return null;
        } else {
            EmployeeAccreditationOutputDto dto = new EmployeeAccreditationOutputDto();

            EmployeeOutputDto employeeOutputDto =
                employeeOutputDtoMapper.entityToDto(employeeAccreditation.getParty());

            dto.setId(employeeAccreditation.getId());
            dto.setStatus(employeeAccreditation.getStatus().getName());
            dto.setEmployee(employeeOutputDto);
            dto.setInvitedBy(employeeAccreditation.getInvitedBy());
            dto.setInvitedAt(employeeAccreditation.getInvitedAt());
            dto.setInvitationUrl(employeeAccreditation.getInvitationUrl());
            dto.setInvitationEmail(employeeAccreditation.getInvitationEmail());
            dto.setInvitationQrCode(employeeAccreditation.getInvitationQrCode());

            return dto;
        }
    }
}
