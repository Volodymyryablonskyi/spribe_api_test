package com.spribe.yablonskyi.tests.getbyid;

import com.spribe.yablonskyi.assertions.PlayerVerifier;
import com.spribe.yablonskyi.base.BasePlayerTest;
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
@Feature("Get Player")
@Story("Positive get-by-id scenarios")
public class GetPlayerByIdPositiveTests extends BasePlayerTest {

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","get-positive","get-by-id"},
            threadPoolSize = 3,
            description = "GET player by id returns 200 and payload matches created entity")
    @Description("Create player (editor = SUPERVISOR), then /player/get by id must return 200 and fields equal to the created payload.")
    public void verifyGetByIdReturnsPlayerAndMatchesData() {
        PlayerRequestPojo expected = playersDataGenerator.get().generateValidPlayer(Role.USER.getLogin());
        var createResp = playersApiClient.createPlayer(SUPERVISOR, expected).verifyStatusCodeCreated();
        registerIfCreated(createResp);
        long id = createResp.getId();
        var getResp = playersApiClient.getPlayerById(id).verifyStatusCode(StatusCode._200_OK);
        PlayerResponsePojo actual = getResp.asPojo(PlayerResponsePojo.class);
        PlayerVerifier.verifyThat(actual)
                .matches(expected)
                .hasValidId()
                .assertAll();
    }

}
