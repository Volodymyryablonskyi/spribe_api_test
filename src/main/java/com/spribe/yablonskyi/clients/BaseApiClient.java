package com.spribe.yablonskyi.clients;

import com.spribe.yablonskyi.endpoints.BaseEndpoints;
import com.spribe.yablonskyi.http.request.HttpMethods;
import com.spribe.yablonskyi.http.request.RequestBuilder;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class BaseApiClient<T extends BaseEndpoints> {

    protected final RequestSpecification spec;
    protected T endpoints;

    protected BaseApiClient(RequestSpecification spec, T endpoints) {
        this.spec = spec;
        this.endpoints = endpoints;
    }

    protected ResponseWrapper request(HttpMethods method, String path) {
        return request(method, path, null, null, null);
    }

    protected ResponseWrapper request(HttpMethods method, String path, Object body) {
        return request(method, path, body, null, null);
    }

    protected ResponseWrapper request(HttpMethods method, String path, Object body,
                                      Map<String, String> queryParams, Map<String, String> headers) {

        Response response = new RequestBuilder(spec)
                .withMethod(method)
                .withPath(path)
                .withBody(body)
                .withQueryParams(queryParams)
                .withHeaders(headers)
                .send();

        return ResponseWrapper.of(response);
    }

    private T createEndpoints(Class<T> cls) {
        try {
            return cls.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create endpoints instance", e);
        }
    }

}
