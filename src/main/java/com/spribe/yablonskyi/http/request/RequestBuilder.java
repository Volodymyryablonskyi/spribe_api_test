package com.spribe.yablonskyi.http.request;

import com.spribe.yablonskyi.util.CustomLogger;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestBuilder {

    private final CustomLogger log = CustomLogger.getLogger(RequestBuilder.class);

    private final RequestSpecification spec;
    private final Map<String, String> queryParams = new HashMap<>();
    private final Map<String, String> headers = new HashMap<>();
    private String baseUri;
    private HttpMethods method;
    private String path;
    private Object body;

    public RequestBuilder(RequestSpecification spec, String baseUri) {
        this.spec = spec;
        this.baseUri = baseUri;
    }

    public RequestBuilder withMethod(HttpMethods method) {
        this.method = method;
        return this;
    }

    public RequestBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public RequestBuilder withBody(Object body) {
        this.body = body;
        return this;
    }

    public RequestBuilder withQueryParam(String key, String value) {
        this.queryParams.put(key, value);
        return this;
    }

    public RequestBuilder withQueryParams(Map<String, String> queryParams) {
        if (!Objects.isNull(queryParams)) {
            this.queryParams.putAll(queryParams);
        }
        return this;
    }

    public RequestBuilder withHeader(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public RequestBuilder withHeaders(Map<String, String> headers) {
        if (!Objects.isNull(headers)) {
            this.headers.putAll(headers);
        }
        return this;
    }

    public Response send() {
        if (path == null) {
            throw new IllegalArgumentException("URI Path value is null");
        }
        String fullUri = baseUri + path;
        var request = RestAssured
                .given()
                .spec(spec);
        if (!queryParams.isEmpty()) request.queryParams(queryParams);
        if (!headers.isEmpty()) request.headers(headers);
        if (!Objects.isNull(body)) request.body(body);
        log.logRequest(method, fullUri, queryParams, body);
        return switch (method) {
            case GET -> request.get(path);
            case POST -> request.post(path);
            case PUT -> request.put(path);
            case PATCH -> request.patch(path);
            case DELETE -> request.delete(path);
        };
    }

}