package com.qa.automation.locators;

import org.openqa.selenium.By;

public enum PageLocators {

    // Google Home Page Locators
    GOOGLE_SEARCH_BOX("google_search_box", By.name("q")),
    GOOGLE_SEARCH_BUTTON("google_search_button", By.xpath("//input[@name='btnK']")),
    GOOGLE_LOGO("google_logo", By.xpath("//img[@alt='Google']")),

    // Add more page locators below as needed
    // Example format:
    // PAGE_ELEMENT("locator_name", By.locatorStrategy("locator_value"))
    ;

    private final String locatorName;
    private final By locatorValue;

    PageLocators(String locatorName, By locatorValue) {
        this.locatorName = locatorName;
        this.locatorValue = locatorValue;
    }

    public String getLocatorName() {
        return locatorName;
    }

    public By getLocatorValue() {
        return locatorValue;
    }

    /**
     * Get locator by name for easy reference
     */
    public static By getLocator(String locatorName) {
        for (PageLocators locator : PageLocators.values()) {
            if (locator.locatorName.equalsIgnoreCase(locatorName)) {
                return locator.locatorValue;
            }
        }
        throw new IllegalArgumentException("Locator not found: " + locatorName);
    }

    /**
     * Print all available locators (useful for debugging)
     */
    public static void printAllLocators() {
        System.out.println("Available Locators:");
        for (PageLocators locator : PageLocators.values()) {
            System.out.println("  - " + locator.locatorName + ": " + locator.locatorValue);
        }
    }
}
