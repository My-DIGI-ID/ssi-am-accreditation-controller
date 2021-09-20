package com.bka.ssi.controller.accreditation.company.aop.configuration.agents;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CredentialsConfiguration {

    @Value("${bdr.credentials.basis_id.credential_definition_id}")
    private String basisIdCredentialDefinitionId;

    @Value("${bdr.credentials.basis_id.employee.schema_id}")
    private String basisIdCredentialSchemaId;

    @Value("${accreditation.credentials.employee.credential_definition_id}")
    private String employeeCredentialDefinitionId;

    @Value("${accreditation.credentials.employee.schema_id}")
    private String employeeCredentialSchemaId;

    @Value("${accreditation.credentials.guest.credential_definition_id}")
    private String guestCredentialDefinitionId;

    @Value("${accreditation.credentials.guest.schema_id}")
    private String guestCredentialSchemaId;

    public String getBasisIdCredentialDefinitionId() {
        return basisIdCredentialDefinitionId;
    }

    public String getBasisIdCredentialSchemaId() {
        return basisIdCredentialSchemaId;
    }

    public String getEmployeeCredentialDefinitionId() {
        return employeeCredentialDefinitionId;
    }

    public String getEmployeeCredentialSchemaId() {
        return employeeCredentialSchemaId;
    }

    public String getGuestCredentialDefinitionId() {
        return guestCredentialDefinitionId;
    }

    public String getGuestCredentialSchemaId() {
        return guestCredentialSchemaId;
    }
}
