package com.spribe.yablonskyi.util;

import com.spribe.yablonskyi.http.request.HttpMethods;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;


public class CustomLogger {

    private static final int MAX_LOG_BODY_LENGTH = 2000;

    private final boolean allowAllure;
    private final Logger logger;

    private CustomLogger(Class<?> clazz, boolean allowAllure) {
        this.allowAllure = allowAllure;
        this.logger = LoggerFactory.getLogger(clazz.getSimpleName());
    }

    public static CustomLogger getLogger(Class<?> clazz, boolean allowAllure) {
        return new CustomLogger(clazz, allowAllure);
    }

    public static CustomLogger getLogger(Class<?> clazz) {
        return new CustomLogger(clazz, true);
    }

    public void info(String message) {
        logger.info(message);
        logAllure(message);
    }


    public void info(String message, Object... args) {
        logger.info(message, args);
        logAllure(message, args);
    }

    public void debug(String message, Object... args) {
        logger.debug(message, args);
        logAllure(message, args);
    }

    public void warn(String message, Object... args) {
        logger.warn(message, args);
        logAllure(message, args);
    }

    public void error(String message, Object... args) {
        logger.error(message, args);
        logAllure(message, args);
    }

    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
        logAllure(message + ": " + throwable.getMessage());
    }

    public void logRequest(HttpMethods method, String uri, Map<String, String> queryParams, Object body) {
        info("Sending {} request to: {}", method, uri);
        if (!Objects.isNull(queryParams) && !queryParams.isEmpty()) {
            info(formatQueryParams(queryParams));
        }
        if (!Objects.isNull(body)) {
            String pretty = JsonConverter.prettifyJson(body);
            info("Request body:\n{}", pretty);
        }
    }


    private String formatQueryParams(Map<String, String> queryParams) {
        StringBuilder sb = new StringBuilder("Query parameters:\n");
        queryParams.forEach((key, value) ->
                sb.append("  ").append(key).append(" = ").append(value).append("\n")
        );
        return sb.toString().trim();
    }

    public void logResponse(ResponseWrapper response) {
        if (Objects.isNull(response)) {
            logger.warn("Received response - null");
            if (allowAllure) Allure.step("Received response - null");
            return;
        }
        var status = response.statusCode();
        String pretty = JsonConverter.prettifyJson(response.getBodyAsString());
        String limited = pretty.length() > MAX_LOG_BODY_LENGTH
                ? pretty.substring(0, MAX_LOG_BODY_LENGTH) + "... [truncated]"
                : pretty;

        info("Response status: {} ({})", status.getCode(), status.name());
        info("Response body:\n{}", limited);
    }

    private void logAllure(String message, Object... args) {
        if (allowAllure) {
            try {
                Allure.step(format(message, args));
            } catch (IllegalStateException ignored) {

            }
        }
    }

    public void logParams(Map<String, Object> params) {
        if (params == null || params.isEmpty()) return;
        logger.info("Test parameters:");
        params.forEach((key, value) -> {
            String val = value == null ? "null" : value.toString();
            logger.info("  {} = {}", key, val);
            if (allowAllure) Allure.parameter(key, val);
        });
    }

    private String format(String message, Object... args) {
        if (args == null) return message;
        for (Object arg : args) {
            message = message.replaceFirst("\\{}", arg != null ? arg.toString() : "null");
        }
        return message;
    }

    public void logHeader(String message, LogLevel level) {
        String formatted = "----------- " + message + " -----------";
        switch (level) {
            case INFO  -> info(formatted);
            case WARN  -> warn(formatted);
            case ERROR -> error(formatted);
        }
    }

    public enum LogLevel {
        INFO,
        WARN,
        ERROR
    }

}