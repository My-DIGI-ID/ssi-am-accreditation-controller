package com.bka.ssi.controller.accreditation.company.domain.values;

import java.util.List;

public class ContactInformation {

    private List<String> emails;
    private List<String> phoneNumbers;

    public ContactInformation(List<String> emails, List<String> phoneNumbers) {
        this.emails = emails;
        this.phoneNumbers = phoneNumbers;
    }

    public ContactInformation(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getEmails() {
        return emails;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }
}
