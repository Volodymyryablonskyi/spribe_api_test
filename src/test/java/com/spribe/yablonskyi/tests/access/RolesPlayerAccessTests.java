package com.spribe.yablonskyi.tests.access;

import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.data.providers.AccessDataProvider;
import com.spribe.yablonskyi.http.response.StatusCode;
import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

@Epic("Players Controller API")
@Feature("Role Access Control")
public class RolesPlayerAccessTests extends BasePlayerTest {

    @Test(alwaysRun = true,
            dataProvider = "createAccess",
            dataProviderClass = AccessDataProvider.class,
            groups = {"regression", "api", "api-players", "role-player-access", "create-player-access"},
            description = "Checks access to CREATE users depending on editor and target roles")
    @Description("Only SUPERVISOR and ADMIN can create users (admin/user). USER must be forbidden.")
    public void verifyCreateAccessBasedOnEditorAndTargetRoles(Role editor, Role target, StatusCode...expected) {
        callCreate(target, editor.getLogin()).verifyStatusCodeIn(expected);
    }

    @Test(alwaysRun = true,
            dataProvider = "createRoleValidation",
            dataProviderClass = AccessDataProvider.class,
            groups = {"regression", "api", "api-players", "role-player-access", "create-player-access"},
            description = "Validates that creating a SUPERVISOR is not allowed")
    @Description("Creating a user with SUPERVISOR role should fail with validation/permission error.")
    public void verifyCreateSupervisorRoleIsForbidden(Role editor, Role target, StatusCode...expected) {
        callCreate(target, editor.getLogin()).verifyStatusCodeIn(expected);
    }

    @Test(alwaysRun = true,
            dataProvider = "updateAccess",
            dataProviderClass = AccessDataProvider.class,
            groups = {"regression", "api", "api-players", "role-player-access", "update-player-access"},
            description = "Checks access to UPDATE users depending on editor, target roles and self-flag")
    @Description("Supervisor can update anyone. Admin can update users and admin(self). User can update only self.")
    public void verifyUpdateAccessBasedOnEditorTargetAndSelf(Role editor, Role targetRole, boolean self, StatusCode...expected) {
        PlayerResponsePojo target = createUser(targetRole, ADMIN);
        String editorLogin = self ? target.getLogin() : editor.getLogin();
        PlayerRequestPojo partial = new PlayerRequestPojo().setScreenName("upd_" + System.nanoTime());
        callUpdate(target.getId(), editorLogin, partial).verifyStatusCodeIn(expected);
    }

    @Test(alwaysRun = true,
            dataProvider = "deleteAccess",
            dataProviderClass = AccessDataProvider.class,
            groups = {"regression", "api", "api-players", "role-player-access", "delete-player-access"},
            description = "Checks access to DELETE users depending on editor, target roles and self-flag")
    @Description("Supervisor can delete admin/user (not supervisor). Admin can delete user and admin(self). User cannot delete.")
    public void verifyDeleteAccessBasedOnEditorTargetAndSelf(Role editor, Role targetRole, boolean self, StatusCode...expected) {
        PlayerResponsePojo target = createUser(targetRole, ADMIN);
        String editorLogin = self ? target.getLogin() : editor.getLogin();
        callDelete(target.getId(), editorLogin).verifyStatusCodeIn(expected);
    }

}
