package com.qa.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class GoogleResultsPage extends BasePage {
    public GoogleResultsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    /**
     * Get all result titles on the search results page
     */
    public List<WebElement> getResultTitles() {
        // Google search result titles are usually in <h3> tags
        return driver.findElements(org.openqa.selenium.By.cssSelector("h3"));
    }
}
