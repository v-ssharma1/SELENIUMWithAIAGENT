package com.qa.automation.stepdefinitions;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.GoogleHomePage;
import com.qa.automation.utils.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class LoginSteps extends BaseTest {

    private GoogleHomePage googleHomePage;

    @Before
    public void setUp() {
        initializeDriver();
        googleHomePage = new GoogleHomePage(driver, wait);
    }

    @After
    public void tearDown() {
        quitDriver();
    }

    @Given("the user is on the Google homepage")
    public void theUserIsOnTheGoogleHomepage() {
        String url = ConfigReader.getUrl();
        googleHomePage.navigateTo(url);
    }

    @Then("the page title should be {string}")
    public void thePageTitleShouldBe(String expectedTitle) {
        String actualTitle = googleHomePage.getGooglePageTitle();
        Assert.assertTrue(actualTitle.contains(expectedTitle), 
            "Expected title to contain: " + expectedTitle + ", but got: " + actualTitle);
    }
}