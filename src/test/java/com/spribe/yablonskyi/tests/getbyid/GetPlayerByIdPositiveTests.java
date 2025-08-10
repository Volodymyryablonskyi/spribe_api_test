package com.spribe.yablonskyi.tests.getbyid;

import com.spribe.yablonskyi.assertions.PlayerVerifier;
import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import com.spribe.yablonskyi.http.response.StatusCode;
import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Epic("Players Controller API")
@Feature("Get Player")
@Story("Positive get-by-id scenarios")
public class GetPlayerByIdPositiveTests extends BasePlayerTest {

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","get-positive","get-by-id"},
            description = "GET player by id returns 200 and payload matches created entity")
    @Description("Create player (editor = ADMIN), then /player/get by id must return 200 and fields equal to the created payload.")
    public void verifyGetByIdReturnsPlayerAndMatchesData() {
        PlayerRequestPojo expected = playersDataGenerator.get().generateValidPlayer(Role.USER.getLogin());
        var createResp = playersApiClient.createPlayer(ADMIN, expected).verifyStatusCodeCreated();
        registerIfCreated(createResp);
        long id = createResp.getId();
        ResponseWrapper getResp = playersApiClient.getPlayerById(id).verifyStatusCodeIn(StatusCode._200_OK, StatusCode._204_NO_CONTENT);
        PlayerResponsePojo actual = getResp.asPojo(PlayerResponsePojo.class);
        PlayerVerifier.verifyThat(actual)
                .matches(expected)
                .hasValidId()
                .assertAll();
    }

}
