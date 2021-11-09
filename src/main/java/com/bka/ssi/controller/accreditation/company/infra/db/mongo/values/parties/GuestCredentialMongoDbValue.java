package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.parties;

import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.abstractions.CredentialMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.ContactInformationMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.PersonaMongoDbValue;
import com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common.ValidityTimeframeDbValue;
import org.springframework.data.mongodb.core.mapping.Field;

public class GuestCredentialMongoDbValue extends CredentialMongoDbValue {

    @Field("validityTimeframe")
    private ValidityTimeframeDbValue validityTimeframe;

    @Field("persona")
    private PersonaMongoDbValue persona;

    @Field("contactInformation")
    private ContactInformationMongoDbValue contactInformation;

    @Field("companyName")
    private String companyName;

    @Field("typeOfVisit")
    private String typeOfVisit;

    @Field("location")
    private String location;

    @Field("invitedBy")
    private String invitedBy;

    @Field("referenceBasisId")
    private String referenceBasisId;

    @Field("guestPrivateInformation")
    private GuestPrivateInformationMongoDbValue guestPrivateInformation;

    public ValidityTimeframeDbValue getValidityTimeframe() {
        return validityTimeframe;
    }

    public void setValidityTimeframe(
        ValidityTimeframeDbValue validityTimeframe) {
        this.validityTimeframe = validityTimeframe;
    }

    public PersonaMongoDbValue getPersona() {
        return persona;
    }

    public void setPersona(
        PersonaMongoDbValue persona) {
        this.persona = persona;
    }

    public ContactInformationMongoDbValue getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(
        ContactInformationMongoDbValue contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTypeOfVisit() {
        return typeOfVisit;
    }

    public void setTypeOfVisit(String typeOfVisit) {
        this.typeOfVisit = typeOfVisit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(String invitedBy) {
        this.invitedBy = invitedBy;
    }

    public String getReferenceBasisId() {
        return referenceBasisId;
    }

    public void setReferenceBasisId(String referenceBasisId) {
        this.referenceBasisId = referenceBasisId;
    }

    public GuestPrivateInformationMongoDbValue getGuestPrivateInformation() {
        return guestPrivateInformation;
    }

    public void setGuestPrivateInformation(
        GuestPrivateInformationMongoDbValue guestPrivateInformation) {
        this.guestPrivateInformation = guestPrivateInformation;
    }
}
