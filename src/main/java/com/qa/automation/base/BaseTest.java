package com.qa.automation.base;

import com.qa.automation.factory.BrowserFactory;
import com.qa.automation.factory.BrowserType;
import com.qa.automation.utils.ConfigReader;
import com.qa.automation.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base Test class for all Selenium test classes.
 * Provides common functionality for driver initialization, logging, and test setup.
 */
public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final Logger logger = LoggerUtil.getLogger(this.getClass());

    /**
     * Initialize WebDriver with browser configuration
     */
    public void initializeDriver() {
        try {
            // Get browser type from config (with Jenkins override support)
            String browserName = ConfigReader.getBrowser();
            logger.info("Initializing browser: " + browserName);
            
            BrowserType browserType = BrowserType.fromString(browserName);
            
            // Create driver using factory
            driver = BrowserFactory.createDriver(browserType);
            logger.info("WebDriver instance created successfully");
            
            driver.manage().window().maximize();
            logger.info("Browser window maximized");

            // Set implicit wait from config
            long implicitWait = ConfigReader.getImplicitWait();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
            logger.info("Implicit wait set to: " + implicitWait + " seconds");

            // Initialize explicit wait from config
            long explicitWait = ConfigReader.getExplicitWait();
            wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
            logger.info("Explicit wait set to: " + explicitWait + " seconds");
            
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Quit WebDriver and cleanup resources
     */
    public void quitDriver() {
        try {
            if (driver != null) {
                driver.quit();
                logger.info("WebDriver quit successfully");
            }
        } catch (Exception e) {
            logger.error("Error while quitting WebDriver: " + e.getMessage(), e);
        }
    }

    /**
     * Log test step information
     * @param stepDescription Description of the test step
     */
    protected void logStep(String stepDescription) {
        logger.info("TEST STEP: " + stepDescription);
    }

    /**
     * Log test action with logging level
     * @param actionDescription Description of the action
     */
    protected void logAction(String actionDescription) {
        logger.debug("ACTION: " + actionDescription);
    }
}