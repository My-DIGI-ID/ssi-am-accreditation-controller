package com.bka.ssi.controller.accreditation.company.api.v1.rest.mappers;

import com.bka.ssi.controller.accreditation.company.api.v1.rest.dto.output.EmployeeOutputDTO;
import com.bka.ssi.controller.accreditation.company.domain.entities.parties.Employee;
import org.springframework.stereotype.Service;

@Service("employeeOutputDTOMapperV1")
public class EmployeeOutputDTOMapper {

    public EmployeeOutputDTO employeeToEmployeeOutputDTO(Employee employee) {
        if (employee == null) {
            return null;
        } else {
            EmployeeOutputDTO employeeOutputDTO = new EmployeeOutputDTO();

            /*
            employeeOutputDTO.setEmployeeId(employee.getId());
            employeeOutputDTO.setFirstName(employee.getFirstName());
            employeeOutputDTO.setLastName(employee.getLastName());
            employeeOutputDTO.setEmail(employee.getEmail());
            employeeOutputDTO.setFirmName(employee.getEmployer().getName());
            employeeOutputDTO.setFirmSubject(employee.getEmployer().getSubject());
            employeeOutputDTO.setFirmStreet(employee.getEmployer().getAddress().getStreet());
            employeeOutputDTO
                .setFirmPostalCode(employee.getEmployer().getAddress().getPostalCode());
            employeeOutputDTO.setFirmCity(employee.getEmployer().getAddress().getCity());
            */

            return employeeOutputDTO;
        }
    }
}
