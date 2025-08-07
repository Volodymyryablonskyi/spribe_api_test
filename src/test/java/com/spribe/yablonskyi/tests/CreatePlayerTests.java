package com.spribe.yablonskyi.tests;

import com.spribe.yablonskyi.assertions.PlayerVerifier;
import com.spribe.yablonskyi.base.BaseTest;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import com.spribe.yablonskyi.pojo.CreatePlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import jdk.jfr.Description;
import org.testng.annotations.Test;

public class CreatePlayerTests extends BaseTest {

    @Test(alwaysRun = true,
            description = "Verify that a player can be created with valid data",
            groups = {"regression", "api", "player", "create-player", "create-player-positive", "create-player"})
    @Description("Ensure that a player can be created with valid data using given editor role")
    public void shouldCreatePlayerWithValidData() {
        CreatePlayerRequestPojo expected = testData.generateValidPlayer();
        ResponseWrapper response = playersApiClient.createPlayer(SUPERVISOR, expected);
        response.verify().statusCode200();
        PlayerResponsePojo actual = response.asPojo(PlayerResponsePojo.class);
        createdPlayerId.set(actual.getId());
        PlayerVerifier.verifyThat(actual)
                .hasValidId()
                .matches(expected)
                .assertAll();
    }

    @Test(alwaysRun = true,
            description = "Verify that player can be created without password",
            dataProvider = "supervisors",
            groups = {"regression", "api", "player", "create-player", "create-player-positive", "create-player-without-password"})
    @Description("Ensure that player can be created without password using given editor role")
    public void shouldCreatePlayerWithoutPassword() {
        CreatePlayerRequestPojo expected = testData.generateValidPlayer();
        expected.setPassword(null);
        ResponseWrapper response = playersApiClient.createPlayer(SUPERVISOR, expected);
        response.verify().statusCode200();
        PlayerResponsePojo actual = response.asPojo(PlayerResponsePojo.class);
        createdPlayerId.set(actual.getId());
        PlayerVerifier.verifyThat(actual)
                .hasValidId()
                .matches(expected)
                .assertAll();
    }

    @Test(alwaysRun = true,
            dataProvider = "edgeAges",
            description = "Verify that player can be created with boundary age values (18, 60)",
            groups = {"regression", "api", "player", "create-player", "create-player-positive",  "create-player-edge-age"})
    @Description("Ensure that player can be created with age 18 or 60 using given editor role")
    public void shouldCreatePlayerWithEdgeValidAge(String validAge) {
        CreatePlayerRequestPojo expected = testData.generateValidPlayer();
        expected.setAge(validAge);
        ResponseWrapper response = playersApiClient.createPlayer(SUPERVISOR, expected);
        response.verify().statusCode200();
        PlayerResponsePojo actual = response.asPojo(PlayerResponsePojo.class);
        createdPlayerId.set(actual.getId());
        PlayerVerifier.verifyThat(actual)
                .hasValidId()
                .matches(expected)
                .assertAll();
    }

    @Test(alwaysRun = true,
            dataProvider = "missingRequiredFields",
            description = "Verify that missing required fields return 4XX code",
            groups = {"regression", "api", "player", "create-player", "create-player-negative", "create-player-missing-req-field"})
    @Description("Ensure that missing required fields cause error response using given editor role")
    public void shouldReturnErrorWhenRequiredFieldMissing(String missingField) {
        CreatePlayerRequestPojo expected = testData.generateValidPlayer();
        try {
            var field = CreatePlayerRequestPojo.class.getDeclaredField(missingField);
            field.setAccessible(true);
            field.set(expected, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to nullify field: " + missingField, e);
        }
        playersApiClient.createPlayer(SUPERVISOR, expected)
                .verify()
                .statusCodeIsIn(400, 403);
    }

    @Test(alwaysRun = true,
            dataProvider = "invalidAges",
            description = "Verify that player cannot be created with invalid ages (e.g. 17, 0, 150)",
            groups = {"regression", "api", "player", "create-player", "create-player-negative", "create-player-invalid-age"})
    @Description("Ensure that player creation fails for invalid age values using given editor role")
    public void shouldNotCreatePlayerWithInvalidAge(String invalidAge) {
        CreatePlayerRequestPojo player = testData.generateValidPlayer();
        player.setAge(invalidAge);
        playersApiClient.createPlayer(SUPERVISOR, player)
                .verify()
                .statusCodeIsIn(400, 403);
    }

    @Test(alwaysRun = true,
            dataProvider = "invalidGenders",
            description = "Verify that player cannot be created with invalid gender values",
            groups = {"regression", "api", "player", "create-player", "create-player-negative", "create-player-invalid-gender"})
    @Description("Ensure that player creation fails for invalid gender values using given editor role")
    public void shouldNotCreatePlayerWithInvalidGender(String invalidGender) {
        CreatePlayerRequestPojo player = testData.generateValidPlayer();
        player.setGender(invalidGender);
        playersApiClient.createPlayer(SUPERVISOR, player)
                .verify()
                .statusCodeIsIn(400, 403);
    }

}
