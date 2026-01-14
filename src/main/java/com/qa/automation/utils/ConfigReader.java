package com.qa.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException("config.properties not found in classpath");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading config.properties", e);
        }
    }

    public static String getEnvironment() {
        // Check system property first (for Jenkins override)
        String env = System.getProperty("environment");
        if (env != null && !env.isEmpty()) {
            return env;
        }
        return properties.getProperty("environment", "dev");
    }

    public static String getUrl() {
        // Check system property first (for Jenkins override)
        String url = System.getProperty("url");
        if (url != null && !url.isEmpty()) {
            return url;
        }
        return properties.getProperty("url", "https://www.google.com");
    }

    public static int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicitWait", "10"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicitWait", "20"));
    }

    // For testing
    public static void main(String[] args) {
        System.out.println("Environment: " + getEnvironment());
        System.out.println("URL: " + getUrl());
        System.out.println("Implicit Wait: " + getImplicitWait());
        System.out.println("Explicit Wait: " + getExplicitWait());
    }
}