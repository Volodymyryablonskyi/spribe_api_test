package com.spribe.yablonskyi.tests;

import com.spribe.yablonskyi.base.BaseTest;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import com.spribe.yablonskyi.pojo.CreatePlayerRequestPojo;
import com.spribe.yablonskyi.pojo.DeletePlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class DeletePlayerTests extends BaseTest {

    @Test(alwaysRun = true,
            dataProvider = "editors",
            description = "Verify that a player can be deleted by admin or supervisor",
            groups = {"regression", "api", "player", "delete-player", "delete-player-positive", "delete-existing-player"})
    @Description("Ensure that a player can be deleted by either admin or supervisor")
    public void shouldDeletePlayerWithValidId(String editor) {
        CreatePlayerRequestPojo createRequest = testData.generateValidPlayer();
        ResponseWrapper createResponse = playersApiClient.createPlayer(editor, createRequest);
        createResponse.verify().statusCode200();
        PlayerResponsePojo createdPlayer = createResponse.asPojo(PlayerResponsePojo.class);
        long playerId = createdPlayer.getId();
        createdPlayerId.set(playerId);
        DeletePlayerRequestPojo deleteRequest = new DeletePlayerRequestPojo().setPlayerId(playerId);
        playersApiClient.deletePlayer(editor, deleteRequest)
                .verify()
                .statusCode200();
    }

    @Test(alwaysRun = true,
            dataProvider = "editors",
            description = "Verify that player deletion with negative ID returns 4XX",
            groups = {"regression", "api", "player", "delete-player", "delete-player-negative", "delete-player-negative-id"})
    @Description("Ensure that deletion of player with invalid (negative) ID returns 4XX")
    public void shouldNotDeletePlayerWithNegativeId(String editor) {
        DeletePlayerRequestPojo deleteRequest = new DeletePlayerRequestPojo().setPlayerId(-1L);
        playersApiClient.deletePlayer(editor, deleteRequest)
                .verify()
                .statusCodeIsIn(400, 403, 404);
    }

    @Test(alwaysRun = true,
            dataProvider = "editors",
            description = "Verify that re-deleting an already deleted player returns 4XX",
            groups = {"regression", "api", "player", "delete-player", "delete-player-negative", "delete-not-existing-player"})
    @Description("Ensure that attempting to delete an already deleted player returns 4XX")
    public void shouldNotDeleteAlreadyDeletedPlayer(String editor) {
        CreatePlayerRequestPojo createRequest = testData.generateValidPlayer();
        ResponseWrapper createResponse = playersApiClient.createPlayer(editor, createRequest);
        createResponse.verify().statusCode200();
        PlayerResponsePojo createdPlayer = createResponse.asPojo(PlayerResponsePojo.class);
        long playerId = createdPlayer.getId();
        DeletePlayerRequestPojo deleteRequest = new DeletePlayerRequestPojo().setPlayerId(playerId);
        playersApiClient.deletePlayer(editor, deleteRequest)
                .verify()
                .statusCode200();
        playersApiClient.deletePlayer(editor, deleteRequest)
                .verify()
                .statusCodeIsIn(403, 404);
    }

    @Test(alwaysRun = true,
            dataProvider = "editors",
            description = "Verify that deletion with 0 ID returns 4XX",
            groups = {"regression", "api", "player", "delete-player", "delete-player-negative", "delete-player-zero-id"})
    @Description("Ensure that deletion of player without 0 Id fails")
    public void shouldNotDeletePlayerWithoutId(String editor) {
        DeletePlayerRequestPojo deleteRequest = new DeletePlayerRequestPojo().setPlayerId(0);
        playersApiClient.deletePlayer(editor, deleteRequest)
                .verify()
                .statusCodeIsIn(400, 403);
    }


}
