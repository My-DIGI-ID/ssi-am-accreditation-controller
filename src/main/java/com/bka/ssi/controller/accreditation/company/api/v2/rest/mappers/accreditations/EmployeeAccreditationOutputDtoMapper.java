package com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.accreditations;

import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.accreditations.EmployeeAccreditationOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.dto.output.parties.EmployeeOutputDto;
import com.bka.ssi.controller.accreditation.company.api.v2.rest.mappers.parties.EmployeeOutputDtoMapper;
import com.bka.ssi.controller.accreditation.company.domain.entities.accreditations.EmployeeAccreditation;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class EmployeeAccreditationOutputDtoMapper {

    private final EmployeeOutputDtoMapper employeeOutputDtoMapper;
    private final Logger logger;

    public EmployeeAccreditationOutputDtoMapper(
        EmployeeOutputDtoMapper employeeOutputDtoMapper, Logger logger) {
        this.employeeOutputDtoMapper = employeeOutputDtoMapper;
        this.logger = logger;
    }

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
