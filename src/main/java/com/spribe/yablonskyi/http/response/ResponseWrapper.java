package com.spribe.yablonskyi.http.response;

import com.spribe.yablonskyi.util.CustomLogger;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

public class ResponseWrapper {

    private static final CustomLogger log = CustomLogger.getLogger(ResponseWrapper.class);

    public ResponseWrapper() {}

    public ResponseWrapper(Response response) {
        this.response = response;
    }

    private Response response;

    public Response response() {
        return response;
    }

    public String asString() {
        return response.thenReturn().asString();
    }

    public JsonPath getJsonPath() {
        return response.jsonPath();
    }


    public <T> T getJsonValue(String jsonPath, Class<T> clazz) {
        return getJsonPath().getObject(jsonPath, clazz);
    }

    public <T> T asPojo(Class<T> clz) {
        return response.as(clz);
    }

    public <T> List<T> asListOfPojo(Class<T> clz) {
        return response.jsonPath().getList("$", clz);
    }

    public Long getId() {
        return getJsonValue("id", Long.class);
    }

    public String getBodyAsString() {
        return response.body().asString();
    }

    public StatusCode statusCode() {
        return StatusCode.parseToCode(getStatusCodeInt());
    }

    public int getStatusCodeInt() {
        return response.getStatusCode();
    }

    public String statusLine() {
        return response.getStatusLine();
    }

    public static ResponseWrapper of(Response response) {
        if (response == null) return ResponseWrapper.empty();
        ResponseWrapper responseWrapper = new ResponseWrapper(response);
        log.logResponse(responseWrapper);
        return responseWrapper;
    }

    public static ResponseWrapper empty() {
        return new ResponseWrapper(null);
    }


    public ResponseWrapper verifyStatusCode(StatusCode expected, String error)  {
        log.info("Verify that Response status code is equal to - {}", expected);
        StatusCode actual = statusCode();
        Assert.assertEquals(actual, expected, error + " " + expected + " but got " + actual);
        return this;
    }

    public ResponseWrapper verifyStatusCodeIn(StatusCode... expectedStatusCodes) {
        StatusCode actual = statusCode();
        log.info("Verify that Response status code is in {}", Arrays.toString(expectedStatusCodes));
        boolean found = Arrays.asList(expectedStatusCodes).contains(actual);
        Assert.assertTrue(found, "Status code " + actual + " is not in " + Arrays.toString(expectedStatusCodes));
        return this;
    }

}