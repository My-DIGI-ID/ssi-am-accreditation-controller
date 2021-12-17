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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FuzzyMatcherTest {

    private final static Logger logger = LoggerFactory.getLogger(FuzzyMatcherTest.class);
    private static FuzzyMatcher matcher;

    @BeforeAll
    static void setUp() {
        matcher = new FuzzyMatcher(100, 10, logger);
    }

    @Test
    void matchPairWithLeftNull() {
        String left = null;
        String right = "Firstname Lastname";

        assertFalse(matcher.isMatch(left, right));
    }

    @Test
    void matchPairWithRightNull() {
        String left = "Firstname Lastname";
        String right = null;

        assertFalse(matcher.isMatch(left, right));
    }

    @Test
    void matchStrictlyEqualPair() {
        String left = "Firstname Lastname";
        String right = "Firstname Lastname";

        assertTrue(left.equals(right));
        assertTrue(matcher.isMatch(left, right));
    }

    @Test
    void matchEqualPair() {
        String left = " Lastname";
        String right = "Firstname Lastname";

        assertTrue(matcher.isMatch(left, right));
    }

    @Test
    void matchNotEqualPair() {
        String left = "astname";
        String right = "Firstname Lastname";

        assertFalse(matcher.isMatch(left, right));
    }

    @Test
    void matchPairWithSpecialCharacters() {
        String left = "Firstnäme-lästname ";
        String right = "Firstnaeme Lastname";

        assertTrue(matcher.isMatch(left, right));
    }
}
