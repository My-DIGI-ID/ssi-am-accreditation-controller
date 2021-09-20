package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

public class ContactInformationMongoDbValue {

    @Field("emails")
    private List<String> emails;

    @Field("phoneNumbers")
    private List<String> phoneNumbers;

    public ContactInformationMongoDbValue() {
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}

