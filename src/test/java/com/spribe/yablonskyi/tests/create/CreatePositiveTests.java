package com.spribe.yablonskyi.tests.create;

import com.spribe.yablonskyi.assertions.Assertions;
import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.data.PositiveTestsDataProviders;
import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class CreatePositiveTests extends BasePlayerTest {

    @Test(alwaysRun = true,
            dataProvider = "editorsAndTargets",
            dataProviderClass = PositiveTestsDataProviders.class,
            groups = {"regression","api","api-players","create-positive","create-editor-role"},
            threadPoolSize = 3,
            description = "Create player: SUPERVISOR creates ADMIN/USER")
    @Description("SUPERVISOR can create users with roles ADMIN/USER. Created IDs auto-registered and request matches response.")
    public void create_allowed_editors_and_roles(Role ignoredEditor, Role targetRole) {
        PlayerRequestPojo req = playersDataGenerator.get().generateValidPlayer(targetRole.getLogin());
        ResponseWrapper resp = playersApiClient.createPlayer(SUPERVISOR, req);
        Assertions.assertCreated(resp.statusCode());
        registerIfCreated(resp);
        Long id = resp.getId();
        verifyPlayerCreatedCorrectly(req, id);
    }

    @Test(alwaysRun = true,
            dataProvider = "boundaryAges",
            dataProviderClass = PositiveTestsDataProviders.class,
            groups = {"regression","api","api-players","create-positive","create-age"},
            threadPoolSize = 3,
            description = "Create with boundary ages 17 and 59 (editor = SUPERVISOR)")
    @Description("User must be >16 and <60. Boundaries 17 and 59 are accepted and persisted as-is.")
    public void create_with_boundary_age_is_accepted(String age) {
        PlayerRequestPojo req = playersDataGenerator.get().generateValidPlayerWithAge(Role.USER.getLogin(), age);
        ResponseWrapper resp = playersApiClient.createPlayer(SUPERVISOR, req);
        Assertions.assertCreated(resp.statusCode());
        registerIfCreated(resp);
        Long id = resp.getId();
        verifyPlayerCreatedCorrectly(req, id);
    }

    @Test(alwaysRun = true,
            dataProvider = "allowedGenders",
            dataProviderClass = PositiveTestsDataProviders.class,
            groups = {"regression","api","api-players","create-positive","create-gender"},
            threadPoolSize = 3,
            description = "Create with allowed genders male/female (editor = SUPERVISOR)")
    @Description("Gender must be male or female. Persisted value equals requested.")
    public void create_with_allowed_gender_is_accepted(String gender) {
        PlayerRequestPojo req = playersDataGenerator.get().generateValidPlayerWithGender(Role.USER.getLogin(), gender);
        ResponseWrapper resp = playersApiClient.createPlayer(SUPERVISOR, req);
        Assertions.assertCreated(resp.statusCode());
        registerIfCreated(resp);
        Long id = resp.getId();
        verifyPlayerCreatedCorrectly(req, id);
    }

    @Test(alwaysRun = true,
            dataProvider = "passwordLengths",
            dataProviderClass = PositiveTestsDataProviders.class,
            groups = {"regression","api","api-players","create-positive","create-password"},
            threadPoolSize = 3,
            description = "Create with valid password length 7..15 (editor = SUPERVISOR)")
    @Description("Password must be alphanumeric 7..15 with at least one letter and one digit. Persisted equals requested (if API returns password).")
    public void create_with_valid_password_lengths_is_accepted(int length) {
        PlayerRequestPojo req = playersDataGenerator.get().generateValidPlayerWithPasswordLength(Role.USER.getLogin(), length);
        ResponseWrapper resp = playersApiClient.createPlayer(SUPERVISOR, req);
        Assertions.assertCreated(resp.statusCode());
        registerIfCreated(resp);
        Long id = resp.getId();
        verifyPlayerCreatedCorrectly(req, id);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","create-positive","create-optional"},
            threadPoolSize = 3,
            description = "Create without optional field 'password' (editor = SUPERVISOR)")
    @Description("Password is optional; creation without password should succeed and be persisted as such (depending on API behavior).")
    public void create_without_optional_password_is_accepted() {
        PlayerRequestPojo req = playersDataGenerator.get().generateValidPlayer(Role.USER.getLogin())
                .setPassword(null);
        ResponseWrapper resp = playersApiClient.createPlayer(SUPERVISOR, req);
        Assertions.assertCreated(resp.statusCode());
        registerIfCreated(resp);
        Long id = resp.getId();
        verifyPlayerCreatedCorrectly(req, id);
    }
}