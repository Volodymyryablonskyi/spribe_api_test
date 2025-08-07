package com.spribe.yablonskyi.base;

import com.spribe.yablonskyi.clients.PlayersApiClient;
import com.spribe.yablonskyi.config.RestAssuredConfigurator;
import com.spribe.yablonskyi.testdata.PlayerTestData;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class BaseTest {


    private static final ThreadLocal<Boolean> isRerun = ThreadLocal.withInitial(() -> Boolean.FALSE);
    protected RequestSpecification spec;


    @BeforeClass(alwaysRun = true)
    public void setUp() {
        RestAssuredConfigurator.configure();
        this.spec = RestAssuredConfigurator.getSpec();
    }

    public static boolean getRerun() {
        return isRerun.get();
    }

    public static void setRerun(boolean rerun) {
        isRerun.set(rerun);
    }


}