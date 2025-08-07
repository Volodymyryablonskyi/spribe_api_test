package com.spribe.yablonskyi.testdata;

import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.util.Randomizer;
import com.spribe.yablonskyi.util.TimeUtil;

public class PlayerTestData {

    private final String[] genders = new String[]{"Male", "Female"};

    protected String getValidAge() {
        return Randomizer.getRandomNumberInRangeString(18, 60);
    }

    protected String getValidGender() {
        return genders[Randomizer.getRandomNumberInRange(0, genders.length - 1)];
    }

    protected String getInvalidGender() {
        return "gender_" + Randomizer.getRandomAlphanumeric(3) + TimeUtil.getCurrentTimeStamp();
    }

    protected String getValidLogin() {
        return "login_" + Randomizer.getRandomAlphanumeric(3) + TimeUtil.getCurrentTimeStamp();
    }

    protected String getValidPassword() {
        return "password_" + Randomizer.getRandomAlphanumeric(3) + TimeUtil.getCurrentTimeStamp();
    }

    protected String getUserRole() {
        return "user";
    }

    protected String getValidScreenName() {
        return "screen_" + Randomizer.getRandomAlphanumeric(3) + TimeUtil.getCurrentTimeStamp();
    }

    public PlayerRequestPojo generateValidPlayer() {
        return new PlayerRequestPojo()
                .setAge(getValidAge())
                .setGender(getValidGender())
                .setLogin(getValidLogin())
                .setPassword(getValidPassword())
                .setRole(getUserRole())
                .setScreenName(getValidScreenName());
    }

}
