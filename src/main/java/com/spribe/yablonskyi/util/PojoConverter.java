package com.spribe.yablonskyi.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class PojoConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts any POJO to Map<String, String> — for query param usage.
     */
    public static Map<String, String> toQueryParams(Object pojo) {
        return objectMapper.convertValue(pojo, new TypeReference<>() {});
    }

    /**
     * Converts POJO to JSON string
     */
    public static String toJson(Object pojo) {
        try {
            return objectMapper.writeValueAsString(pojo);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        }
    }

}
