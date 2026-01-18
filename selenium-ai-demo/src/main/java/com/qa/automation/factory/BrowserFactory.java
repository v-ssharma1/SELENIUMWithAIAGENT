package com.qa.automation.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

public class BrowserFactory {

    /**
     * Factory method to create WebDriver instance based on browser type
     * @param browserType the type of browser to instantiate
     * @return WebDriver instance
     */
    public static WebDriver createDriver(BrowserType browserType) {
        switch (browserType) {
            case CHROME:
                return createChromeDriver(false);
            case HEADLESS_CHROME:
                return createChromeDriver(true);
            case FIREFOX:
                return createFirefoxDriver(false);
            case HEADLESS_FIREFOX:
                return createFirefoxDriver(true);
            case EDGE:
                return createEdgeDriver();
            case SAFARI:
                return createSafariDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }
    }

    /**
     * Get Selenium Grid host from environment variable or default to localhost
     */
    private static String getGridHost() {
        String host = System.getenv("HUB_HOST");
        return (host != null && !host.isEmpty()) ? host : "localhost";
    }

    private static String getGridUrl() {
        // Selenium Grid 4+ uses root endpoint, not /wd/hub
        return "http://" + getGridHost() + ":4444/";
    }

    /**
     * Create Chrome WebDriver with optional headless mode
     */
    private static WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-software-rasterizer");
        try {
            return new org.openqa.selenium.remote.RemoteWebDriver(
                new java.net.URL(getGridUrl()),
                options
            );
        } catch (java.net.MalformedURLException e) {
            throw new RuntimeException("Invalid Selenium Grid URL", e);
        }
    }

    /**
     * Create Firefox WebDriver with optional headless mode
     */
    private static WebDriver createFirefoxDriver(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("--headless");
        }
        try {
            return new org.openqa.selenium.remote.RemoteWebDriver(
                new java.net.URL(getGridUrl()),
                options
            );
        } catch (java.net.MalformedURLException e) {
            throw new RuntimeException("Invalid Selenium Grid URL", e);
        }
    }

    /**
     * Create Edge WebDriver
     */
    private static WebDriver createEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        try {
            return new org.openqa.selenium.remote.RemoteWebDriver(
                new java.net.URL(getGridUrl()),
                options
            );
        } catch (java.net.MalformedURLException e) {
            throw new RuntimeException("Invalid Selenium Grid URL", e);
        }
    }

    /**
     * Create Safari WebDriver
     * Note: Safari doesn't support many options like headless mode natively
     */
    private static WebDriver createSafariDriver() {
        WebDriverManager.safaridriver().setup();
        return new SafariDriver();
    }
}
