package com.qa.automation.base;

import com.qa.automation.factory.BrowserFactory;
import com.qa.automation.factory.BrowserType;
import com.qa.automation.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public void initializeDriver() {
        // Get browser type from config (with Jenkins override support)
        String browserName = ConfigReader.getBrowser();
        BrowserType browserType = BrowserType.fromString(browserName);
        
        // Create driver using factory
        driver = BrowserFactory.createDriver(browserType);
        driver.manage().window().maximize();

        // Set implicit wait from config
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));

        // Initialize explicit wait from config
        wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}