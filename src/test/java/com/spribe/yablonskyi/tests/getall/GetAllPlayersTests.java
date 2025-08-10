package com.spribe.yablonskyi.tests.getall;

import com.spribe.yablonskyi.assertions.PlayerVerifier;
import com.spribe.yablonskyi.base.BasePlayerTest;
import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import com.spribe.yablonskyi.http.response.StatusCode;
import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import com.spribe.yablonskyi.pojo.PlayersListResponsePojo;
import com.spribe.yablonskyi.util.Sleep;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.stream.Collectors;

@Epic("Players Controller API")
@Feature("Get All Players")
@Story("Positive get-all scenarios")
public class GetAllPlayersTests extends BasePlayerTest {

    @Test(alwaysRun = true,
            groups = {"regression", "api", "api-players", "get-positive", "get-all"},
            description = "GET /player/get/all returns 200 OK")
    @Description("Smoke: endpoint is reachable and responds with 200 OK.")
    public void verifyGetAllReturnsOk() {
        playersApiClient.getAllPlayers()
                .verifyStatusCode(StatusCode._200_OK);
    }

    @Test(alwaysRun = true,
            groups = {"regression", "api", "api-players", "get-positive", "get-all"},
            description = "GET /player/get/all contains users created in this test")
    @Description("Create users, then wait until it appears in the get-all response and assert by id/screenName.")
    public void verifyGetAllContainsNewlyCreatedPlayers() {
        PlayerRequestPojo req = playersDataGenerator.get().generateValidPlayer(Role.USER.getLogin());
        ResponseWrapper resp = createAsAdmin(req);
        long id = resp.getId();
        PlayersListResponsePojo list = awaitGetAllContainsIds(20, 200, id);
        Assert.assertNotNull(list);
        PlayerResponsePojo fromList = list.getPlayers().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Created player id=" + id + " not found in /get/all"));
        PlayerVerifier.verifyMatches(req, fromList);
    }

    private PlayersListResponsePojo awaitGetAllContainsIds(int attempts, int sleepMs, long... ids) {
        for (int i = 0; i < attempts; i++) {
            ResponseWrapper resp = playersApiClient.getAllPlayers()
                    .verifyStatusCode(StatusCode._200_OK);
            PlayersListResponsePojo list = resp.asPojo(PlayersListResponsePojo.class);

            Set<Long> present = list.getPlayers().stream()
                    .map(PlayerResponsePojo::getId)
                    .collect(Collectors.toSet());

            boolean allPresent = true;
            for (long id : ids) {
                if (!present.contains(id)) {
                    allPresent = false;
                    break;
                }
            }
            if (allPresent) {
                return list;
            }
            Sleep.sleep(sleepMs);
        }
        Assert.fail("Created players did not appear in GET /player/get/all within timeout");
        return null;
    }
}

