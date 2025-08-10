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
@Story("Negative delete scenarios")
public class DeletePlayerNegativeTests extends BasePlayerTest {

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","delete-negative","delete-admin"},
            threadPoolSize = 3,
            description = "Admin cannot delete another ADMIN (only self) — expect 403; entity must remain")
    @Description("Per rules, admin may operate on admin only if it is himself. Deleting another admin must be forbidden.")
    public void verifyAdminCannotDeleteAnotherAdmin() {
        PlayerResponsePojo targetAdmin = createUser(Role.ADMIN);
        performDeleteAndVerifyNotDeleted(targetAdmin.getId(), ADMIN, StatusCode._403_FORBIDDEN);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","delete-negative","delete-user"},
            threadPoolSize = 3,
            description = "User cannot delete self — expect 403; entity must remain")
    @Description("User is not allowed to delete at all, including self-delete.")
    public void verifyUserCannotDeleteSelf() {
        PlayerResponsePojo user = createUser(Role.USER, ADMIN);
        performDeleteAndVerifyNotDeleted(user.getId(), user.getLogin(), StatusCode._403_FORBIDDEN);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","delete-negative","delete-user"},
            threadPoolSize = 3,
            description = "User cannot delete another user — expect 403; entity must remain")
    @Description("User has no delete permission for any target.")
    public void verifyUserCannotDeleteAnotherUser() {
        PlayerResponsePojo targetUser = createUser(Role.USER, ADMIN);
        PlayerResponsePojo editorUser = createUser(Role.USER, ADMIN);
        performDeleteAndVerifyNotDeleted(targetUser.getId(), editorUser.getLogin(), StatusCode._403_FORBIDDEN);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","delete-negative","delete-request"},
            threadPoolSize = 3,
            description = "Delete with invalid playerId=0 must return 400 Bad Request")
    @Description("playerId is required and must be >0.")
    public void verifyDeleteRejectsZeroPlayerId() {
        DeletePlayerRequestPojo bad = new DeletePlayerRequestPojo().setPlayerId(0);
        playersApiClient.deletePlayer(ADMIN, bad).verifyStatusCode(StatusCode._400_BAD_REQUEST);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","delete-negative","delete-request"},
            threadPoolSize = 3,
            description = "Delete with invalid playerId<0 must return 400 Bad Request")
    @Description("playerId must be a positive number.")
    public void verifyDeleteRejectsNegativePlayerId() {
        DeletePlayerRequestPojo bad = new DeletePlayerRequestPojo().setPlayerId(-1);
        playersApiClient.deletePlayer(ADMIN, bad).verifyStatusCode(StatusCode._400_BAD_REQUEST);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","delete-negative","delete-not-found"},
            threadPoolSize = 3,
            description = "Delete non-existent id should return 204 No Content; GET also 204")
    @Description("Spec exposes 200/204/401/403 for delete. For non-existent id we expect 204 and no side effects.")
    public void verifyDeleteNonExistingIdIsNoop() {
        long nonexistentId = 9_000_000_000L;
        performDeleteNonExistingAndVerifyNoContent(nonexistentId, ADMIN);
    }

    private void performDeleteAndVerifyNotDeleted(long id, String editorLogin, StatusCode expected) {
        ResponseWrapper del = playersApiClient.deletePlayer(editorLogin, new DeletePlayerRequestPojo().setPlayerId(id));
        del.verifyStatusCode(expected);
        playersApiClient.getPlayerById(id).verifyStatusCode(StatusCode._200_OK);
    }

    private void performDeleteNonExistingAndVerifyNoContent(long id, String editorLogin) {
        ResponseWrapper del = playersApiClient.deletePlayer(editorLogin, new DeletePlayerRequestPojo().setPlayerId(id));
        del.verifyStatusCode(StatusCode._204_NO_CONTENT);
        playersApiClient.getPlayerById(id).verifyStatusCode(StatusCode._204_NO_CONTENT);
    }

}