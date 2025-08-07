package com.spribe.yablonskyi.config;

import com.spribe.yablonskyi.constants.Constants;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.specification.RequestSpecification;

import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.config.LogConfig.logConfig;

public class RestAssuredConfigurator {

    public static void configure() {
        RestAssured.config = RestAssuredConfig.config()
                .jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL))
                .logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL))
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", ApplicationConfig.getConnectionTimeout())
                        .setParam("http.socket.timeout", ApplicationConfig.getSocketTimeout()))
                .redirect(RedirectConfig.redirectConfig()
                        .followRedirects(true)
                        .maxRedirects(10));
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public static RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .setConfig(RestAssured.config)
                .setBaseUri(Constants.BASE_URI)
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .build();
    }
}