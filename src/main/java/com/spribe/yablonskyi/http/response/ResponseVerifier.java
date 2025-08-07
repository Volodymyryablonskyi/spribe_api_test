package com.spribe.yablonskyi.http.response;

import com.spribe.yablonskyi.util.CustomLogger;
import org.testng.Assert;

import java.util.Arrays;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ResponseVerifier {

    private static final CustomLogger log = CustomLogger.getLogger(ResponseVerifier.class);

    private final ResponseWrapper responseWrapper;

    public ResponseVerifier(ResponseWrapper responseWrapper) {
        this.responseWrapper = responseWrapper;
    }

    public ResponseVerifier hasStatusCode(int expected) {
        log.info("Verify that Response status code is equal to - {}", expected);
        int actual = responseWrapper.statusCode();
        Assert.assertEquals(actual, expected, "Expected status code " + expected + " but got " + actual);
        return this;
    }

    public ResponseVerifier statusCode200() {
        return hasStatusCode(200);
    }

    public ResponseVerifier statusCode404() {
        return hasStatusCode(404);
    }

    public ResponseVerifier statusCode400() {
        return hasStatusCode(400);
    }

    public ResponseVerifier statusCodeIsIn(int... expectedStatusCodes) {
        int actual = responseWrapper.statusCode();
        log.info("Verify that Response status code is in {}", Arrays.toString(expectedStatusCodes));
        boolean found = Arrays.stream(expectedStatusCodes).anyMatch(code -> code == actual);
        Assert.assertTrue(found, "Status code " + actual + " is not in " + Arrays.toString(expectedStatusCodes));
        return this;
    }

    public ResponseVerifier verifyJsonFieldIsInstanceOf(String jsonPath, Class<?> clazz) {
        Object value = responseWrapper.getJsonPath().get(jsonPath);
        log.info("Verify that field '{}' is instance of '{}'", jsonPath, clazz.getSimpleName());
        Assert.assertTrue(clazz.isInstance(value),
                String.format("Field '%s' is not instance of %s. Actual type: %s",
                        jsonPath, clazz.getSimpleName(), value != null ? value.getClass().getSimpleName() : "null"));
        return this;
    }

    public <T> ResponseVerifier verifyBodyEqualsToPojo(Class<T> clazz, T expected) {
        log.info("Verify that Response body equals expected POJO: {}", expected);
        T actual = responseWrapper.asPojo(clazz);
        Assert.assertEquals(actual, expected, "Response body does not match expected POJO");
        return this;
    }

}
