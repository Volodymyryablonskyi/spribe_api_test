// src/test/java/com/spribe/yablonskyi/tests/getbyid/GetByIdNegativeTests.java
package com.spribe.yablonskyi.tests.getbyid;

import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.data.providers.NegativeDataProvider;
import com.spribe.yablonskyi.http.response.StatusCode;
import com.spribe.yablonskyi.pojo.DeletePlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Epic("Players Controller API")
@Feature("Get Player")
@Story("Negative get-by-id scenarios")
public class GetPlayerByIdNegativeTests extends BasePlayerTest {

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","get-negative","get-by-id"},
            threadPoolSize = 3,
            description = "GET by id for a deleted player must return 204 No Content")
    @Description("Create USER via SUPERVISOR, delete it, then GET by id should return 204 (entity not found).")
    public void verifyGetByIdReturnsNoContentForDeletedPlayer() {
        PlayerResponsePojo created = createUser(Role.USER, ADMIN);
        playersApiClient.deletePlayer(ADMIN, new DeletePlayerRequestPojo().setPlayerId(created.getId()))
                .verifyStatusCodeIn(StatusCode._200_OK, StatusCode._204_NO_CONTENT);

        verifyPlayerNotFound(created.getId());
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","get-negative","get-by-id"},
            threadPoolSize = 3,
            description = "GET by id for a non-existing player must return 204 No Content")
    @Description("Use a very large ID that should not exist. Service should respond with 204.")
    public void verifyGetByIdReturnsNoContentForNonExistingId() {
        long nonExistingId = Long.MAX_VALUE - 12345;
        verifyPlayerNotFound(nonExistingId);
    }

    @Test(alwaysRun = true,
            dataProvider = "invalidIds",
            dataProviderClass = NegativeDataProvider.class,
            groups = {"regression","api","api-players","get-negative","get-by-id"},
            threadPoolSize = 3,
            description = "GET by id with invalid id (0 or negative) must return 400 Bad Request")
    @Description("IDs must be positive. Zero/negative IDs are invalid and should be rejected with 400.")
    public void verifyGetByIdRejectsInvalidIds(long badId) {
        playersApiClient.getPlayerById(badId)
                .verifyStatusCode(StatusCode._400_BAD_REQUEST);
    }


    private void verifyPlayerNotFound(long id) {
        playersApiClient.getPlayerById(id)
                .verifyStatusCode(StatusCode._204_NO_CONTENT);
    }
}
