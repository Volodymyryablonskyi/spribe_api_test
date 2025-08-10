package com.spribe.yablonskyi.base;

import com.spribe.yablonskyi.config.RestAssuredConfigurator;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected RequestSpecification spec;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        RestAssuredConfigurator.configure();
        this.spec = RestAssuredConfigurator.getSpec();
    }

}