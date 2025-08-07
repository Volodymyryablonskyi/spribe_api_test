package com.spribe.yablonskyi.tests;

import com.spribe.yablonskyi.assertions.PlayerVerifier;
import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

public class GetAllPlayersTests extends BasePlayerTest {

    @Test(alwaysRun = true,
            description = "Verify that GET /player/get/all returns 200 status",
            groups = {"regression", "api", "player", "get-all-players", "positive"})
    @Description("Ensure that GET /player/get/all returns 200 status")
    public void shouldReturn200WhenGettingAllPlayers() {
        playersApiClient.getAllPlayers()
                .verifyStatus200();
    }

    @Test(alwaysRun = true,
            description = "Verify that created player is returned in the list",
            groups = {"regression", "api", "player", "get-all-players", "positive"})
    @Description("Ensure that newly created player appears in the response list")
    public void shouldReturnCreatedPlayerInList() {
        PlayerRequestPojo expected = testData.generateValidPlayer();
        PlayerResponsePojo actual = playersApiClient.createPlayer(SUPERVISOR, expected)
                .verifyStatus200()
                .asPojo(PlayerResponsePojo.class);
        createdPlayerId.set(actual.getId());

        List<PlayerResponsePojo> allPlayers = playersApiClient.getAllPlayers()
                .verifyStatus200()
                .asListOfPojo(PlayerResponsePojo.class);

        PlayerResponsePojo found = allPlayers.stream()
                .filter(p -> p.getId() == actual.getId())
                .findFirst()
                .orElse(null);

        assertNotNull(found, "Expected created player to be in the list");

        PlayerVerifier.verifyThat(found)
                .matches(expected)
                .hasValidId()
                .assertAll();
    }

    @Test(alwaysRun = true,
            description = "Verify that players list is not empty after creation",
            groups = {"regression", "api", "player", "get-all-players", "positive"})
    @Description("Ensure that player list is not empty if player was created before")
    public void shouldReturnNonEmptyList() {
        PlayerRequestPojo expected = testData.generateValidPlayer();
        PlayerResponsePojo actual = playersApiClient.createPlayer(SUPERVISOR, expected)
                .verifyStatus200()
                .asPojo(PlayerResponsePojo.class);
        createdPlayerId.set(actual.getId());

        List<PlayerResponsePojo> allPlayers = playersApiClient.getAllPlayers()
                .verifyStatus200()
                .asListOfPojo(PlayerResponsePojo.class);

        assertNotNull(allPlayers, "List should not be null");
        assertFalse(allPlayers.isEmpty(), "List should not be empty");
    }

}
