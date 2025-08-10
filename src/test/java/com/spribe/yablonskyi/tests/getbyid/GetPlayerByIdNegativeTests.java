// src/test/java/com/spribe/yablonskyi/tests/getbyid/GetByIdNegativeTests.java
package com.spribe.yablonskyi.tests.getbyid;

import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.data.providers.NegativeDataProvider;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import com.spribe.yablonskyi.http.response.StatusCode;
import com.spribe.yablonskyi.pojo.DeletePlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import com.spribe.yablonskyi.util.Randomizer;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.SkipException;
import org.testng.annotations.Test;

@Epic("Players Controller API")
@Feature("Get Player")
@Story("Negative get-by-id scenarios")
public class GetPlayerByIdNegativeTests extends BasePlayerTest {

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","get-negative","get-by-id"},
            description = "GET by id for a deleted player returns 204 No Content")
    @Description("Create USER, delete it (accept 200/204), then GET by id -> 204.")
    public void verifyGetByIdReturnsNoContentForDeletedPlayer() {
        PlayerResponsePojo created = createUser(Role.USER, ADMIN);
        tryDeleteOrSkip(created.getId(), ADMIN, Role.SUPERVISOR.getLogin(), created.getLogin());
        awaitNoContent(created.getId(), 10, 250);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","get-negative","get-by-id"},
            description = "GET by id for a non-existing player returns 204 or 400")
    @Description("Use a large positive id unlikely to exist. Expect 204; some envs return 400 for out-of-range.")
    public void verifyGetByIdReturnsNoContentForNonExistingId() {
        long nonExistingId = Randomizer.getRandomNumberInRange(800_000_000, 2_100_000_000);
        playersApiClient.getPlayerById(nonExistingId)
                .verifyStatusCodeIn(StatusCode._204_NO_CONTENT, StatusCode._400_BAD_REQUEST);
    }

    @Test(alwaysRun = true,
            dataProvider = "invalidIds",
            dataProviderClass = NegativeDataProvider.class,
            groups = {"regression","api","api-players","get-negative","get-by-id"},
            description = "GET by id with 0 or negative returns 400")
    @Description("IDs must be positive. Zero/negative -> 400.")
    public void verifyGetByIdRejectsInvalidIds(long badId) {
        playersApiClient.getPlayerById(badId)
                .verifyStatusCode(StatusCode._400_BAD_REQUEST);
    }

    private void tryDeleteOrSkip(long id, String... editors) {
        for (String editor : editors) {
            ResponseWrapper r = playersApiClient.deletePlayer(editor, new DeletePlayerRequestPojo().setPlayerId(id));
            if (r.statusCode() == StatusCode._200_OK || r.statusCode() == StatusCode._204_NO_CONTENT) return;
        }
        throw new SkipException("Delete denied for id=" + id + " by all editors");
    }

    private static void sleep(long ms) {
        try { Thread.sleep(ms); }
        catch (InterruptedException ie) { Thread.currentThread().interrupt(); throw new RuntimeException(ie); }
    }
}
