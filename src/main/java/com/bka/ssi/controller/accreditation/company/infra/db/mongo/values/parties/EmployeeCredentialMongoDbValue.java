package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.parties;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.ContactInformationMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.CredentialMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.EmployerMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.IdentityManagementMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.PersonaMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.PositionMongoDbValue;
import org.springframework.data.mongodb.core.mapping.Field;

public class EmployeeCredentialMongoDbValue extends CredentialMongoDbValue {

    @Field("employeeId")
    private String employeeId;

    @Field("employeeState")
    private String employeeState;

    @Field("persona")
    private PersonaMongoDbValue persona;

    @Field("contactInformation")
    private ContactInformationMongoDbValue contactInformation;

    @Field("identityManagement")
    private IdentityManagementMongoDbValue identityManagement;

    @Field("employer")
    private EmployerMongoDbValue employer;

    @Field("position")
    private PositionMongoDbValue position;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeState() {
        return employeeState;
    }

    public void setEmployeeState(String employeeState) {
        this.employeeState = employeeState;
    }

    public PersonaMongoDbValue getPersona() {
        return persona;
    }

    public void setPersona(PersonaMongoDbValue persona) {
        this.persona = persona;
    }

    public ContactInformationMongoDbValue getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(
        ContactInformationMongoDbValue contactInformation) {
        this.contactInformation = contactInformation;
    }

    public IdentityManagementMongoDbValue getIdentityManagement() {
        return identityManagement;
    }

    public void setIdentityManagement(
        IdentityManagementMongoDbValue identityManagement) {
        this.identityManagement = identityManagement;
    }

    public EmployerMongoDbValue getEmployer() {
        return employer;
    }

    public void setEmployer(
        EmployerMongoDbValue employer) {
        this.employer = employer;
    }

    public PositionMongoDbValue getPosition() {
        return position;
    }

    public void setPosition(
        PositionMongoDbValue position) {
        this.position = position;
    }
}
