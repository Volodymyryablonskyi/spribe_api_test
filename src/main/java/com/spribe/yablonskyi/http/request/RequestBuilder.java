package com.spribe.yablonskyi.http.request;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestBuilder {

    private final RequestSpecification spec;
    private final Map<String, String> queryParams = new HashMap<>();
    private final Map<String, String> headers = new HashMap<>();
    private HttpMethods method;
    private String path;
    private Object body;

    public RequestBuilder(RequestSpecification spec) {
        this.spec = spec;
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

    public RequestBuilder withQuery(String key, String value) {
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
        RequestSpecification request = spec;
        if (!queryParams.isEmpty()) {
            request = request.queryParams(queryParams);
        }
        if (!headers.isEmpty()) {
            request = request.headers(headers);
        }
        if (!Objects.isNull(body)) {
            request = request.body(body);
        }
        return switch (method) {
            case GET -> request.get(path);
            case POST -> request.post(path);
            case PUT -> request.put(path);
            case PATCH -> request.patch(path);
            case DELETE -> request.delete(path);
        };
    }


}
