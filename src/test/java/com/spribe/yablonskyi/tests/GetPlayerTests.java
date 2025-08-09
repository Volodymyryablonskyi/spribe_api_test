package com.spribe.yablonskyi.tests;

import com.spribe.yablonskyi.assertions.PlayerVerifier;
import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.pojo.GetPlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class GetPlayerTests extends BasePlayerTest {

   /* @Test(alwaysRun = true,
            description = "Verify that player can be retrieved by valid ID",
            groups = {"regression", "api", "player", "get-player", "get-player-positive"})
    @Description("Ensure that player can be retrieved using POST /player/get with valid ID")
    public void shouldGetPlayerByValidId() {
        PlayerRequestPojo expected = testData.generateValidPlayer();
        PlayerResponsePojo created = playersApiClient.createPlayer(SUPERVISOR, expected)
                .verifyStatus200()
                .asPojo(PlayerResponsePojo.class);
        createdPlayerId.set(created.getId());

        GetPlayerRequestPojo request = new GetPlayerRequestPojo().setPlayerId(created.getId());
        PlayerResponsePojo actual = playersApiClient.getPlayerById(request)
                .verifyStatus200()
                .asPojo(PlayerResponsePojo.class);

        PlayerVerifier.verifyThat(actual)
                .hasValidId()
                .matches(expected)
                .assertAll();
    }

    @Test(alwaysRun = true,
            description = "Verify that retrieving non-existing player returns 4XX",
            groups = {"regression", "api", "player", "get-player", "get-player-negative", "get-non-existing"})
    @Description("Ensure that POST /player/get with non-existing ID fails with 4XX")
    public void shouldNotGetNonExistingPlayer() {
        GetPlayerRequestPojo request = new GetPlayerRequestPojo().setPlayerId(Long.MAX_VALUE);
        playersApiClient.getPlayerById(request)
                .verifyStatusCodeIn(403, 404);
    }

    @Test(alwaysRun = true,
            description = "Verify that retrieving with ID = 0 fails",
            groups = {"regression", "api", "player", "get-player", "get-player-negative", "get-player-zero-id"})
    @Description("Ensure that POST /player/get with ID = 0 fails with 4XX")
    public void shouldNotGetPlayerWithZeroId() {
        GetPlayerRequestPojo request = new GetPlayerRequestPojo().setPlayerId(0L);
        playersApiClient.getPlayerById(request)
                .verifyStatusCodeIn(400, 403);
    }

    @Test(alwaysRun = true,
            description = "Verify that retrieving with negative ID fails",
            groups = {"regression", "api", "player", "get-player", "get-player-negative", "get-player-negative-id"})
    @Description("Ensure that POST /player/get with negative ID fails with 4XX")
    public void shouldNotGetPlayerWithNegativeId() {
        GetPlayerRequestPojo request = new GetPlayerRequestPojo().setPlayerId(-1L);
        playersApiClient.getPlayerById(request)
                .verifyStatusCodeIn(400, 403, 404);
    }

    @Test(alwaysRun = true,
            description = "Verify that empty body in getPlayer request fails with 400",
            groups = {"regression", "api", "player", "get-player", "get-player-negative", "get-empty-body"})
    @Description("Ensure that POST /player/get without body fails with 400 Bad Request")
    public void shouldReturn400WhenRequestBodyIsEmpty() {
        playersApiClient.getPlayerById(null)
                .verifyStatusCode(400);
    }*/


}