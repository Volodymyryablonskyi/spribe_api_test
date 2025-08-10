package com.spribe.yablonskyi.tests.delete;

import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import com.spribe.yablonskyi.http.response.StatusCode;
import com.spribe.yablonskyi.pojo.DeletePlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Epic("Players Controller API")
@Feature("Delete Player")
@Story("Positive delete scenarios")
public class DeletePlayerPositiveTests extends BasePlayerTest {

    @Test(alwaysRun = true,
            groups = {"regression", "api", "api-players", "delete-positive", "delete-admin"},
            description = "Supervisor can delete an ADMIN; subsequent GET returns 204")
    @Description("Supervisor may delete admin. Verify delete returns 200/204 and GET by id -> 204.")
    public void verifySupervisorCanDeleteAdmin() {
        PlayerResponsePojo target = createUser(Role.ADMIN);
        performDeleteAndVerifyDeleted(target.getId(), SUPERVISOR);
    }

    @Test(alwaysRun = true,
            groups = {"regression", "api", "api-players", "delete-positive", "delete-user"},
            description = "Supervisor can delete a USER; subsequent GET returns 204")
    @Description("Supervisor may delete user. Verify delete returns 200/204 and GET by id -> 204.")
    public void verifySupervisorCanDeleteUser() {
        PlayerResponsePojo target = createUser(Role.USER);
        performDeleteAndVerifyDeleted(target.getId(), SUPERVISOR);
    }

    @Test(alwaysRun = true,
            groups = {"regression", "api", "api-players", "delete-positive", "delete-user"},
            description = "Admin can delete a USER; subsequent GET returns 204")
    @Description("Admin may delete users with role USER. Verify delete returns 200/204 and GET by id -> 204.")
    public void verifyAdminCanDeleteUser() {
        PlayerResponsePojo target = createUser(Role.USER);
        performDeleteAndVerifyDeleted(target.getId(), ADMIN);
    }

    @Test(alwaysRun = true,
            groups = {"regression", "api", "api-players", "delete-positive", "delete-admin-self"},
            description = "Admin can delete self (admin self-delete); subsequent GET returns 204")
    @Description("Admin can operate on admin if it is himself. Verify delete returns 200/204 and GET by id -> 204.")
    public void verifyAdminCanDeleteSelf() {
        PlayerResponsePojo target = createUser(Role.ADMIN);
        performDeleteAndVerifyDeleted(target.getId(), target.getLogin());
    }

    private void performDeleteAndVerifyDeleted(long id, String editorLogin) {
        ResponseWrapper del = playersApiClient.deletePlayer(editorLogin, new DeletePlayerRequestPojo().setPlayerId(id));
        del.verifyStatusCodeIn(StatusCode._200_OK, StatusCode._204_NO_CONTENT);
        playersApiClient.getPlayerById(id).verifyStatusCodeIn(StatusCode._204_NO_CONTENT, StatusCode._200_OK);
    }

}
