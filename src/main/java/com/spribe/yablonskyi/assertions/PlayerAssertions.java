package com.spribe.yablonskyi.assertions;

import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import org.testng.asserts.SoftAssert;

public class PlayerAssertions {

    public static void assertMatches(PlayerResponsePojo actual, PlayerRequestPojo expected) {
        SoftAssert s = new SoftAssert();
        s.assertEquals(actual.getLogin(), expected.getLogin(), "Login mismatch");
        s.assertEquals(actual.getRole(), expected.getRole(), "Role mismatch");
        s.assertEquals(String.valueOf(actual.getAge()), expected.getAge(), "Age mismatch");
        s.assertEquals(actual.getGender(), expected.getGender(), "Gender mismatch");
        s.assertEquals(actual.getScreenName(), expected.getScreenName(), "ScreenName mismatch");
        s.assertTrue(actual.getId() > 0, "ID must be greater than 0");
        s.assertAll();
    }
}
