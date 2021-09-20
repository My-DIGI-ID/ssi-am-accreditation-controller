package com.bka.ssi.controller.accreditation.company.infra.db.mongo.values.common;

import org.springframework.data.mongodb.core.mapping.Field;

public class CorrelationMongoDbDocument {

    @Field("connectionId")
    private String connectionId;

    @Field("threadId")
    private String threadId;

    @Field("presentationExchangeId")
    private String presentationExchangeId;

    public CorrelationMongoDbDocument() {
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getPresentationExchangeId() {
        return presentationExchangeId;
    }

    public void setPresentationExchangeId(String presentationExchangeId) {
        this.presentationExchangeId = presentationExchangeId;
    }
}
