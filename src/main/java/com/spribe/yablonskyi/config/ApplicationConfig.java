package com.spribe.yablonskyi.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class ApplicationConfig {

    private static final Properties PROPS = new Properties();
    private static final String PROPERTY_FILE_PATH = "application.properties";

    static {
        try (InputStream input = ApplicationConfig.class.getClassLoader()
                .getResourceAsStream(PROPERTY_FILE_PATH)) {
            if (input == null) {
                throw new RuntimeException(PROPERTY_FILE_PATH + " not found in resources");
            }
            PROPS.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + PROPERTY_FILE_PATH, e);
        }
    }

    public static String getBaseUri() {
        return getProp("base.uri");
    }

    public static int getThreadPoolSize() {
        return getIntProp("threads");
    }

    public static int getConnectionTimeout() {
        return getIntProp("http.connection.timeout");
    }

    public static int getSocketTimeout() {
        return getIntProp("http.socket.timeout");
    }

    private static String getProp(String key) {
        return Optional.ofNullable(System.getProperty(key))
                .orElseGet(() -> PROPS.getProperty(key));
    }

    private static int getIntProp(String key) {
        return Integer.parseInt(getProp(key));
    }

}
