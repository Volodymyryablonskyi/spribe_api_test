package com.spribe.yablonskyi.data.providers;

import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.util.Randomizer;
import org.testng.annotations.DataProvider;

public class NegativeDataProvider {

    @DataProvider(name = "invalidAges", parallel = true)
    public Object[][] invalidAges() {
        return new Object[][]{{"16"}, {"60"}, {"-1"}, {"0"}, {"120"}, {"abc"}};
    }

    @DataProvider(name = "invalidGenders", parallel = true)
    public Object[][] invalidGenders() {
        return new Object[][]{{""}, {"unknown"}, {"MALE"}, {"Female "}, {"mAle"}};
    }

    @DataProvider(name = "invalidPasswords", parallel = true)
    public Object[][] invalidPasswords() {
        return new Object[][]{
                {Randomizer.getRandomAlphanumeric(6)},
                {Randomizer.getRandomAlphanumeric(16)},
                {"1234567"},
                {"abcdefg"},
                {"abc123!"},
                {"пароль123"}
        };
    }

    @DataProvider(name = "invalidRolesOnCreate", parallel = true)
    public Object[][] invalidRolesOnCreate() {
        return new Object[][]{{Role.SUPERVISOR.getLogin()}, {"root"}, {""}, {"manager"}};
    }

    @DataProvider(name = "missingRequiredFields", parallel = true)
    public Object[][] missingRequiredFields() {
        return new Object[][]{{"age"}, {"gender"}, {"login"}, {"role"}, {"screenName"}};
    }

    @DataProvider(name = "invalidIds", parallel = true)
    public Object[][] invalidIds() {
        return new Object[][]{{0L}, {-1L},};
    }


}
