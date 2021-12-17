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

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type Fuzzy matcher.
 */
@Component
public class FuzzyMatcher {

    private final int levenshteinDistanceThreshold;
    private final int limit;

    private final LevenshteinDistance levenshteinDistance;
    private final Logger logger;

    /**
     * Instantiates a new Fuzzy matcher.
     *
     * @param levenshteinDistanceThreshold the levenshtein distance threshold
     * @param limit                        the limit
     * @param logger                       the logger
     */
    public FuzzyMatcher(
        @Value("${fuzzy.levenshtein_distance.threshold}") int levenshteinDistanceThreshold,
        @Value("${accreditation.guest.basis_id.fuzzy.limit}") int limit,
        Logger logger) {
        this.levenshteinDistanceThreshold = levenshteinDistanceThreshold;
        this.limit = limit;
        this.logger = logger;

        this.levenshteinDistance = new LevenshteinDistance(this.levenshteinDistanceThreshold);
    }

    /**
     * Is match boolean.
     *
     * @param left  the left
     * @param right the right
     * @return the boolean
     */
    public boolean isMatch(String left, String right) {
        return left != null && right != null &&
            this.levenshteinDistance.apply(left, right) <= this.limit;
    }
}
