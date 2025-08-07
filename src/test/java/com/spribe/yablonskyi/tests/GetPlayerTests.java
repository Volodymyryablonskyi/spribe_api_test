package com.spribe.yablonskyi.tests;

import com.spribe.yablonskyi.assertions.PlayerVerifier;
import com.spribe.yablonskyi.base.BaseTest;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import com.spribe.yablonskyi.pojo.CreatePlayerRequestPojo;
import com.spribe.yablonskyi.pojo.GetPlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class GetPlayerTests extends BaseTest {

    @Test(alwaysRun = true,
            description = "Verify that player can be retrieved by valid ID",
            groups = {"regression", "api", "player", "get-player", "get-player-positive"})
    @Description("Ensure that player can be retrieved using POST /player/get with valid ID")
    public void shouldGetPlayerByValidId() {
        CreatePlayerRequestPojo createRequest = testData.generateValidPlayer();
        ResponseWrapper createResponse = playersApiClient.createPlayer(SUPERVISOR, createRequest);
        createResponse.verify().statusCode200();

        PlayerResponsePojo createdPlayer = createResponse.asPojo(PlayerResponsePojo.class);
        createdPlayerId.set(createdPlayer.getId());

        GetPlayerRequestPojo getRequest = new GetPlayerRequestPojo().setPlayerId(createdPlayer.getId());
        ResponseWrapper getResponse = playersApiClient.getPlayerById( getRequest);
        getResponse.verify().statusCode200();

        PlayerResponsePojo actual = getResponse.asPojo(PlayerResponsePojo.class);
        PlayerVerifier.verifyThat(actual)
                .hasValidId()
                .matches(createRequest)
                .assertAll();
    }

    @Test(alwaysRun = true,
            description = "Verify that retrieving non-existing player returns 4XX",
            groups = {"regression", "api", "player", "get-player", "get-player-negative", "get-non-existing"})
    @Description("Ensure that GET /player/get with non-existing ID fails with 4XX")
    public void shouldNotGetNonExistingPlayer() {
        long nonExistingId = Long.MAX_VALUE;
        GetPlayerRequestPojo getRequest = new GetPlayerRequestPojo().setPlayerId(nonExistingId);
        playersApiClient.getPlayerById( getRequest)
                .verify()
                .statusCodeIsIn(403, 404);
    }

    @Test(alwaysRun = true,
            description = "Verify that retrieving with ID = 0 fails",
            groups = {"regression", "api", "player", "get-player", "get-player-negative", "get-player-zero-id"})
    @Description("Ensure that GET /player/get with ID = 0 fails with 4XX")
    public void shouldNotGetPlayerWithZeroId() {
        GetPlayerRequestPojo getRequest = new GetPlayerRequestPojo().setPlayerId(0L);
        playersApiClient.getPlayerById(getRequest)
                .verify()
                .statusCodeIsIn(400, 403);
    }

    @Test(alwaysRun = true,
            description = "Verify that retrieving with negative ID fails",
            groups = {"regression", "api", "player", "get-player", "get-player-negative", "get-player-negative-id"})
    @Description("Ensure that GET /player/get with negative ID fails with 4XX")
    public void shouldNotGetPlayerWithNegativeId() {
        GetPlayerRequestPojo getRequest = new GetPlayerRequestPojo().setPlayerId(-1L);
        playersApiClient.getPlayerById(getRequest)
                .verify()
                .statusCodeIsIn(400, 403, 404);
    }


}