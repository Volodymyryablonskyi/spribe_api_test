package com.spribe.yablonskyi.assertions;

import com.spribe.yablonskyi.http.response.StatusCode;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Assertions {

    public static void assertCreated(StatusCode actual) {
        assertTrue(actual == StatusCode._200_OK || actual == StatusCode._201_CREATED,
                "Expected 200/201 but got: " + actual);
    }

    public static void assertOk(StatusCode actual) {
        assertEquals(actual, StatusCode._200_OK, "Expected 200 OK but got: " + actual);
    }

    public static void assertNoContent(StatusCode actual) {
        assertEquals(actual, StatusCode._204_NO_CONTENT, "Expected 204 NO_CONTENT but got: " + actual);
    }

    public static void assertBadRequest(StatusCode actual) {
        assertEquals(actual, StatusCode._400_BAD_REQUEST, "Expected 400 BAD_REQUEST but got: " + actual);
    }

    public static void assertUnauthorized(StatusCode actual) {
        assertEquals(actual, StatusCode._401_UNAUTHORIZED, "Expected 401 UNAUTHORIZED but got: " + actual);
    }

    public static void assertForbidden(StatusCode actual) {
        assertEquals(actual, StatusCode._403_FORBIDDEN, "Expected 403 FORBIDDEN but got: " + actual);
    }

    public static void assertNotFound(StatusCode actual) {
        assertEquals(actual, StatusCode._404_NOT_FOUND, "Expected 404 NOT_FOUND but got: " + actual);
    }

    public static void assertConflict(StatusCode actual) {
        assertEquals(actual, StatusCode._409_CONFLICT, "Expected 409 CONFLICT but got: " + actual);
    }

    /** Уніфікована перевірка: очікуваний == фактичний (для дата-провайдерів) */
    public static void assertStatusEquals(StatusCode expected, StatusCode actual, String message) {
        assertEquals(actual, expected, message);
    }
}
