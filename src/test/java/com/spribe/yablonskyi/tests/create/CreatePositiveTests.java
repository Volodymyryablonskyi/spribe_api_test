package com.spribe.yablonskyi.tests.create;

import com.spribe.yablonskyi.assertions.Assertions;
import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.data.GlobalTestData;
import com.spribe.yablonskyi.data.PositiveTestsDataProviders;
import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import com.spribe.yablonskyi.http.response.StatusCode;
import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.util.CustomLogger;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import java.util.Map;

import static com.spribe.yablonskyi.data.GlobalTestData.*;
import static jdk.internal.net.http.common.Log.logParams;

public class CreatePositiveTests extends BasePlayerTest {

    private static final CustomLogger log = CustomLogger.getLogger(CreatePositiveTests.class);

    @Test(alwaysRun = true,
            dataProvider = "editorsAndTargets",
            dataProviderClass = PositiveTestsDataProviders.class,
            groups = {"regression","api","api-players","create-positive","create-editor-role"},
            threadPoolSize = 3,
            description = "Create player: allowed editors (supervisor/admin) and allowed target roles (admin/user)")
    @Description("Verifies that SUPERVISOR/ADMIN can create users with roles ADMIN/USER.")
    public void create_allowed_editors_and_roles(Role editor, Role target) {
        ResponseWrapper response = callCreate(target, editor.getLogin());
        long id = response.getId();
        log.logParams(Map.of(OPERATION, OP_CREATE, EDITOR, editor.getLogin(), TARGET, target.getLogin()));
        Assertions.assertCreated(response.statusCode());
    }

    @Test(alwaysRun = true,
            dataProvider = "boundaryAges",
            dataProviderClass = PositiveTestsDataProviders.class,
            groups = {"regression","api","api-players","create-positive","create-age"},
            threadPoolSize = 3,
            description = "Create with boundary ages 17 and 59")
    @Description("User should be >16 and <60. Boundaries 17 and 59 are accepted.")
    public void create_with_boundary_age_is_accepted(String age) {
        PlayerRequestPojo req = playersDataGenerator.get().generateValidPlayerWithAge(Role.USER.getLogin(), age);
        StatusCode actual = playersApiClient.createPlayer(SUPERVISOR, req).statusCode();
        log.logParams(java.util.Map.of("Operation","CREATE","Editor",SUPERVISOR,"TargetRole","user","Age",age,"Actual",actual));
        Assertions.assertCreated(actual);
    }

    @Test(alwaysRun = true,
            dataProvider = "allowedGenders",
            dataProviderClass = PositiveTestsDataProviders.class,
            groups = {"regression","api","api-players","create-positive","create-gender"},
            threadPoolSize = 3,
            description = "Create with allowed genders: male/female")
    @Description("Gender must be male or female.")
    public void create_with_allowed_gender_is_accepted(String gender) {
        PlayerRequestPojo req = playersDataGenerator.get().generateValidPlayerWithGender(Role.USER.getLogin(), gender);
        StatusCode actual = playersApiClient.createPlayer(ADMIN, req).statusCode();
        log.logParams(java.util.Map.of("Operation","CREATE","Editor", ADMIN,"TargetRole","user","Gender",gender,"Actual",actual));
        Assertions.assertCreated(actual);
    }

    @Test(alwaysRun = true,
            dataProvider = "passwordLengths",
            dataProviderClass = PositiveTestsDataProviders.class,
            groups = {"regression","api","api-players","create-positive","create-password"},
            threadPoolSize = 3,
            description = "Create with valid password lengths 7..15, alphanumeric with at least one letter and one digit")
    @Description("Password must be alphanumeric 7..15, with letters and digits.")
    public void create_with_valid_password_lengths_is_accepted(int length) {
        PlayerRequestPojo req = playersDataGenerator.get().generateValidPlayerWithPasswordLength(Role.USER.getLogin(), length);
        StatusCode actual = playersApiClient.createPlayer(ADMIN, req).statusCode();
        log.logParams(java.util.Map.of("Operation","CREATE","Editor",ADMIN,"TargetRole","user","PasswordLength",length,"Password",req.getPassword(),"Actual",actual));
        Assertions.assertCreated(actual);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api", "api-players", "create-positive", "create-optional"},
            threadPoolSize = 3,
            description = "Create without optional field 'password'")
    @Description("Password is optional; creation without password should succeed.")
    public void create_without_optional_password_is_accepted() {
        PlayerRequestPojo req = playersDataGenerator.get().generateValidPlayer(Role.USER.getLogin())
                .setPassword(null);
        StatusCode actual = playersApiClient.createPlayer(SUPERVISOR, req).statusCode();
        log.logParams(java.util.Map.of("Operation","CREATE","Editor",SUPERVISOR,"TargetRole","user","Password","<null>","Actual",actual));
        Assertions.assertCreated(actual);
    }


}