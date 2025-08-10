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

@Epic("Player Management")
@Feature("Delete Player")
@Story("Negative delete scenarios")
public class DeletePlayerNegativeTests extends BasePlayerTest {

    // ---------- ADMIN: cannot delete other ADMIN (not self) -> 403 ----------
    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","delete-negative","delete-admin"},
            threadPoolSize = 3,
            description = "Admin cannot delete another ADMIN (only self) — expect 403; entity must remain")
    @Description("Per rules, admin may operate on admin only if it is himself. Deleting another admin must be forbidden.")
    public void verifyAdminCannotDeleteAnotherAdmin() {
        PlayerResponsePojo targetAdmin = createUser(Role.ADMIN, SUPERVISOR); // target admin
        performDeleteAndVerifyNotDeleted(targetAdmin.getId(), ADMIN, StatusCode._403_FORBIDDEN);
    }

    // ---------- USER: cannot delete self -> 403 ----------
    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","delete-negative","delete-user"},
            threadPoolSize = 3,
            description = "User cannot delete self — expect 403; entity must remain")
    @Description("User is not allowed to delete at all, including self-delete.")
    public void verifyUserCannotDeleteSelf() {
        PlayerResponsePojo user = createUser(Role.USER, SUPERVISOR);
        performDeleteAndVerifyNotDeleted(user.getId(), user.getLogin(), StatusCode._403_FORBIDDEN);
    }

    // ---------- USER: cannot delete another USER -> 403 ----------
    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","delete-negative","delete-user"},
            threadPoolSize = 3,
            description = "User cannot delete another user — expect 403; entity must remain")
    @Description("User has no delete permission for any target.")
    public void verifyUserCannotDeleteAnotherUser() {
        PlayerResponsePojo targetUser = createUser(Role.USER, SUPERVISOR);
        PlayerResponsePojo editorUser = createUser(Role.USER, SUPERVISOR);
        performDeleteAndVerifyNotDeleted(targetUser.getId(), editorUser.getLogin(), StatusCode._403_FORBIDDEN);
    }

    // ---------- INVALID playerId (0) -> 400 ----------
    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","delete-negative","delete-request"},
            threadPoolSize = 3,
            description = "Delete with invalid playerId=0 must return 400 Bad Request")
    @Description("playerId is required and must be >0.")
    public void verifyDeleteRejectsZeroPlayerId() {
        DeletePlayerRequestPojo bad = new DeletePlayerRequestPojo().setPlayerId(0);
        playersApiClient.deletePlayer(SUPERVISOR, bad).verifyStatusCode(StatusCode._400_BAD_REQUEST);
    }

    // ---------- INVALID playerId (-1) -> 400 ----------
    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","delete-negative","delete-request"},
            threadPoolSize = 3,
            description = "Delete with invalid playerId<0 must return 400 Bad Request")
    @Description("playerId must be a positive number.")
    public void verifyDeleteRejectsNegativePlayerId() {
        DeletePlayerRequestPojo bad = new DeletePlayerRequestPojo().setPlayerId(-1);
        playersApiClient.deletePlayer(SUPERVISOR, bad).verifyStatusCode(StatusCode._400_BAD_REQUEST);
    }

    // ---------- NON-EXISTENT id -> 204 (noop) ----------
    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","delete-negative","delete-not-found"},
            threadPoolSize = 3,
            description = "Delete non-existent id should return 204 No Content; GET also 204")
    @Description("Spec exposes 200/204/401/403 for delete. For non-existent id we expect 204 and no side effects.")
    public void verifyDeleteNonExistingIdIsNoop() {
        long nonexistentId = 9_000_000_000L; // малоймовірно, що існує
        performDeleteNonExistingAndVerifyNoContent(nonexistentId, SUPERVISOR);
    }

    // ---------- helpers (scoped to this test class) ----------
    private void performDeleteAndVerifyNotDeleted(long id, String editorLogin, StatusCode expected) {
        ResponseWrapper del = playersApiClient.deletePlayer(editorLogin, new DeletePlayerRequestPojo().setPlayerId(id));
        del.verifyStatusCode(expected);
        // Переконуємось, що юзер не видалений
        playersApiClient.getPlayerById(id).verifyStatusCode(StatusCode._200_OK);
    }

    private void performDeleteNonExistingAndVerifyNoContent(long id, String editorLogin) {
        ResponseWrapper del = playersApiClient.deletePlayer(editorLogin, new DeletePlayerRequestPojo().setPlayerId(id));
        del.verifyStatusCode(StatusCode._204_NO_CONTENT);
        playersApiClient.getPlayerById(id).verifyStatusCode(StatusCode._204_NO_CONTENT);
    }
}
