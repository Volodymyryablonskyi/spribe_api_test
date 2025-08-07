package com.spribe.yablonskyi.tests;

import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.config.ApplicationConfig;
import com.spribe.yablonskyi.pojo.PlayerRequest;
import com.spribe.yablonskyi.testdata.CreatePlayerTestData;
import jdk.jfr.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CreatePlayerTests extends BasePlayerTest {

    private CreatePlayerTestData testData;

    @BeforeMethod(alwaysRun = true)
    @Override
    public void setUp() {
        super.setUp();
        testData = new CreatePlayerTestData();
    }

    @Test(alwaysRun = true,
            description = "Verify that a player can be created with valid data",
            groups = {"regression", "api", "player", "player-positive", "create-player"},
            priority = 1)
    @Description("Ensure that the GET /player/create/{editor} endpoint creates a player with valid request data")
    public void checkCreatePlayerWithValidData() {
        PlayerRequest request = testData.generateValidPlayer();
        playersApiClient.createPlayer(ApplicationConfig.getEditor(), request);

    }


}

