package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common;

import org.springframework.data.mongodb.core.mapping.Field;

public class EmployerMongoDbValue {

    @Field("name")
    private String name;

    @Field("address")
    private AddressMongoDbValue address;

    @Field("subject")
    private String subject;

    @Field("proofOfOwnership")
    private String proofOfOwnership;

    public EmployerMongoDbValue() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressMongoDbValue getAddress() {
        return address;
    }

    public void setAddress(
        AddressMongoDbValue address) {
        this.address = address;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getProofOfOwnership() {
        return proofOfOwnership;
    }

    public void setProofOfOwnership(String proofOfOwnership) {
        this.proofOfOwnership = proofOfOwnership;
    }
}
