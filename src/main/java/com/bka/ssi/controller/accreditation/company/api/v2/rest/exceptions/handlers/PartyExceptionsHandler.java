package com.bka.ssi.controller.accreditation.company.api.v2.rest.exceptions.handlers;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Priority;

@Priority(1)
@RestControllerAdvice(basePackages = {
    "com.bka.ssi.controller.accreditation.company.api.v2.rest.controllers.parties"})
public class PartyExceptionsHandler {
}
