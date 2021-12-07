package com.bka.ssi.controller.accreditation.company.application.utilities;

import java.security.SecureRandom;

public class NonceGenerator {

    private final static SecureRandom secureRandom = new SecureRandom();

    private NonceGenerator() {
    }

    public static int nextPositiveInt() {
        return secureRandom.nextInt() & Integer.MAX_VALUE;
    }

    public static int nextPositiveIntExcludingIntMaxValue() {
        return secureRandom.nextInt(Integer.MAX_VALUE);
    }

    public static int notSecureNextPositiveIntFavoringAllBesidesZero() {
        return Math.abs(secureRandom.nextInt());
    }
}
