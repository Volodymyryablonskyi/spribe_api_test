package com.spribe.yablonskyi.tests.access;

import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.data.AccessDataProvider;
import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.http.response.StatusCode;
import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Epic("Player Management")
@Feature("Access Control")
@Story("Role-based access to player operations")
public class RolesAccessTests extends BasePlayerTest {

    // ---------- CREATE ACCESS ----------
    @Test(alwaysRun = true,
            dataProvider = "createAccess",
            dataProviderClass = AccessDataProvider.class,
            groups = {"regression", "api", "api-players", "create-player-access"},
            threadPoolSize = 3,
            description = "Checks access to CREATE users depending on editor and target roles")
    @Story("Role-based access to CREATE player")
    @Description("Only SUPERVISOR and ADMIN can create users (admin/user). USER must be forbidden.")
    public void create_access(Role editor, Role target, StatusCode expected) {
        callCreate(target, editor.getLogin()).verifyStatusCode(expected);
    }

    @Test(alwaysRun = true,
            dataProvider = "createRoleValidation",
            dataProviderClass = AccessDataProvider.class,
            groups = {"regression", "api", "api-players", "create-player-access"},
            threadPoolSize = 3,
            description = "Validates that creating a SUPERVISOR is not allowed")
    @Story("Role-based access to CREATE player")
    @Description("Creating a user with SUPERVISOR role should fail with validation/permission error.")
    public void create_role_validation(Role editor, Role target, StatusCode expected) {
        callCreate(target, editor.getLogin()).verifyStatusCode(expected);
    }

    // ---------- UPDATE ACCESS ----------
    @Test(alwaysRun = true,
            dataProvider = "updateAccess",
            dataProviderClass = AccessDataProvider.class,
            groups = {"regression", "api", "api-players", "update-player-access"},
            threadPoolSize = 3,
            description = "Checks access to UPDATE users depending on editor role and target role")
    @Story("Role-based access to UPDATE player")
    @Description("Supervisor can update anyone. Admin can update users and admin(self). User can update only self.")
    public void update_access(Role editor, Role targetRole, StatusCode expected) {
        PlayerResponsePojo target = createUser(targetRole, SUPERVISOR); // auto-cleanup
        PlayerRequestPojo partial = new PlayerRequestPojo()
                .setScreenName("upd_" + System.nanoTime());
        callUpdate(target.getId(), editor.getLogin(), partial).verifyStatusCode(expected);
    }

    // ---------- DELETE ACCESS ----------
    @Test(alwaysRun = true,
            dataProvider = "deleteAccess",
            dataProviderClass = AccessDataProvider.class,
            groups = {"regression", "api", "api-players", "delete-player-access"},
            threadPoolSize = 3,
            description = "Checks access to DELETE users depending on editor role and target role")
    @Story("Role-based access to DELETE player")
    @Description("Supervisor can delete admin/user (not supervisor). Admin can delete user and admin(self). User – nobody.")
    public void delete_access(Role editor, Role targetRole, StatusCode expected) {
        PlayerResponsePojo target = createUser(targetRole, SUPERVISOR);
        callDelete(target.getId(), editor.getLogin()).verifyStatusCode(expected);
    }

    // ---------- GET ACCESS ----------
    @Test(alwaysRun = true,
            dataProvider = "getAccess",
            dataProviderClass = AccessDataProvider.class,
            groups = {"regression", "api", "api-players", "get-player-access"},
            threadPoolSize = 3,
            description = "Checks access to GET user by id")
    @Story("Role-based access to GET player by id")
    @Description("Note: /player/get has no editor param; backend may ignore requester/self for access. Expectation depends on API impl.")
    public void get_access(Role targetRole, StatusCode expected) {
        PlayerResponsePojo target = createUser(targetRole, SUPERVISOR);
        playersApiClient.getPlayerById(target.getId()).verifyStatusCode(expected);
    }

}
