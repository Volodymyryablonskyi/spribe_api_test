package com.spribe.yablonskyi.base;

import com.spribe.yablonskyi.clients.PlayersApiClient;
import com.spribe.yablonskyi.pojo.DeletePlayerRequestPojo;
import com.spribe.yablonskyi.testdata.PlayerTestData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.util.Objects;

public class BasePlayerTest extends BaseTest {

    protected static final String SUPERVISOR = "supervisor";
    protected static final ThreadLocal<Long> createdPlayerId = new ThreadLocal<>();
    protected PlayersApiClient playersApiClient;
    protected PlayerTestData testData;

    @BeforeClass(alwaysRun = true)
    public void initContext() {
        this.playersApiClient = new PlayersApiClient(spec);
        this.testData = new PlayerTestData();
    }


    @AfterMethod(alwaysRun = true)
    public void deleteCreatedPlayer() {
        Long playerId = createdPlayerId.get();
        if (!Objects.isNull(playerId)) {
            try {
                DeletePlayerRequestPojo deletePlayerRequest = new DeletePlayerRequestPojo().setPlayerId(playerId);
                playersApiClient.deletePlayer(SUPERVISOR, deletePlayerRequest);
            } catch (Exception e) {
                System.err.println("❌ Failed to delete player with ID: " + playerId + " → " + e.getMessage());
            } finally {
                createdPlayerId.remove();
            }
        }
    }

    @DataProvider(name = "editors", parallel = true)
    public Object[][] editors() {
        return new Object[][]{{"supervisor"}, {"admin"}};
    }

    @DataProvider(name = "missingRequiredFields", parallel = true)
    public Object[][] missingRequiredFields() {
        return new Object[][]{{"age"}, {"gender"}, {"login"}, {"role"}, {"screenName"}};
    }

    @DataProvider(name = "invalidAges", parallel = true)
    public Object[][] invalidAges() {
        return new Object[][]{{"17"}, {"61"}, {"0"}, {"-5"}, {"150"}};
    }

    @DataProvider(name = "edgeAges", parallel = true)
    public Object[][] edgeAges() {
        return new Object[][]{{"18"}, {"60"}};
    }

    @DataProvider(name = "invalidGenders")
    public Object[][] invalidGenders() {
        return new Object[][]{{""}, {" "}, {"ale"}, {"123"}, {"!@#"}, {"UnsupportedGender"}};
    }



}
