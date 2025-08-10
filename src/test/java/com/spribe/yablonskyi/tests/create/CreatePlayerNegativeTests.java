package com.spribe.yablonskyi.tests.create;

import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.data.providers.NegativeDataProvider;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import com.spribe.yablonskyi.http.response.StatusCode;
import com.spribe.yablonskyi.pojo.DeletePlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;


@Epic("Players Controller API")
@Feature("Create Player Negative")
public class CreatePlayerNegativeTests extends BasePlayerTest {

    @Test(alwaysRun = true,
            dataProvider = "invalidAges",
            dataProviderClass = NegativeDataProvider.class,
            groups = {"regression","api","api-players","create-negative","create-age"},
            threadPoolSize = 3,
            description = "Create player with invalid 'age' must return 400 Bad Request")
    @Description("Age must be >16 and <60. Values like 16, 60, negative, zero, too large or non-numeric should be rejected.")
    public void verifyCreateRejectsInvalidAge(String badAge) {
        PlayerRequestPojo req = playersDataGenerator.get()
                .generateValidPlayerWithAge(Role.USER.getLogin(), badAge);
        performCreateAndVerifyNotCreated(req, StatusCode._400_BAD_REQUEST);
    }

    @Test(alwaysRun = true,
            dataProvider = "invalidGenders",
            dataProviderClass = NegativeDataProvider.class,
            groups = {"regression","api","api-players","create-negative","create-gender"},
            threadPoolSize = 3,
            description = "Create player with invalid 'gender' must return 400 Bad Request")
    @Description("Only 'male' or 'female' are allowed. Empty, mixed case with spaces, or unknown values should be rejected.")
    public void verifyCreateRejectsInvalidGender(String badGender) {
        PlayerRequestPojo req = playersDataGenerator.get()
                .generateValidPlayerWithGender(Role.USER.getLogin(), badGender);
        performCreateAndVerifyNotCreated(req, StatusCode._400_BAD_REQUEST);
    }

    // ---------- INVALID PASSWORD -> 400 ----------
    @Test(alwaysRun = true,
            dataProvider = "invalidPasswords",
            dataProviderClass = NegativeDataProvider.class,
            groups = {"regression","api","api-players","create-negative","create-password"},
            threadPoolSize = 3,
            description = "Create player with invalid 'password' must return 400 Bad Request")
    @Description("Password must be alphanumeric 7..15 with at least one letter and one digit. Too short/long, digits-only, letters-only, non-latin should be rejected.")
    public void verifyCreateRejectsInvalidPassword(String badPassword) {
        PlayerRequestPojo req = playersDataGenerator.get()
                .generateValidPlayer(Role.USER.getLogin())
                .setPassword(badPassword);
        performCreateAndVerifyNotCreated(req, StatusCode._400_BAD_REQUEST);
    }

    // ---------- INVALID ROLE ON CREATE -> 400 ----------
    @Test(alwaysRun = true,
            dataProvider = "invalidRolesOnCreate",
            dataProviderClass = NegativeDataProvider.class,
            groups = {"regression","api","api-players","create-negative","create-role"},
            threadPoolSize = 3,
            description = "Create player with invalid 'role' must return 400 Bad Request")
    @Description("Only 'admin' or 'user' are allowed. 'supervisor' or unknown roles should be rejected.")
    public void verifyCreateRejectsInvalidRole(String badRole) {
        PlayerRequestPojo req = playersDataGenerator.get()
                .generateValidPlayer(badRole);
        performCreateAndVerifyNotCreated(req, StatusCode._400_BAD_REQUEST);
    }

    // ---------- MISSING REQUIRED FIELD -> 400 ----------
    @Test(alwaysRun = true,
            dataProvider = "missingRequiredFields",
            dataProviderClass = NegativeDataProvider.class,
            groups = {"regression","api","api-players","create-negative","create-required"},
            threadPoolSize = 3,
            description = "Create player without a required field must return 400 Bad Request")
    @Description("Required fields: age, gender, login, role, screenName. (password is optional)")
    public void verifyCreateRejectsMissingRequiredField(String missingField) {
        PlayerRequestPojo req = playersDataGenerator.get()
                .generateValidPlayer(Role.USER.getLogin());
        switch (missingField) {
            case "age" -> req.setAge(null);
            case "gender" -> req.setGender(null);
            case "login" -> req.setLogin(null);
            case "role" -> req.setRole(null);
            case "screenName" -> req.setScreenName(null);
            default -> throw new IllegalArgumentException("Unknown required field: " + missingField);
        }

        performCreateAndVerifyNotCreated(req, StatusCode._400_BAD_REQUEST);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","create-negative","create-unique"},
            threadPoolSize = 3,
            description = "Create player with duplicate 'login' must return 409 Conflict")
    @Description("Login must be unique. Creating another player with the same login should fail.")
    public void verifyCreateRejectsDuplicateLogin() {
        PlayerRequestPojo base = playersDataGenerator.get().generateValidPlayer(Role.USER.getLogin());
        createAsAdmin(base);
        PlayerRequestPojo dup = playersDataGenerator.get()
                .generateValidPlayer(Role.USER.getLogin())
                .setLogin(base.getLogin());
        performCreateAndVerifyNotCreated(dup, StatusCode._409_CONFLICT);
    }

    // ---------- DUPLICATE SCREEN NAME -> 409 ----------
    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","create-negative","create-unique"},
            threadPoolSize = 3,
            description = "Create player with duplicate 'screenName' must return 409 Conflict")
    @Description("ScreenName must be unique. Creating another player with the same screenName should fail.")
    public void verifyCreateRejectsDuplicateScreenName() {
        PlayerRequestPojo base = playersDataGenerator.get().generateValidPlayer(Role.USER.getLogin());
        createAsAdmin(base);
        PlayerRequestPojo dup = playersDataGenerator.get()
                .generateValidPlayer(Role.USER.getLogin())
                .setScreenName(base.getScreenName());
        performCreateAndVerifyNotCreated(dup, StatusCode._409_CONFLICT);
    }

    private void performCreateAndVerifyNotCreated(PlayerRequestPojo req, StatusCode expected) {
        ResponseWrapper resp = playersApiClient.createPlayer(ADMIN, req);
        if (resp.statusCode() == StatusCode._200_OK || resp.statusCode() == StatusCode._201_CREATED) {
            Long id = resp.getId();
            if (id != null && id > 0) {
                try {
                    playersApiClient.deletePlayer(ADMIN, new DeletePlayerRequestPojo().setPlayerId(id));
                } catch (Throwable ignored) { }
            }
        }
        resp.verifyStatusCode(expected);
    }
    
}
