package com.bka.ssi.controller.accreditation.company.application.utilities;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FuzzyMatcher {

    private final int levenshteinDistanceThreshold;
    private final int limit;

    private final LevenshteinDistance levenshteinDistance;
    private final Logger logger;

    public FuzzyMatcher(
        @Value("${fuzzy.levenshtein_distance.threshold}") int levenshteinDistanceThreshold,
        @Value("${accreditation.guest.basis_id.fuzzy.limit}") int limit,
        Logger logger) {
        this.levenshteinDistanceThreshold = levenshteinDistanceThreshold;
        this.limit = limit;
        this.logger = logger;

        this.levenshteinDistance = new LevenshteinDistance(this.levenshteinDistanceThreshold);
    }

    public boolean isMatch(String left, String right) {
        return left != null && right != null &&
            this.levenshteinDistance.apply(left, right) <= this.limit;
    }
}
