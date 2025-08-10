package com.spribe.yablonskyi.tests.update;

import com.spribe.yablonskyi.assertions.PlayerVerifier;
import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.data.providers.NegativeDataProvider;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import com.spribe.yablonskyi.http.response.StatusCode;
import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;


@Epic("Players Controller API")
@Feature("Update Player")
@Story("Negative update scenarios")
public class UpdatePlayerNegativeTests extends BasePlayerTest {

    @Test(alwaysRun = true,
            dataProvider = "invalidAges",
            dataProviderClass = NegativeDataProvider.class,
            groups = {"regression","api","api-players","update-negative","update-age"},
            description = "Update player with invalid 'age' must return 400 Bad Request and not change entity")
    @Description("Age must be >16 and <60. Out-of-range or non-numeric should be rejected.")
    public void verifyUpdateRejectsInvalidAges(String badAge) {
        PlayerResponsePojo before = createUser(Role.USER, ADMIN);
        PlayerRequestPojo partial = new PlayerRequestPojo().setAge(badAge);
        performUpdateAndVerifyNotUpdated(before.getId(), ADMIN, before, partial, StatusCode._400_BAD_REQUEST);
    }

    @Test(alwaysRun = true,
            dataProvider = "invalidGenders",
            dataProviderClass = NegativeDataProvider.class,
            groups = {"regression","api","api-players","update-negative","update-gender"},
            description = "Update player with invalid 'gender' must return 400 Bad Request and not change entity")
    @Description("Only 'male'/'female' are allowed. Any other value should be rejected.")
    public void verifyUpdateRejectsInvalidGenders(String badGender) {
        PlayerResponsePojo before = createUser(Role.USER, ADMIN);
        PlayerRequestPojo partial = new PlayerRequestPojo().setGender(badGender);
        performUpdateAndVerifyNotUpdated(before.getId(), ADMIN, before, partial, StatusCode._400_BAD_REQUEST);
    }

    @Test(alwaysRun = true,
            dataProvider = "invalidPasswords",
            dataProviderClass = NegativeDataProvider.class,
            groups = {"regression","api","api-players","update-negative","update-password"},
            description = "Update player with invalid 'password' must return 400 Bad Request and not change entity")
    @Description("Password must be alphanumeric 7..15 with at least one letter and one digit.")
    public void verifyUpdateRejectsInvalidPasswords(String badPassword) {
        PlayerResponsePojo before = createUser(Role.USER, ADMIN);
        PlayerRequestPojo partial = new PlayerRequestPojo().setPassword(badPassword);
        performUpdateAndVerifyNotUpdated(before.getId(), ADMIN, before, partial, StatusCode._400_BAD_REQUEST);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","update-negative","update-unique"},
            description = "Update 'login' to a duplicate must return 409 Conflict and not change entity")
    @Description("Login is unique. Trying to set an existing login on another user must fail.")
    public void verifyUpdateRejectsDuplicateLogin() {
        PlayerResponsePojo userA = createUser(Role.USER, ADMIN);
        PlayerResponsePojo userB = createUser(Role.USER, ADMIN);
        PlayerRequestPojo partial = new PlayerRequestPojo().setLogin(userA.getLogin());
        performUpdateAndVerifyNotUpdated(userB.getId(), ADMIN, userB, partial, StatusCode._409_CONFLICT);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","update-negative","update-unique"},
            description = "Update 'screenName' to a duplicate must return 409 Conflict and not change entity")
    @Description("screenName is unique. Trying to set an existing screenName on another user must fail.")
    public void verifyUpdateRejectsDuplicateScreenName() {
        PlayerResponsePojo userA = createUser(Role.USER, ADMIN);
        PlayerResponsePojo userB = createUser(Role.USER, ADMIN);
        PlayerRequestPojo partial = new PlayerRequestPojo().setScreenName(userA.getScreenName());
        performUpdateAndVerifyNotUpdated(userB.getId(), ADMIN, userB, partial, StatusCode._409_CONFLICT);
    }

    protected ResponseWrapper performUpdateAndVerifyNotUpdated(long id, String editorLogin, PlayerResponsePojo before, PlayerRequestPojo partial, StatusCode expected) {
        ResponseWrapper resp = callUpdate(id, editorLogin, partial);
        resp.verifyStatusCode(expected);
        PlayerResponsePojo after = fetchPlayer(id);
        PlayerVerifier.verifyThat(after)
                .equalsTo(before)
                .assertAll();
        return resp;
    }

}