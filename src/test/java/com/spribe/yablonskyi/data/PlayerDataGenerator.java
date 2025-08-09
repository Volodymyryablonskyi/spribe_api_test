package com.spribe.yablonskyi.data;

import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.util.Randomizer;
import com.spribe.yablonskyi.util.TimeUtil;

public class PlayerDataGenerator {

    private final String[] genders = new String[]{"Male", "Female"};

    public String getValidAge() {
        return Randomizer.getRandomNumberInRangeString(18, 60);
    }

    public String getValidGender() {
        return genders[Randomizer.getRandomNumberInRange(0, genders.length - 1)];
    }

    public String getInvalidGender() {
        return "gender_" + Randomizer.getRandomAlphanumeric(3) + TimeUtil.getCurrentTimeStamp();
    }

    public String getValidLogin() {
        return "login_" + Randomizer.getRandomAlphanumeric(3) + TimeUtil.getCurrentTimeStamp();
    }

    public String getValidPassword() {
        return "password_" + Randomizer.getRandomAlphanumeric(3) + TimeUtil.getCurrentTimeStamp();
    }

    public String getValidScreenName() {
        return "screen_" + Randomizer.getRandomAlphanumeric(3) + TimeUtil.getCurrentTimeStamp();
    }

    public String getInvalidRole() { return "invalidEditor"; }

    public PlayerRequestPojo generateValidPlayer(String role) {
        return new PlayerRequestPojo()
                .setAge(getValidAge())
                .setGender(getValidGender())
                .setLogin(getValidLogin())
                .setPassword(getValidPassword())
                .setRole(role)
                .setScreenName(getValidScreenName());
    }

}
