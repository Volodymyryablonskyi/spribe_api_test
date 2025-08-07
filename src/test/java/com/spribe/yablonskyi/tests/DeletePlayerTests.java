package com.spribe.yablonskyi.tests;

import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.pojo.DeletePlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class DeletePlayerTests extends BasePlayerTest {

    @Test(alwaysRun = true,
            dataProvider = "editors",
            description = "Verify that a player can be deleted by admin or supervisor",
            groups = {"regression", "api", "player", "delete-player", "delete-player-positive", "delete-existing-player"})
    @Description("Ensure that a player can be deleted by either admin or supervisor")
    public void shouldDeletePlayerWithValidId(String editor) {
        PlayerRequestPojo createRequest = testData.generateValidPlayer();
        PlayerResponsePojo created = playersApiClient.createPlayer(editor, createRequest)
                .verifyStatus200()
                .asPojo(PlayerResponsePojo.class);
        createdPlayerId.set(created.getId());

        DeletePlayerRequestPojo deleteRequest = new DeletePlayerRequestPojo().setPlayerId(created.getId());
        playersApiClient.deletePlayer(editor, deleteRequest)
                .verifyStatus200();
    }

    @Test(alwaysRun = true,
            dataProvider = "editors",
            description = "Verify that deletion with negative ID returns 4XX",
            groups = {"regression", "api", "player", "delete-player", "delete-player-negative", "delete-player-negative-id"})
    @Description("Ensure that deletion of player with invalid (negative) ID returns 4XX")
    public void shouldNotDeletePlayerWithNegativeId(String editor) {
        DeletePlayerRequestPojo deleteRequest = new DeletePlayerRequestPojo().setPlayerId(-1L);
        playersApiClient.deletePlayer(editor, deleteRequest)
                .verifyStatusCodeIn(400, 403, 404);
    }

    @Test(alwaysRun = true,
            dataProvider = "editors",
            description = "Verify that deletion with 0 ID returns 4XX",
            groups = {"regression", "api", "player", "delete-player", "delete-player-negative", "delete-player-zero-id"})
    @Description("Ensure that deletion of player with ID 0 fails")
    public void shouldNotDeletePlayerWithZeroId(String editor) {
        DeletePlayerRequestPojo deleteRequest = new DeletePlayerRequestPojo().setPlayerId(0L);
        playersApiClient.deletePlayer(editor, deleteRequest)
                .verifyStatusCodeIn(400, 403);
    }

    @Test(alwaysRun = true,
            dataProvider = "editors",
            description = "Verify that deleting already deleted player returns 4XX",
            groups = {"regression", "api", "player", "delete-player", "delete-player-negative", "delete-not-existing-player"})
    @Description("Ensure that attempting to delete already deleted player returns 4XX")
    public void shouldNotDeleteAlreadyDeletedPlayer(String editor) {
        PlayerRequestPojo createRequest = testData.generateValidPlayer();
        PlayerResponsePojo created = playersApiClient.createPlayer(editor, createRequest)
                .verifyStatus200()
                .asPojo(PlayerResponsePojo.class);

        DeletePlayerRequestPojo deleteRequest = new DeletePlayerRequestPojo().setPlayerId(created.getId());

        playersApiClient.deletePlayer(editor, deleteRequest)
                .verifyStatus200();

        playersApiClient.deletePlayer(editor, deleteRequest)
                .verifyStatusCodeIn(403, 404);
    }

    @Test(alwaysRun = true,
            dataProvider = "editors",
            description = "Verify that sending empty body to delete endpoint returns 400",
            groups = {"regression", "api", "player", "delete-player", "delete-player-negative", "delete-empty-body"})
    @Description("Ensure that sending empty request body to DELETE /player/delete/{editor} returns 400")
    public void shouldReturn400WhenRequestBodyIsEmpty(String editor) {
        playersApiClient.deletePlayer(editor, null)
                .verifyStatusCode(400);
    }

}
