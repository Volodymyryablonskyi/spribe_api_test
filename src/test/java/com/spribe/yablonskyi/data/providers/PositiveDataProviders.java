package com.spribe.yablonskyi.data.providers;

import com.spribe.yablonskyi.data.Role;
import org.testng.annotations.DataProvider;

public class PositiveDataProviders {

    @DataProvider(name = "editorsAndTargets")
    public Object[][] editorsAndTargets() {
        return new Object[][]{
                {Role.SUPERVISOR, Role.ADMIN},
                {Role.SUPERVISOR, Role.USER},
                {Role.ADMIN, Role.USER},
        };
    }
    @DataProvider(name = "boundaryAges")
    public Object[][] boundaryAges() {
        return new Object[][] {{"17"}, {"59"}};
    }

    @DataProvider(name = "allowedGenders")
    public Object[][] allowedGenders() {
        return new Object[][]{ {"male"}, {"female"}};
    }

    @DataProvider(name = "passwordLengths")
    public Object[][] passwordLengths() {
        return new Object[][]{ {7}, {8}, {10}, {15}};
    }

}