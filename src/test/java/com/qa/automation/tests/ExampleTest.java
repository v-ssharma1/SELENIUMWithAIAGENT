package com.qa.automation.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.GoogleHomePage;
import com.qa.automation.utils.ReportUtil;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Example Test Class demonstrating logging and failure step capture.
 * Shows how to use LoggerUtil for logging and ExtentReports for detailed failure reporting.
 */
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

    @Test(description = "Example test demonstrating logging and step capture")
    public void exampleGoogleSearchTest() {
        try {
            // Step 1: Navigate to Google
            logStep("Navigating to Google homepage");
            extentTest.log(Status.INFO, "Step 1: Navigating to Google homepage");
            driver.navigate().to("https://www.google.com");
            
            // Step 2: Verify page loaded
            logStep("Verifying Google homepage loaded");
            extentTest.log(Status.INFO, "Step 2: Verifying Google homepage loaded");
            GoogleHomePage homePage = new GoogleHomePage(driver, wait);
            
            // Step 3: Perform search
            logStep("Entering search query: 'Selenium'");
            extentTest.log(Status.INFO, "Step 3: Entering search query: 'Selenium'");
            logAction("Typing search term");
            // homePage.searchFor("Selenium");
            
            // Step 4: Verify results
            logStep("Verifying search results");
            extentTest.log(Status.INFO, "Step 4: Verifying search results");
            
            logger.info("Test completed successfully");
            extentTest.log(Status.PASS, "Test completed successfully");
            
        } catch (Exception e) {
            logger.error("Test failed with exception: " + e.getMessage(), e);
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
