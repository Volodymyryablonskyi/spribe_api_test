package com.spribe.yablonskyi.tests.access;

import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.data.AccessDataProvider;
import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.http.response.StatusCode;
import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Epic("Player Management")
@Feature("Access Control")
@Story("Role-based access to player operations")
public class RolesAccessTests extends BasePlayerTest {

    // ---------- CREATE ACCESS----------
    @Test(alwaysRun = true,
            dataProvider = "createAccess",
            dataProviderClass = AccessDataProvider.class,
            threadPoolSize = 3,
            description = "Checks access to CREATE users depending on editor and target roles")
    @Story("Role-based access to CREATE player")
    @Description("Only SUPERVISOR and ADMIN can create users (admin/user). USER must be forbidden.")
    public void create_access(Role editor, Role target, StatusCode expected) {
        StatusCode actual = callCreate(target, editor.getLogin()).statusCode();
        logAccessParams("CREATE", editor, target, expected, actual);
        assertEquals(actual, expected, "Status code mismatch on CREATE user");
    }

    @Test(alwaysRun = true,
            dataProvider = "createRoleValidation",
            dataProviderClass = AccessDataProvider.class,
            threadPoolSize = 3,
            description = "Validates that creating a SUPERVISOR is not allowed")
    @Story("Role-based access to CREATE player")
    @Description("Creating a user with SUPERVISOR role should fail with validation/permission error.")
    public void create_role_validation(Role editor, Role target, StatusCode expected) {
        StatusCode actual = callCreate(target, editor.getLogin()).statusCode();
        logAccessParams("CREATE (role validation)", editor, target, expected, actual);
        assertEquals(actual, expected, "Status code mismatch on CREATE SUPERVISOR");
    }

    // ---------- UPDATE ACCESS ----------
    @Test(alwaysRun = true,
            dataProvider = "updateAccess",
            dataProviderClass = AccessDataProvider.class,
            threadPoolSize = 3,
            description = "Checks access to UPDATE users depending on editor role, target role and self-flag")
    @Story("Role-based access to UPDATE player")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Supervisor can update anyone. Admin can update users and admin(self). User can update only self.")
    public void update_access(Role editor, Role targetRole, boolean self, StatusCode expected) {
        PlayerResponsePojo target = createUser(targetRole, SUPERVISOR);
        String editorLogin = self ? target.getLogin() : editor.getLogin();
        PlayerRequestPojo partial = new PlayerRequestPojo()
                .setScreenName("upd_" + System.nanoTime());
        StatusCode actual = callUpdate(target.getId(), editorLogin, partial).statusCode();
        logAccessParams("UPDATE", editor, targetRole, expected, actual);
        assertEquals(actual, expected, "Status code mismatch on UPDATE");
    }

    // ---------- DELETE ACCESS ----------
    @Test(alwaysRun = true,
            dataProvider = "deleteAccess",
            dataProviderClass = AccessDataProvider.class,
            threadPoolSize = 3,
            description = "Checks access to DELETE users depending on editor role, target role and self-flag")
    @Story("Role-based access to DELETE player")
    @Description("Supervisor can delete admin/user (not supervisor). Admin can delete user and admin(self). User – nobody.")
    public void delete_access(Role editor, Role targetRole, boolean self, StatusCode expected) {
        PlayerResponsePojo target = createUser(targetRole, SUPERVISOR);
        String editorLogin = self ? target.getLogin() : editor.getLogin();
        StatusCode actual = callDelete(target.getId(), editorLogin).statusCode();
        logAccessParams("DELETE", editor, targetRole, expected, actual);
        assertEquals(actual, expected, "Status code mismatch on DELETE");
    }

    // ---------- GET ACESS ----------
    @Test(alwaysRun = true,
            dataProvider = "getAccess",
            dataProviderClass = AccessDataProvider.class,
            threadPoolSize = 3,
            description = "Checks access to GET user by id depending on requester role, target role and self-flag (if enforced)")
    @Story("Role-based access to GET player by id")
    @Severity(SeverityLevel.NORMAL)
    @Description("Note: /player/get doesn't accept an editor; if backend doesn't enforce role here, results may be uniform.")
    public void get_access(Role requester, Role targetRole, boolean self, StatusCode expected) {
        PlayerResponsePojo target = createUser(targetRole, SUPERVISOR);

        StatusCode actual = playersApiClient.getPlayerById(target.getId()).statusCode();
        logAccessParams("GET", requester, targetRole, expected, actual);
        assertEquals(actual, expected, "Status code mismatch on GET by id");
    }

    @Step("Log access params: {operation}")
    private void logAccessParams(String operation,
                                 Role editor, Role target,
                                 StatusCode expected, StatusCode actual) {
        Allure.parameter("Operation", operation);
        Allure.parameter("Editor Role", editor != null ? editor.name() : "N/A");
        Allure.parameter("Target Role", target != null ? target.name() : "N/A");
        Allure.parameter("Expected Status", expected.name() + " (" + expected.getCode() + ")");
        Allure.parameter("Actual Status", actual.name() + " (" + actual.getCode() + ")");
    }

}
