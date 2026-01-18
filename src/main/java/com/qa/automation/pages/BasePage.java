package com.qa.automation.pages;

import com.qa.automation.locators.PageLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Base Page class containing common methods used across all page objects
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    /**
     * Find element using PageLocators enum
     */
    protected WebElement findElement(PageLocators locator) {
        return driver.findElement(locator.getLocatorValue());
    }

    /**
     * Find element using locator name string
     */
    protected WebElement findElement(String locatorName) {
        return driver.findElement(PageLocators.getLocator(locatorName));
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Get current page URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Navigate to URL
     */
    public void navigateTo(String url) {
        driver.get(url);
    }

    /**
     * Check if element is displayed
     */
    protected boolean isElementDisplayed(PageLocators locator) {
        try {
            return findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get element text
     */
    protected String getElementText(PageLocators locator) {
        return findElement(locator).getText();
    }

    /**
     * Click on element
     */
    protected void clickElement(PageLocators locator) {
        findElement(locator).click();
    }

    /**
     * Type text into element
     */
    protected void typeText(PageLocators locator, String text) {
        WebElement element = findElement(locator);
        element.clear();
        element.sendKeys(text);
    }
}
