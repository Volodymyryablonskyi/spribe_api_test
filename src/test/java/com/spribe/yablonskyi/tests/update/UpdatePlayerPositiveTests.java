package com.spribe.yablonskyi.tests.update;

import com.spribe.yablonskyi.assertions.PlayerAssertions;
import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.data.providers.PositiveDataProviders;
import com.spribe.yablonskyi.http.response.StatusCode;
import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class UpdatePlayerPositiveTests extends BasePlayerTest {

    @Test(alwaysRun = true,
            dataProvider = "boundaryAges",
            dataProviderClass = PositiveDataProviders.class,
            groups = {"regression","api","api-players","update-positive","update-age"},
            threadPoolSize = 3,
            description = "Update 'age' to boundary values (17, 59) should succeed and persist")
    @Description("Age must be >16 and <60. Updating to 17/59 is accepted and persisted.")
    public void verifyUpdateAcceptsBoundaryAges(String newAge) {
        PlayerResponsePojo before = createUser(Role.USER, SUPERVISOR);
        PlayerRequestPojo partial = new PlayerRequestPojo().setAge(newAge);
        performUpdateAndVerify(before.getId(), SUPERVISOR, before, partial);
    }

    @Test(alwaysRun = true,
            dataProvider = "allowedGenders",
            dataProviderClass = PositiveDataProviders.class,
            groups = {"regression","api","api-players","update-positive","update-gender"},
            threadPoolSize = 3,
            description = "Update 'gender' to allowed values (male/female) should succeed and persist")
    @Description("Gender can be 'male' or 'female'. Update should persist the requested value.")
    public void verifyUpdateAcceptsAllowedGenders(String gender) {
        PlayerResponsePojo before = createUser(Role.USER, SUPERVISOR);
        PlayerRequestPojo partial = new PlayerRequestPojo().setGender(gender);
        performUpdateAndVerify(before.getId(), SUPERVISOR, before, partial);
    }

    @Test(alwaysRun = true,
            dataProvider = "passwordLengths",
            dataProviderClass = PositiveDataProviders.class,
            groups = {"regression","api","api-players","update-positive","update-password"},
            threadPoolSize = 3,
            description = "Update 'password' within valid length (7..15) should succeed")
    @Description("Password must be alphanumeric 7..15 with at least one letter and one digit. We assert 200 and integrity of other fields.")
    public void verifyUpdateAcceptsValidPasswordLengths(int length) {
        PlayerResponsePojo before = createUser(Role.USER, SUPERVISOR);
        String newPwd = playersDataGenerator.get().generateValidPassword(length);
        PlayerRequestPojo partial = new PlayerRequestPojo().setPassword(newPwd);
        performUpdateAndVerify(before.getId(), SUPERVISOR, before, partial);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","update-positive","update-login"},
            threadPoolSize = 3,
            description = "Update 'login' to a unique value should succeed and persist")
    @Description("Login must be unique. Updating to a new unique login is allowed and persisted.")
    public void verifyUpdateAllowsChangingLoginToUnique() {
        PlayerResponsePojo before = createUser(Role.USER, SUPERVISOR);
        String newLogin = playersDataGenerator.get().getValidLogin();
        PlayerRequestPojo partial = new PlayerRequestPojo().setLogin(newLogin);
        performUpdateAndVerify(before.getId(), SUPERVISOR, before, partial);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","update-positive","update-screenname"},
            threadPoolSize = 3,
            description = "Update 'screenName' to a unique value should succeed and persist")
    @Description("screenName must be unique. Updating to a new unique screenName is allowed and persisted.")
    public void verifyUpdateAllowsChangingScreenNameToUnique() {
        PlayerResponsePojo before = createUser(Role.USER, SUPERVISOR);
        String newScreen = playersDataGenerator.get().getValidScreenName();
        PlayerRequestPojo partial = new PlayerRequestPojo().setScreenName(newScreen);
        performUpdateAndVerify(before.getId(), SUPERVISOR, before, partial);
    }


    private void verifyUpdateApplied(long id, PlayerResponsePojo before, PlayerRequestPojo partial) {
        PlayerResponsePojo after = fetchPlayer(id);
        PlayerRequestPojo expected = new PlayerRequestPojo()
                .setAge(partial.getAge() != null ? partial.getAge() : String.valueOf(before.getAge()))
                .setGender(partial.getGender() != null ? partial.getGender() : before.getGender())
                .setLogin(partial.getLogin() != null ? partial.getLogin() : before.getLogin())
                .setRole(before.getRole())
                .setScreenName(partial.getScreenName() != null ? partial.getScreenName() : before.getScreenName());
        PlayerAssertions.assertMatches(after, expected);
    }

    protected void performUpdateAndVerify(long id, String editorLogin, PlayerResponsePojo before, PlayerRequestPojo partial) {
        callUpdate(id, editorLogin, partial).verifyStatusCode(StatusCode._200_OK);
        verifyUpdateApplied(id, before, partial);
    }

}
