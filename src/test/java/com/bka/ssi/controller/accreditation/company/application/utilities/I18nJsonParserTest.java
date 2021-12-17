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

package com.bka.ssi.controller.accreditation.company.application.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.bka.ssi.controller.accreditation.company.application.exceptions.JsonParseException;
import com.bka.ssi.controller.accreditation.company.application.exceptions.NotFoundException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class I18nJsonParserTest {

    private final static Logger logger = LoggerFactory.getLogger(I18nJsonParserTest.class);
    private static I18nJsonParser i18nJsonParser;

    private static final String validJsonWithFilenameWithoutExtension = "language-valid";
    private static final String validJsonWithFilenameWithExtension = "language-valid.json";
    private static final String jsonNotExist = "language-nonexist";
    private static final String invalidJson = "language-invalid.json";

    @BeforeAll
    static void setUp() {
        try {
            i18nJsonParser = new I18nJsonParser(
                "src/test/resources/i18n/ui/",
                "src/test/resources/i18n/ui/",
                logger);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void i18nJsonParserPathIsNull() {
        assertThrows(NotFoundException.class, () ->
        {
            i18nJsonParser = new I18nJsonParser(
                "src/test/resources/i18n/ui/",
                null,
                logger);
        });
    }

    @Test
    void i18nJsonParserDefaultPathIsNull() {
        assertThrows(NotFoundException.class, () ->
        {
            i18nJsonParser = new I18nJsonParser(
                null,
                "src/test/resources/i18n/ui/notexist",
                logger);
        });
    }

    @Test
    void i18nJsonParserInvalidPathAndInvalidDefaultPath() {
        assertThrows(NotFoundException.class, () ->
        {
            i18nJsonParser = new I18nJsonParser(
                "src/test/resources/i18n/ui/notexist",
                "src/test/resources/i18n/ui/notexist",
                logger);
        });
    }

    @Test
    void parseI18nJsonFileForValidJsonWithFilenameWithoutExtension() {
        try {
            Object object = i18nJsonParser.parseI18nJsonFile(validJsonWithFilenameWithoutExtension);
            assertNotNull(object);

            String value = (String) ((JSONObject) object).get("key");

            assertEquals("value", value);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void parseI18nJsonFileForValidJsonWithFilenameWithExtension() {
        try {
            Object object = i18nJsonParser.parseI18nJsonFile(validJsonWithFilenameWithExtension);
            assertNotNull(object);

            String value = (String) ((JSONObject) object).get("key");

            assertEquals("value", value);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void parseI18nJsonFileShouldThrowNotFoundExceptionForJsonNotExist() {
        assertThrows(NotFoundException.class, () ->
        {
            i18nJsonParser.parseI18nJsonFile(jsonNotExist);
        });
    }

    @Test
    void parseI18nJsonFileShouldThrowNotFoundExceptionOnNull() {
        assertThrows(NotFoundException.class, () ->
        {
            i18nJsonParser.parseI18nJsonFile(null);
        });
    }

    @Test
    void parseI18nJsonFileShouldThrowJsonParseExceptionWithInvalidJson() {
        assertThrows(JsonParseException.class, () ->
        {
            i18nJsonParser.parseI18nJsonFile(invalidJson);
        });
    }
}
