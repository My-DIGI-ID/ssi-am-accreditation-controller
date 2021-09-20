package com.bka.ssi.controller.accreditation.company.domain.values;

public class Correlation {

    /* ToDo - differentiate between different Correlation and have this as abstract class */

    private String connectionId;
    private String threadId;
    private String presentationExchangeId;

    public Correlation(String connectionId, String threadId, String presentationExchangeId) {
        this.connectionId = connectionId;
        this.threadId = threadId;
        this.presentationExchangeId = presentationExchangeId;
    }

    public Correlation(String connectionId) {
        this.connectionId = connectionId;
    }

    public Correlation() {
    }

    public String getConnectionId() {
        return connectionId;
    }

    public String getThreadId() {
        return threadId;
    }

    public String getPresentationExchangeId() {
        return presentationExchangeId;
    }
}
