package com.bka.ssi.controller.accreditation.company.domain.values;

import java.util.Date;

public class CredentialMetadata {

    private String id;
    private String type;
    private String did;
    private Date partyCreated;
    private Date partyPersonalDataDeleted;

    public CredentialMetadata(Date partyCreated) {
        this.partyCreated = partyCreated;
    }

    public CredentialMetadata(String id, String type, String did, Date partyCreated) {
        this.id = id;
        this.type = type;
        this.did = did;
        this.partyCreated = partyCreated;
    }

    public CredentialMetadata(String id, String type, String did, Date partyCreated,
        Date partyPersonalDataDeleted) {
        this.id = id;
        this.type = type;
        this.did = did;
        this.partyCreated = partyCreated;
        this.partyPersonalDataDeleted = partyPersonalDataDeleted;
    }

    public CredentialMetadata() {
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getDid() {
        return did;
    }

    public Date getPartyCreated() {
        return partyCreated;
    }

    public Date getPartyPersonalDataDeleted() {
        return partyPersonalDataDeleted;
    }
}
