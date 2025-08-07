package com.spribe.yablonskyi.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonConverter {

    private static final Gson gson = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .create();

    public static String prettifyJson(Object object) {
        return gson.toJson(object);
    }

    public static String prettifyJson(String rawJson) {
        try {
            JsonElement jsonElement = JsonParser.parseString(rawJson);
            return gson.toJson(jsonElement);
        } catch (Exception e) {
            return rawJson;
        }
    }

}
