package com.spribe.yablonskyi.http.response;

import com.spribe.yablonskyi.util.CustomLogger;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

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

    public <T> T asPojo(Class<T> clz) {
        return response.as(clz);
    }

    public <T> List<T> asListOfPojo(Class<T> clz) {
        return response.jsonPath().getList("$", clz);
    }

    public String getBodyAsString() {
        return response.body().asString();
    }

    public int statusCode() {
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


}