package com.qa.automation.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.GoogleHomePage;
import com.qa.automation.pages.GoogleResultsPage;
import com.qa.automation.utils.ReportUtil;
import com.qa.automation.utils.ScreenshotUtil;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ExampleTest extends BaseTest {

    private ExtentTest extentTest;

    @BeforeMethod
    public void setUp() {
        logger.info("Setting up test environment");
        initializeDriver();
        extentTest = ReportUtil.getReportInstance().createTest(
                this.getClass().getSimpleName() + "_" + Thread.currentThread().getStackTrace()[2].getMethodName()
        );
        logStep("Test setup completed");
    }

    /**
     * 
     */
    @Test(description = "Example test demonstrating logging and step capture")
    public void exampleGoogleSearchTest() {
        try {
            // Step 1: Navigate to Google
            logStep("Navigating to Google homepage");
            extentTest.log(Status.INFO, "Step 1: Navigating to Google homepage");
            driver.navigate().to("https://www.google.com");
            // Screenshot after step 1
            String step1Screenshot = ScreenshotUtil.captureScreenshotBase64(driver);
            if (step1Screenshot != null) {
                extentTest.addScreenCaptureFromBase64String(step1Screenshot, "Step 1 Screenshot");
            }
            // Step 2: Verify page loaded
            logStep("Verifying Google homepage loaded");
            extentTest.log(Status.INFO, "Step 2: Verifying Google homepage loaded");
            
            // Screenshot after step 2
            String step2Screenshot = ScreenshotUtil.captureScreenshotBase64(driver);
            if (step2Screenshot != null) {
                extentTest.addScreenCaptureFromBase64String(step2Screenshot, "Step 2 Screenshot");
            }
            // Step 3: Perform search
            logStep("Entering search query: 'Selenium'");
            extentTest.log(Status.INFO, "Step 3: Entering search query: 'Selenium'");
            logAction("Typing search term");
            // homePage.searchFor("Selenium");
            // Screenshot after step 3
            String step3Screenshot = ScreenshotUtil.captureScreenshotBase64(driver);
            if (step3Screenshot != null) {
                extentTest.addScreenCaptureFromBase64String(step3Screenshot, "Step 3 Screenshot");
            }
            // Step 4: Verify results
            logStep("Verifying search results");
            extentTest.log(Status.INFO, "Step 4: Verifying search results");
            // Screenshot after step 4
            String step4Screenshot = ScreenshotUtil.captureScreenshotBase64(driver);
            if (step4Screenshot != null) {
                extentTest.addScreenCaptureFromBase64String(step4Screenshot, "Step 4 Screenshot");
            }
            logger.info("Test completed successfully");
            extentTest.log(Status.PASS, "Test completed successfully");
        } catch (Exception e) {
            logger.error("Test failed with exception: " + e.getMessage(), e);
            extentTest.log(Status.FAIL, "Test failed at execution step");
            extentTest.log(Status.FAIL, "Error: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(description = "When user performs a search for facebook page, user should see required search results on Google page")
    public void facebookSearchResultsTest() {
        try {
            // Step 1: Navigate to Google
            logStep("Navigating to Google homepage");
            extentTest.log(Status.INFO, "Step 1: Navigating to Google homepage");
            driver.navigate().to("https://www.google.com");
            String step1Screenshot = ScreenshotUtil.captureScreenshotBase64(driver);
            if (step1Screenshot != null) {
                extentTest.addScreenCaptureFromBase64String(step1Screenshot, "Step 1 Screenshot");
            }
            // Step 2: Search for 'facebook'
            logStep("Searching for 'facebook'");
            extentTest.log(Status.INFO, "Step 2: Searching for 'facebook'");
            GoogleHomePage homePage = new GoogleHomePage(driver, wait);
            homePage.searchKeyword("facebook");
            String step2Screenshot = ScreenshotUtil.captureScreenshotBase64(driver);
            if (step2Screenshot != null) {
                extentTest.addScreenCaptureFromBase64String(step2Screenshot, "Step 2 Screenshot");
            }
            // Step 3: Verify search results contain 'Facebook'
            logStep("Verifying search results contain 'Facebook'");
            extentTest.log(Status.INFO, "Step 3: Verifying search results contain 'Facebook'");
            GoogleResultsPage resultsPage = new GoogleResultsPage(driver, wait);
            boolean found = resultsPage.getResultTitles().stream()
                .anyMatch(e -> e.getText().toLowerCase().contains("facebook"));
            Assert.assertTrue(found, "Expected at least one search result to contain 'facebook'");
            String step3Screenshot = ScreenshotUtil.captureScreenshotBase64(driver);
            if (step3Screenshot != null) {
                extentTest.addScreenCaptureFromBase64String(step3Screenshot, "Step 3 Screenshot");
            }
            logger.info("Facebook search test completed successfully");
            extentTest.log(Status.PASS, "Facebook search test completed successfully");
        } catch (Exception e) {
            logger.error("Facebook search test failed: " + e.getMessage(), e);
            extentTest.log(Status.FAIL, "Test failed at execution step");
            extentTest.log(Status.FAIL, "Error: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        logger.info("Tearing down test environment");
        logStep("Test cleanup in progress");
        quitDriver();
        logger.info("Test execution completed");
    }
}
