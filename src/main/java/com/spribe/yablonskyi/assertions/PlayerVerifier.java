package com.spribe.yablonskyi.assertions;

import com.spribe.yablonskyi.pojo.CreatePlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import org.testng.asserts.SoftAssert;

public class PlayerVerifier {

    private final PlayerResponsePojo actual;
    private final SoftAssert soft;

    private PlayerVerifier(PlayerResponsePojo actual) {
        this.actual = actual;
        this.soft = new SoftAssert();
    }

    public static PlayerVerifier verifyThat(PlayerResponsePojo actual) {
        return new PlayerVerifier(actual);
    }

    public PlayerVerifier matches(CreatePlayerRequestPojo expected) {
        soft.assertEquals(actual.getLogin(), expected.getLogin(), "Login mismatch");
        soft.assertEquals(actual.getRole(), expected.getRole(), "Role mismatch");
        soft.assertEquals(String.valueOf(actual.getAge()), expected.getAge(), "Age mismatch");
        soft.assertEquals(actual.getGender(), expected.getGender(), "Gender mismatch");
        soft.assertEquals(actual.getScreenName(), expected.getScreenName(), "ScreenName mismatch");
        return this;
    }

    public PlayerVerifier hasValidId() {
        soft.assertTrue(actual.getId() > 0, "ID must be greater than 0");
        return this;
    }

    public void assertAll() {
        soft.assertAll();
    }

}