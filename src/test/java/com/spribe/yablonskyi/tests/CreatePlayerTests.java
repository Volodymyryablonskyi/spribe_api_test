package com.spribe.yablonskyi.tests;

import com.spribe.yablonskyi.assertions.PlayerVerifier;
import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import jdk.jfr.Description;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Objects;

public class CreatePlayerTests extends BasePlayerTest {

    @DataProvider(name = "createAccess", parallel = true)
    public Object[][] createAccess() {
        return new Object[][]{
                {Role.SUPERVISOR, Role.USER, 200},
                {Role.SUPERVISOR, Role.ADMIN, 200},
                {Role.ADMIN, Role.USER, 200},
                {Role.ADMIN, Role.ADMIN, 200},
                {Role.USER, Role.USER, 403},
                {Role.USER, Role.ADMIN, 403},
                {null, Role.USER, 401}
        };
    }

    @Test(alwaysRun = true,
            dataProvider = "createAccess",
            description = "RBAC: different editor roles creating different targets",
            groups = {"regression", "api", "player", "create-player", "create-player-access"})
    @Description("Ensure that player creation is allowed/denied depending on editor and target roles")
    public void shouldHandleCreateAccess(Role editor, Role target, int expectedStatus) {
        PlayerRequestPojo createRequest = testData.generateValidPlayer(target.name().toLowerCase());
        String editorLogin = Objects.isNull(editor) ? testData.getInvalidRole() :  editor.getLogin();
        ResponseWrapper response = playersApiClient.createPlayer(editorLogin, createRequest)
                .verifyStatusCode(expectedStatus);

        if (expectedStatus == 200) {
            PlayerResponsePojo createResponse = response.asPojo(PlayerResponsePojo.class);
            long id = createResponse.getId();
            createdPlayerId.set(id);
            PlayerVerifier.verifyThat(createResponse)
                    .hasValidId()
                    .matches(createRequest)
                    .assertAll();
            PlayerResponsePojo actual = playersApiClient.getPlayerById(id)
                    .verifyStatus200()
                    .asPojo(PlayerResponsePojo.class);
            PlayerVerifier.verifyThat(actual)
                    .hasValidId()
                    .matches(createRequest)
                    .assertAll();
        } else {
            if (response.getId() != null) {
                playersApiClient.getPlayerById(response.getId())
                        .verifyStatusCodeIn(400, 404);
            }
        }
    }


}


/*



    @Test(alwaysRun = true,
            description = "Verify that a player can be created with valid data",
            groups = {"regression", "api", "player", "create-player", "create-player-positive", "create-player"})
    @Description("Ensure that a player can be created with valid data using given editor role")
    public void shouldCreatePlayerWithValidData() {
        PlayerRequestPojo expected = testData.generateValidPlayer();
        PlayerResponsePojo actual =  playersApiClient.createPlayer(SUPERVISOR, expected)
                .verifyStatus200()
                .asPojo(PlayerResponsePojo.class);
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
        PlayerRequestPojo expected = testData.generateValidPlayer()
                .setPassword(null);
        PlayerResponsePojo actual = playersApiClient.createPlayer(SUPERVISOR, expected)
                .verifyStatus200()
                .asPojo(PlayerResponsePojo.class);
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
        PlayerRequestPojo expected = testData.generateValidPlayer()
                .setAge(validAge);
        PlayerResponsePojo actual = playersApiClient.createPlayer(SUPERVISOR, expected)
                .verifyStatus200()
                .asPojo(PlayerResponsePojo.class);
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
        PlayerRequestPojo expected = testData.generateValidPlayer();
        try {
            var field = PlayerRequestPojo.class.getDeclaredField(missingField);
            field.setAccessible(true);
            field.set(expected, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to nullify field: " + missingField, e);
        }
        playersApiClient.createPlayer(SUPERVISOR, expected)
                .verifyStatus403();
    }

    @Test(alwaysRun = true,
            dataProvider = "invalidAges",
            description = "Verify that player cannot be created with invalid ages (e.g. 17, 0, 150)",
            groups = {"regression", "api", "player", "create-player", "create-player-negative", "create-player-invalid-age"})
    @Description("Ensure that player creation fails for invalid age values using given editor role")
    public void shouldNotCreatePlayerWithInvalidAge(String invalidAge) {
        PlayerRequestPojo player = testData.generateValidPlayer()
                .setAge(invalidAge);
        playersApiClient.createPlayer(SUPERVISOR, player)
                .verifyStatus403();
    }

    @Test(alwaysRun = true,
            dataProvider = "invalidGenders",
            description = "Verify that player cannot be created with invalid gender values",
            groups = {"regression", "api", "player", "create-player", "create-player-negative", "create-player-invalid-gender"})
    @Description("Ensure that player creation fails for invalid gender values using given editor role")
    public void shouldNotCreatePlayerWithInvalidGender(String invalidGender) {
        PlayerRequestPojo player = testData.generateValidPlayer()
                .setGender(invalidGender);
        playersApiClient.createPlayer(SUPERVISOR, player)
                .verifyStatusCode(403);
    }
*/


