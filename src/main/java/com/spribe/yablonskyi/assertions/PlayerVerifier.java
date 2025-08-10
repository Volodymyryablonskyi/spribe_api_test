package com.spribe.yablonskyi.assertions;

import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import com.spribe.yablonskyi.util.CustomLogger;
import org.testng.asserts.SoftAssert;

public class PlayerVerifier {

    private static CustomLogger log = CustomLogger.getLogger(PlayerVerifier.class);

    private final PlayerResponsePojo actual;
    private final SoftAssert soft;

    private PlayerVerifier(PlayerResponsePojo actual) {
        this.actual = actual;
        this.soft = new SoftAssert();
    }

    public static PlayerVerifier verifyThat(PlayerResponsePojo actual) {
        return new PlayerVerifier(actual);
    }

    public PlayerVerifier matches(PlayerRequestPojo expected) {
        soft.assertEquals(actual.getLogin(), expected.getLogin(), "Login mismatch");
        soft.assertEquals(actual.getRole(), expected.getRole(), "Role mismatch");
        soft.assertEquals(String.valueOf(actual.getAge()), expected.getAge(), "Age mismatch");
        soft.assertEquals(actual.getGender(), expected.getGender(), "Gender mismatch");
        soft.assertEquals(actual.getScreenName(), expected.getScreenName(), "ScreenName mismatch");
        return this;
    }

    public PlayerVerifier equalsTo(PlayerResponsePojo expected) {
        soft.assertEquals(actual.getLogin(), expected.getLogin(), "Login changed unexpectedly");
        soft.assertEquals(actual.getScreenName(), expected.getScreenName(), "ScreenName changed unexpectedly");
        soft.assertEquals(actual.getGender(), expected.getGender(), "Gender changed unexpectedly");
        soft.assertEquals(actual.getRole(), expected.getRole(), "Role changed unexpectedly");
        soft.assertEquals(actual.getAge(), expected.getAge(), "Age changed unexpectedly");
        return this;
    }


    public PlayerVerifier hasValidId() {
        soft.assertTrue(actual.getId() > 0, "ID must be greater than 0");
        return this;
    }

    public void assertAll() {
        soft.assertAll();
    }

    public static void verifyMatches(PlayerRequestPojo req, PlayerResponsePojo resp) {
        log.info("Verify Request - {} matches with Response - {}", req, resp);
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(resp.getLogin(), req.getLogin(), "Login mismatch");
        sa.assertEquals(resp.getRole(), req.getRole(), "Role mismatch");
        sa.assertEquals(String.valueOf(resp.getAge()), req.getAge(), "Age mismatch");
        sa.assertEquals(resp.getGender(), req.getGender(), "Gender mismatch");
        sa.assertEquals(resp.getScreenName(), req.getScreenName(), "ScreenName mismatch");
        sa.assertTrue(resp.getId() > 0, "ID must be greater than 0");
        sa.assertAll();
    }

}