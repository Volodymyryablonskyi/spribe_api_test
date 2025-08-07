package com.spribe.yablonskyi.tests;

import com.spribe.yablonskyi.assertions.PlayerVerifier;
import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class UpdatePlayerTests extends BasePlayerTest {

    @Test(alwaysRun = true,
            dataProvider = "editors",
            description = "Verify that player can be updated with valid data",
            groups = {"regression", "api", "player", "update-player", "update-player-positive"})
    @Description("Ensure that PATCH /player/update/{editor}/{id} works with valid update request")
    public void shouldUpdatePlayerWithValidData(String editor) {
        PlayerRequestPojo createRequest = testData.generateValidPlayer();
        PlayerResponsePojo created = playersApiClient.createPlayer(editor, createRequest)
                .verifyStatus200()
                .asPojo(PlayerResponsePojo.class);
        createdPlayerId.set(created.getId());

        PlayerRequestPojo updateRequest = testData.generateValidPlayer()
                .setLogin(created.getLogin())
                .setPassword(created.getPassword());

        PlayerResponsePojo updated = playersApiClient.updatePlayer(editor, created.getId(), updateRequest)
                .verifyStatus200()
                .asPojo(PlayerResponsePojo.class);

        PlayerVerifier.verifyThat(updated)
                .hasValidId()
                .matches(updateRequest)
                .assertAll();
    }

    @Test(alwaysRun = true,
            description = "Verify that updating non-existing player returns 204",
            groups = {"regression", "api", "player", "update-player", "update-player-negative", "update-non-existing"})
    @Description("Ensure that PATCH /player/update/{editor}/{nonExistingId} returns 204 No Content")
    public void shouldReturn204ForNonExistingPlayer() {
        PlayerRequestPojo updateRequest = testData.generateValidPlayer();
        playersApiClient.updatePlayer(SUPERVISOR, Long.MAX_VALUE, updateRequest)
                .verifyStatusCode(204);
    }

    @Test(alwaysRun = true,
            dataProvider = "missingRequiredFields",
            description = "Verify that updating with missing required field returns 4XX",
            groups = {"regression", "api", "player", "update-player", "update-player-negative", "update-missing-field"})
    @Description("Ensure that PATCH /player/update fails when required field is missing")
    public void shouldReturnErrorWhenRequiredFieldMissing(String missingField) {
        PlayerRequestPojo createRequest = testData.generateValidPlayer();
        PlayerResponsePojo created = playersApiClient.createPlayer(SUPERVISOR, createRequest)
                .verifyStatus200()
                .asPojo(PlayerResponsePojo.class);
        createdPlayerId.set(created.getId());

        PlayerRequestPojo updateRequest = testData.generateValidPlayer()
                .setLogin(createRequest.getLogin())
                .setPassword(createRequest.getPassword());

        try {
            var field = PlayerRequestPojo.class.getDeclaredField(missingField);
            field.setAccessible(true);
            field.set(updateRequest, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to nullify field: " + missingField, e);
        }

        playersApiClient.updatePlayer(SUPERVISOR, created.getId(), updateRequest)
                .verifyStatusCodeIn(400, 403);
    }

    @Test(alwaysRun = true,
            dataProvider = "invalidAges",
            description = "Verify that updating with invalid age returns 4XX",
            groups = {"regression", "api", "player", "update-player", "update-player-negative", "update-invalid-age"})
    @Description("Ensure that PATCH /player/update fails with invalid age")
    public void shouldNotUpdatePlayerWithInvalidAge(String invalidAge) {
        PlayerRequestPojo createRequest = testData.generateValidPlayer();
        PlayerResponsePojo created = playersApiClient.createPlayer(SUPERVISOR, createRequest)
                .verifyStatus200()
                .asPojo(PlayerResponsePojo.class);
        createdPlayerId.set(created.getId());

        PlayerRequestPojo updateRequest = testData.generateValidPlayer()
                .setAge(invalidAge)
                .setLogin(createRequest.getLogin())
                .setPassword(createRequest.getPassword());

        playersApiClient.updatePlayer(SUPERVISOR, created.getId(), updateRequest)
                .verifyStatusCodeIn(400, 403);
    }

    @Test(alwaysRun = true,
            dataProvider = "invalidGenders",
            description = "Verify that updating with invalid gender returns 4XX",
            groups = {"regression", "api", "player", "update-player", "update-player-negative", "update-invalid-gender"})
    @Description("Ensure that PATCH /player/update fails with invalid gender")
    public void shouldNotUpdatePlayerWithInvalidGender(String invalidGender) {
        PlayerRequestPojo createRequest = testData.generateValidPlayer();
        PlayerResponsePojo created = playersApiClient.createPlayer(SUPERVISOR, createRequest)
                .verifyStatus200()
                .asPojo(PlayerResponsePojo.class);
        createdPlayerId.set(created.getId());

        PlayerRequestPojo updateRequest = testData.generateValidPlayer()
                .setGender(invalidGender)
                .setLogin(createRequest.getLogin())
                .setPassword(createRequest.getPassword());

        playersApiClient.updatePlayer(SUPERVISOR, created.getId(), updateRequest)
                .verifyStatusCodeIn(400, 403);
    }


    @Test(alwaysRun = true,
            description = "Verify that PATCH fails when editor has no permission",
            groups = {"regression", "api", "player", "update-player", "update-player-negative", "update-forbidden"})
    @Description("Ensure that PATCH /player/update returns 403 when editor is unauthorized")
    public void shouldReturn403WhenEditorForbidden() {
        PlayerRequestPojo createRequest = testData.generateValidPlayer();
        PlayerResponsePojo created = playersApiClient.createPlayer(SUPERVISOR, createRequest)
                .verifyStatus200()
                .asPojo(PlayerResponsePojo.class);
        createdPlayerId.set(created.getId());

        PlayerRequestPojo updateRequest = testData.generateValidPlayer()
                .setLogin(createRequest.getLogin())
                .setPassword(createRequest.getPassword());

        playersApiClient.updatePlayer("user", created.getId(), updateRequest)
                .verifyStatusCode(403);
    }



}
