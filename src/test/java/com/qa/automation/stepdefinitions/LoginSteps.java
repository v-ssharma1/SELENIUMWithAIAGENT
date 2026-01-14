package com.qa.automation.stepdefinitions;

import com.qa.automation.base.BaseTest;
import com.qa.automation.utils.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class LoginSteps extends BaseTest {

    @Before
    public void setUp() {
        initializeDriver();
    }

    @After
    public void tearDown() {
        quitDriver();
    }

    @Given("the user is on the Google homepage")
    public void theUserIsOnTheGoogleHomepage() {
        String url = ConfigReader.getUrl();
        driver.get(url);
    }

    @Then("the page title should be {string}")
    public void thePageTitleShouldBe(String expectedTitle) {
        // Example of using explicit wait
        wait.until(ExpectedConditions.titleIs(expectedTitle));
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
    }
}