package com.spribe.yablonskyi.data;

import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.util.Randomizer;
import com.spribe.yablonskyi.util.TimeUtil;

import java.util.concurrent.ThreadLocalRandom;


public class PlayerDataGenerator {

    private static final String[] GENDERS = {"male", "female"};

    public String getValidAge() {
        return Randomizer.getRandomNumberInRangeString(17, 59);
    }

    public String getValidGender() {
        return GENDERS[ThreadLocalRandom.current().nextInt(GENDERS.length)];
    }

    public String getValidPassword() {
        int len = Randomizer.getRandomNumberInRange(7, 15);
        return generateValidPassword(len);
    }

    public String getValidLogin() {
        return "login_" + Randomizer.getRandomAlphanumeric(3) + TimeUtil.getCurrentTimeStamp();
    }

    public String getValidScreenName() {
        return "screen_" + Randomizer.getRandomAlphanumeric(3) + TimeUtil.getCurrentTimeStamp();
    }

    public String getInvalidGender() {
        return "gender_" + Randomizer.getRandomAlphanumeric(3) + TimeUtil.getCurrentTimeStamp();
    }

    public String getInvalidRole() {
        return "invalidEditor";
    }

    public PlayerRequestPojo generateValidPlayer(String role) {
        return new PlayerRequestPojo()
                .setAge(getValidAge())
                .setGender(getValidGender())
                .setLogin(getValidLogin())
                .setPassword(getValidPassword())
                .setRole(role)
                .setScreenName(getValidScreenName());
    }

    public PlayerRequestPojo generateValidPlayerWithAge(String role, String age) {
        return generateValidPlayer(role).setAge(age);
    }

    public PlayerRequestPojo generateValidPlayerWithGender(String role, String gender) {
        return generateValidPlayer(role).setGender(gender);
    }

    public PlayerRequestPojo generateValidPlayerWithPasswordLength(String role, int length) {
        return generateValidPlayer(role).setPassword(generateValidPassword(length));
    }

    public String generateValidPassword(int len) {
        if (len < 7 || len > 15) {
            throw new IllegalArgumentException("Password length must be 7..15, got " + len);
        }
        return Randomizer.getRandomAlphabetic(1) +
                        Randomizer.getRandomNumberNotNull() +
                        Randomizer.getRandomAlphanumeric(len - 2);
    }

}