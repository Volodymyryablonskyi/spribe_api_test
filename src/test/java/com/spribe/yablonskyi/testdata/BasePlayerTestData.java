package com.spribe.yablonskyi.testdata;

import com.spribe.yablonskyi.config.ApplicationConfig;
import com.spribe.yablonskyi.util.Randomizer;

public class BasePlayerTestData {

    private final String[] genders = new String[]{"Male, Female"};

    protected String getRandomAge() {
        return Randomizer.getRandomNumberInRangeString(1, 100);
    }

    protected String getValidEditor() {
        return ApplicationConfig.getEditor();
    }

    protected String getInvalidEditor() {
        return "editor_" + Randomizer.getRandomAlphanumeric(5);
    }

    protected String getValidGender() {
        return genders[Randomizer.getRandomNumberInRange(0, genders.length - 1)];
    }

    protected String getInvalidGender() {
        return "gender_" + Randomizer.getRandomAlphanumeric(5);
    }

    protected String getValidLogin() {
        return "login_" + Randomizer.getRandomAlphanumeric(5);
    }

    protected String getValidPassword() {
        return "password_" + Randomizer.getRandomAlphanumeric(5);
    }

    protected String getUserRole() {
        return "user";
    }

    protected String getValidScreenName() {
        return "screen_" + Randomizer.getRandomAlphanumeric(5);
    }


}
