package com.spribe.yablonskyi.data.providers;

import com.spribe.yablonskyi.data.Role;
import org.testng.annotations.DataProvider;

public class PositiveDataProviders {

    @DataProvider(name = "editorsAndTargets", parallel = true)
    public Object[][] editorsAndTargets() {
        return new Object[][]{
                {Role.SUPERVISOR, Role.ADMIN},
                {Role.SUPERVISOR, Role.USER},
                {Role.ADMIN,      Role.ADMIN},
                {Role.ADMIN,      Role.USER},
        };
    }

    @DataProvider(name = "boundaryAges", parallel = true)
    public Object[][] boundaryAges() {
        return new Object[][] {{"17"}, {"59"}};
    }

    @DataProvider(name = "allowedGenders", parallel = true)
    public Object[][] allowedGenders() {
        return new Object[][]{ {"male"}, {"female"}};
    }

    @DataProvider(name = "passwordLengths", parallel = true)
    public Object[][] passwordLengths() {
        return new Object[][]{ {7}, {8}, {10}, {15}};
    }

}