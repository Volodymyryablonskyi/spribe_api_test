package com.spribe.yablonskyi.tests.create;

import com.spribe.yablonskyi.assertions.PlayerVerifier;
import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.data.providers.PositiveDataProviders;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import com.spribe.yablonskyi.http.response.StatusCode;
import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

@Epic("Players Controller API")
@Feature("Create Player Positive cases")
public class CreatePlayerPositiveTests extends BasePlayerTest {

    @Test(alwaysRun = true,
            dataProvider = "editorsAndTargets",
            dataProviderClass = PositiveDataProviders.class,
            groups = {"regression", "api", "api-players", "create-player-positive","create-editor-role"},
            description = "Create player: allowed editor/target combos per role model")
    @Description("SUPERVISOR can create ADMIN/USER; ADMIN can create USER. Response matches request.")
    public void verifyCreateAllowsValidEditorTargetCombos(Role editor, Role targetRole) {
        PlayerRequestPojo req = playersDataGenerator.get()
                .generateValidPlayer(editor.getLogin())
                .setRole(targetRole.name().toLowerCase());
        ResponseWrapper resp = createUser(editor.getLogin(), req)
                .verifyStatusCodeIn(StatusCode._200_OK, StatusCode._201_CREATED);
        PlayerResponsePojo actual = fetchPlayer(resp.getId());
        PlayerVerifier.verifyMatches(req, actual);
    }

    @Test(alwaysRun = true,
            dataProvider = "boundaryAges",
            dataProviderClass = PositiveDataProviders.class,
            groups = {"regression","api","api-players","create-player-positive","create-edge-age"},
            description = "Create player with boundary age (17 or 59) and response matches request")
    @Description("Age must be >16 and <60. Boundaries 17 and 59 are accepted and persisted as-is.")
    public void verifyCreateAcceptsBoundaryAges(String age) {
        PlayerRequestPojo req = playersDataGenerator.get()
                .generateValidPlayerWithAge(Role.USER.getLogin(), age);
        performCreateAndVerifyCreated(req);
    }

    @Test(alwaysRun = true,
            dataProvider = "allowedGenders",
            dataProviderClass = PositiveDataProviders.class,
            groups = {"regression","api","api-players","create-player-positive","create-valid-gender"},
            description = "Create player with allowed gender (male/female) and response matches request")
    @Description("Gender must be either 'male' or 'female'. Persisted value equals requested.")
    public void verifyCreateAcceptsAllowedGenders(String gender) {
        PlayerRequestPojo req = playersDataGenerator.get()
                .generateValidPlayerWithGender(Role.USER.getLogin(), gender);
        performCreateAndVerifyCreated(req);
    }

    @Test(alwaysRun = true,
            dataProvider = "passwordLengths",
            dataProviderClass = PositiveDataProviders.class,
            groups = {"regression","api","api-players","create-player-positive","create-valid-password"},
            description = "Create player with valid password length (7..15) and response matches request")
    @Description("Password must be alphanumeric 7..15 with at least one letter and one digit.")
    public void verifyCreateAcceptsValidPasswordLengths(int length) {
        PlayerRequestPojo req = playersDataGenerator.get()
                .generateValidPlayerWithPasswordLength(Role.USER.getLogin(), length);
        performCreateAndVerifyCreated(req);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","create-player-positive","create-password-optional"},
            description = "Create player without optional 'password' and response matches request")
    @Description("Password is optional; creation without password should succeed and persist accordingly (per API behavior).")
    public void verifyCreateAcceptsMissingOptionalPassword() {
        PlayerRequestPojo req = playersDataGenerator.get()
                .generateValidPlayer(Role.USER.getLogin())
                .setPassword(null);
        performCreateAndVerifyCreated(req);
    }

    private void performCreateAndVerifyCreated(PlayerRequestPojo expected) {
        ResponseWrapper resp = createAsAdmin(expected).verifyStatusCodeIn(StatusCode._200_OK, StatusCode._201_CREATED);
        verifyPlayerCreatedCorrectly(expected, resp.getId());
    }

    private void verifyPlayerCreatedCorrectly(PlayerRequestPojo expected, long id) {
        ResponseWrapper getResp = playersApiClient.getPlayerById(id);
        if (!getResp.statusCode().equals(StatusCode._200_OK)) {
            throw new AssertionError("GET by id failed. code=" + getResp.statusCode() + " body=" + getResp.asString());
        }
        PlayerResponsePojo actual = getResp.asPojo(PlayerResponsePojo.class);
        PlayerVerifier.verifyMatches(expected, actual);
    }

}