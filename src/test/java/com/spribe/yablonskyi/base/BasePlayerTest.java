package com.spribe.yablonskyi.base;

import com.spribe.yablonskyi.clients.PlayersApiClient;
import org.testng.annotations.BeforeMethod;

public class BasePlayerTest extends BaseTest {

    protected PlayersApiClient playersApiClient;

    @BeforeMethod(alwaysRun = true)
    @Override
    public void setUp() {
        super.setUp();
        playersApiClient = new PlayersApiClient(spec);
    }

}