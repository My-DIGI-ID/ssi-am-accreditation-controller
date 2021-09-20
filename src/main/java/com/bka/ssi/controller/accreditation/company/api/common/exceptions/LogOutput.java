package com.bka.ssi.controller.accreditation.company.api.common.exceptions;

import com.bka.ssi.controller.accreditation.company.api.common.exceptions.response.RestErrorResponse;

public class LogOutput {

    private RestErrorResponse response;

    public LogOutput(RestErrorResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "status: " + this.response.getStatus() + "; message: " + this.response.getMessage()
            + "; path: " + this.response.getPath() + ";";
    }

}
