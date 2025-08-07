package com.spribe.yablonskyi.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {

    public static String getRandomAlphabetic(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static int getRandomNumberInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static String getRandomNumberInRangeString(int min, int max) {
        return String.valueOf(getRandomNumberInRange(min, max));
    }

    public static String getRandomAlphanumeric(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String getRandomNumberNotNull() {
        String number = RandomStringUtils.randomNumeric(1);
        while (number.equals("0")) {
            number = RandomStringUtils.randomNumeric(1);
        }
        return number;
    }


}