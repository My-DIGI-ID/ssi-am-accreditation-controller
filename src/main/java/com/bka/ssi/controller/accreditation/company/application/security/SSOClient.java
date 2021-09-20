package com.bka.ssi.controller.accreditation.company.application.security;

public interface SSOClient {

    boolean verifyPermission(String token, String transaction) throws Exception;

    boolean verifyToken(String token) throws Exception;
}
