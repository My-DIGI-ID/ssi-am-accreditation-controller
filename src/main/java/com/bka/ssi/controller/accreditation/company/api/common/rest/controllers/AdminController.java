/*
 * Copyright 2021 Bundesrepublik Deutschland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bka.ssi.controller.accreditation.company.api.common.rest.controllers;

import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.common.NoForbiddenCharacters;
import com.bka.ssi.controller.accreditation.company.application.services.dto.validation.common.NoWhitespaces;
import com.bka.ssi.controller.accreditation.company.application.utilities.I18nJsonParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Admin controller.
 */
@Tag(name = "Admin Controller", description = "(For now) Handling internalization for UI")
@RestController
@RequestMapping(value = "/admin")
@Validated
public class AdminController {

    private final Logger logger;
    private final I18nJsonParser i18nJsonParser;

    /**
     * Instantiates a new Admin controller.
     *
     * @param logger         the logger
     * @param i18nJsonParser the 18 n json parser
     */
    public AdminController(Logger logger, I18nJsonParser i18nJsonParser) {
        this.logger = logger;
        this.i18nJsonParser = i18nJsonParser;
    }

    /**
     * Gets i 18 n json file.
     *
     * @param languageTag  the language tag
     * @param languageCode the language code
     * @return the i 18 n json file
     * @throws Exception the exception
     */
    @Operation(summary = "Get i18n JSON file by language code. Default is 'de'.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved i18n JSON file",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = Object.class))})
    })
    @GetMapping(path = "/i18n", produces = {MediaType.APPLICATION_JSON_VALUE})
    /* ! Public API */
    public ResponseEntity<Object> getI18nJsonFile(
            @RequestHeader(name = "content-language", required = false, defaultValue = "de-DE")
            @NoForbiddenCharacters @NoWhitespaces String languageTag,
            @RequestParam(required = false) @NoForbiddenCharacters @NoWhitespaces String languageCode)
        throws Exception {
        String language = languageCode != null ?
            StringUtils.substringBefore(languageCode, "-").toLowerCase() :
            StringUtils.substringBefore(languageTag, "-").toLowerCase();

        logger.info("start: getting i18n JSON file for language {}", language);

        /* Technical Debt: Escape special characters. Request/ user input is used */
        Object json = this.i18nJsonParser.parseI18nJsonFile(language);

        logger.info("end: getting i18n JSON file for language {}", language);
        return ResponseEntity.ok(json);
    }
}
