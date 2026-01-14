package com.qa.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        properties = loadProperties();
    }

    /**
     * Load properties based on environment system property or default to dev
     * Looks for config-{env}.properties file, falls back to config.properties
     */
    private static Properties loadProperties() {
        Properties props = new Properties();
        String env = getEnvironmentFromSystem();
        String configFileName = "config-" + env + ".properties";

        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(configFileName)) {
            if (input != null) {
                props.load(input);
                System.out.println("Loaded configuration from: " + configFileName);
            } else {
                // Fallback to default config.properties
                System.out.println("Config file not found: " + configFileName + ". Falling back to config.properties");
                try (InputStream fallbackInput = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
                    if (fallbackInput != null) {
                        props.load(fallbackInput);
                    } else {
                        throw new RuntimeException("config.properties not found in classpath");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading configuration properties", e);
        }

        return props;
    }

    /**
     * Get environment from system property (Jenkins parameter) or default to "dev"
     */
    private static String getEnvironmentFromSystem() {
        String env = System.getProperty("env");
        return (env != null && !env.isEmpty()) ? env : "dev";
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
        // Check system property first (for Jenkins override)
        String wait = System.getProperty("implicitWait");
        if (wait != null && !wait.isEmpty()) {
            return Integer.parseInt(wait);
        }
        return Integer.parseInt(properties.getProperty("implicitWait", "10"));
    }

    public static int getExplicitWait() {
        // Check system property first (for Jenkins override)
        String wait = System.getProperty("explicitWait");
        if (wait != null && !wait.isEmpty()) {
            return Integer.parseInt(wait);
        }
        return Integer.parseInt(properties.getProperty("explicitWait", "20"));
    }

    public static String getBrowser() {
        // Check system property first (for Jenkins override)
        String browser = System.getProperty("browser");
        if (browser != null && !browser.isEmpty()) {
            return browser;
        }
        return properties.getProperty("browser", "chrome");
    }

    /**
     * Get all properties (useful for debugging)
     */
    public static Properties getAllProperties() {
        return properties;
    }

    // For testing
    public static void main(String[] args) {
        System.out.println("\n=== Configuration Properties ===");
        System.out.println("Environment: " + getEnvironment());
        System.out.println("URL: " + getUrl());
        System.out.println("Browser: " + getBrowser());
        System.out.println("Implicit Wait: " + getImplicitWait() + " seconds");
        System.out.println("Explicit Wait: " + getExplicitWait() + " seconds");
        System.out.println("================================\n");
    }
}