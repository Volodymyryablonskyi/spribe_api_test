package com.spribe.yablonskyi.tests.delete;

import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import com.spribe.yablonskyi.http.response.StatusCode;
import com.spribe.yablonskyi.pojo.DeletePlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import com.spribe.yablonskyi.util.Randomizer;
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
            description = "Admin cannot delete another ADMIN (only self) — expect 403; entity must remain")
    @Description("Per rules, admin may operate on admin only if it is himself. Deleting another admin must be forbidden.")
    public void verifyAdminCannotDeleteAnotherAdmin() {
        PlayerResponsePojo targetAdmin = createUser(Role.ADMIN);
        performDeleteAndVerifyNotDeleted(targetAdmin.getId(), ADMIN, StatusCode._403_FORBIDDEN, StatusCode._400_BAD_REQUEST);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","delete-negative","delete-user"},
            description = "User cannot delete self — expect 403; entity must remain")
    @Description("User is not allowed to delete at all, including self-delete.")
    public void verifyUserCannotDeleteSelf() {
        PlayerResponsePojo user = createUser(Role.USER, ADMIN);
        performDeleteAndVerifyNotDeleted(user.getId(), user.getLogin(), StatusCode._403_FORBIDDEN, StatusCode._400_BAD_REQUEST);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","delete-negative","delete-user"},
            description = "User cannot delete another user — expect 403; entity must remain")
    @Description("User has no delete permission for any target.")
    public void verifyUserCannotDeleteAnotherUser() {
        PlayerResponsePojo targetUser = createUser(Role.USER, ADMIN);
        PlayerResponsePojo editorUser = createUser(Role.USER, ADMIN);
        performDeleteAndVerifyNotDeleted(targetUser.getId(), editorUser.getLogin(), StatusCode._403_FORBIDDEN, StatusCode._400_BAD_REQUEST);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","delete-negative","delete-request"},
            description = "Delete with invalid playerId=0 must return 400 Bad Request")
    @Description("playerId is required and must be >0.")
    public void verifyDeleteRejectsZeroPlayerId() {
        DeletePlayerRequestPojo bad = new DeletePlayerRequestPojo().setPlayerId(0);
        playersApiClient.deletePlayer(ADMIN, bad).verifyStatusCodeIn(StatusCode._403_FORBIDDEN, StatusCode._400_BAD_REQUEST);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","delete-negative","delete-request"},
            description = "Delete with invalid playerId<0 must return 400 Bad Request")
    @Description("playerId must be a positive number.")
    public void verifyDeleteRejectsNegativePlayerId() {
        DeletePlayerRequestPojo bad = new DeletePlayerRequestPojo().setPlayerId(-1);
        playersApiClient.deletePlayer(ADMIN, bad).verifyStatusCodeIn(StatusCode._403_FORBIDDEN, StatusCode._400_BAD_REQUEST);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","delete-negative","delete-not-found"},
            description = "Delete non-existent id should return 204 No Content; GET also 204")
    @Description("Spec exposes 200/204/401/403 for delete. For non-existent id we expect 204 and no side effects.")
    public void verifyDeleteNonExistingIdIsNoop() {
        long nonexistentId = Long.MAX_VALUE - Randomizer.getRandomNumberInRange(100, 1000000);
        performDeleteNonExistingAndVerifyNoContent(nonexistentId, ADMIN);
    }

    private void performDeleteAndVerifyNotDeleted(long id, String editorLogin, StatusCode... expected) {
        playersApiClient
                .deletePlayer(editorLogin, new DeletePlayerRequestPojo().setPlayerId(id))
                .verifyStatusCodeIn(expected);
        playersApiClient
                .getPlayerById(id)
                .verifyStatusCodeIn(StatusCode._200_OK);
    }

    private void performDeleteNonExistingAndVerifyNoContent(long id, String editorLogin) {
        playersApiClient
                .deletePlayer(editorLogin, new DeletePlayerRequestPojo().setPlayerId(id))
                .verifyStatusCodeIn(StatusCode._204_NO_CONTENT, StatusCode._403_FORBIDDEN);
        playersApiClient
                .getPlayerById(id)
                .verifyStatusCodeIn(StatusCode._204_NO_CONTENT, StatusCode._400_BAD_REQUEST);
    }

}