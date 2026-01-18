package com.qa.automation.pages;

import com.qa.automation.locators.PageLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Google Home Page Object
 * Contains all locators and actions specific to Google homepage
 */
public class GoogleHomePage extends BasePage {

    public GoogleHomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    /**
     * Get the title of Google homepage
     */
    public String getGooglePageTitle() {
        return getPageTitle();
    }

    /**
     * Check if Google logo is displayed
     */
    public boolean isGoogleLogoDisplayed() {
        return isElementDisplayed(PageLocators.GOOGLE_LOGO);
    }

    /**
     * Check if search box is displayed
     */
    public boolean isSearchBoxDisplayed() {
        return isElementDisplayed(PageLocators.GOOGLE_SEARCH_BOX);
    }

    /**
     * Search for a keyword
     */
    public void searchKeyword(String keyword) {
        typeText(PageLocators.GOOGLE_SEARCH_BOX, keyword);
        clickElement(PageLocators.GOOGLE_SEARCH_BUTTON);
    }

    /**
     * Enter search text without submitting
     */
    public void enterSearchText(String searchText) {
        typeText(PageLocators.GOOGLE_SEARCH_BOX, searchText);
    }

    /**
     * Click search button
     */
    public void clickSearchButton() {
        clickElement(PageLocators.GOOGLE_SEARCH_BUTTON);
    }

    /**
     * Verify user is on Google homepage by checking title
     */
    public boolean verifyGoogleHomepage() {
        return getGooglePageTitle().contains("Google");
    }
}
