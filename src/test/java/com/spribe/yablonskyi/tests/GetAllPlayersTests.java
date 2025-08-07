package com.spribe.yablonskyi.tests;

import com.spribe.yablonskyi.base.BaseTest;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class GetAllPlayersTests extends BaseTest {

    @Test(alwaysRun = true,
            description = "Verify that all players are returned with 200 OK",
            groups = {"regression", "api", "player", "get-all-players", "positive"})
    @Description("Ensure that GET /player/get/all returns players list with 200 status")
    public void shouldReturnAllPlayersWithStatus200() {
        playersApiClient.getAllPlayers()
                .verify()
                .statusCode200();
    }


}
