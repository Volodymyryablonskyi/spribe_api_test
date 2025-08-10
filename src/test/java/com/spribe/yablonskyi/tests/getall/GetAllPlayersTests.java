package com.spribe.yablonskyi.tests.getall;

import com.spribe.yablonskyi.assertions.PlayersListVerifier;
import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import com.spribe.yablonskyi.http.response.StatusCode;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import com.spribe.yablonskyi.pojo.PlayersListResponsePojo;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Epic("Players Controller API")
@Feature("Get All Players")
@Story("Positive get-all scenarios")
public class GetAllPlayersTests extends BasePlayerTest {

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","get-positive","get-all"},
            description = "GET /player/get/all returns 200 OK")
    @Description("Smoke: endpoint is reachable and responds with 200 OK.")
    public void verifyGetAllReturnsOk() {
        playersApiClient.getAllPlayers()
                .verifyStatusCode(StatusCode._200_OK);
    }

    @Test(alwaysRun = true,
            groups = {"regression","api","api-players","get-positive","get-all"},
            description = "GET /player/get/all contains users created in this test")
    @Description("Create two users via SUPERVISOR, then assert their presence in the get-all response (by id/screenName).")
    public void verifyGetAllContainsNewlyCreatedPlayers() {
        PlayerResponsePojo admin = createUser(Role.ADMIN, ADMIN);
        PlayerResponsePojo user  = createUser(Role.USER, ADMIN);
        ResponseWrapper resp = playersApiClient.getAllPlayers()
                .verifyStatusCode(StatusCode._200_OK);
        var list = resp.asPojo(PlayersListResponsePojo.class);
        PlayersListVerifier.verifyThat(list)
                .containsId(admin.getId())
                .containsId(user.getId())
                .containsScreenName(admin.getScreenName())
                .containsScreenName(user.getScreenName())
                .assertAll();
    }

}
