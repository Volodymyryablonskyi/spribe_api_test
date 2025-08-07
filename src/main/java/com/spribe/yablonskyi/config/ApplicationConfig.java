package com.spribe.yablonskyi.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {

    private static final Properties properties = new Properties();
    private static final String PROPERTY_FILE_PATH = "application.properties";

    static {
        try (InputStream input = ApplicationConfig.class.getClassLoader()
                .getResourceAsStream(PROPERTY_FILE_PATH)) {
            if (input == null) {
                throw new RuntimeException(PROPERTY_FILE_PATH + " not found in resources");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + PROPERTY_FILE_PATH, e);
        }
    }

    public static String getBaseUri() {
        return properties.getProperty("base.uri");
    }

    public static int getConnectionTimeout() {
        return Integer.parseInt(properties.getProperty("http.connection.timeout"));
    }

    public static int getSocketTimeout() {
        return Integer.parseInt(properties.getProperty("http.socket.timeout"));
    }

    public static String getEditor() {
        return properties.getProperty("default.editor");
    }

}
