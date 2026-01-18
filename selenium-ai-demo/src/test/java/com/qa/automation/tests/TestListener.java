package com.qa.automation.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.automation.utils.LoggerUtil;
import com.qa.automation.utils.ReportUtil;
import com.qa.automation.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Test Listener to capture test execution events and report failures with steps.
 * Captures:
 * - Test start/finish events
 * - Test failures with error details
 * - Test passes
 * - Test skips
 */
public class TestListener implements ITestListener {

    private static final Logger logger = LoggerUtil.getLogger(TestListener.class);
    private static final ExtentReports extent = ReportUtil.getReportInstance();
    
    // Map to store ExtentTest instances for each test
    private static final Map<String, ExtentTest> testMap = new HashMap<>();

    /**
     * Called when test suite execution starts
     */
    @Override
    public void onStart(ITestContext context) {
        logger.info("===============================================");
        logger.info("Test Suite Started: " + context.getName());
        logger.info("Test Count: " + context.getAllTestMethods().length);
        logger.info("===============================================");
    }

    /**
     * Called when test suite execution finishes
     */
    @Override
    public void onFinish(ITestContext context) {
        logger.info("===============================================");
        logger.info("Test Suite Finished: " + context.getName());
        logger.info("Total Tests Passed: " + context.getPassedTests().size());
        logger.info("Total Tests Failed: " + context.getFailedTests().size());
        logger.info("Total Tests Skipped: " + context.getSkippedTests().size());
        logger.info("===============================================");
        
        // Flush the report
        ReportUtil.flushReport();
    }

    /**
     * Called when individual test starts
     */
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String testClass = result.getTestClass().getName();
        
        logger.info("Test Started: " + testClass + " >> " + testName);
        
        // Create ExtentTest
        ExtentTest test = extent.createTest(testName, "Test: " + testClass + "::" + testName);
        testMap.put(testName, test);
        
        // Log test start in report
        test.log(Status.INFO, "Test Started: " + testName);
    }

    /**
     * Called when test passes
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        logger.info("✓ Test Passed: " + testName);
        
        ExtentTest test = testMap.get(testName);
        if (test != null) {
            test.log(Status.PASS, "Test Passed: " + testName);
        }
    }

    /**
     * Called when test fails - captures failure step and error details
     */
    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String testClass = result.getTestClass().getName();
        Throwable throwable = result.getThrowable();
        
        logger.error("✗ Test Failed: " + testName);
        logger.error("Failure Class: " + testClass);
        logger.error("Failure Exception: " + (throwable != null ? throwable.getMessage() : "No exception message"));
        
        ExtentTest test = testMap.get(testName);
        if (test != null) {
            // Capture the failure step
            test.log(Status.FAIL, "Test Failed at Step");
            
            // Add failure details
            if (throwable != null) {
                test.log(Status.FAIL, "Failure Reason: " + throwable.getMessage());
                
                // Add stack trace for debugging
                StackTraceElement[] stackTrace = throwable.getStackTrace();
                if (stackTrace.length > 0) {
                    test.log(Status.FAIL, "Failed At: " + stackTrace[0].getClassName() + 
                            " >> " + stackTrace[0].getMethodName() + 
                            " (Line: " + stackTrace[0].getLineNumber() + ")");
                }
                // Capture and attach screenshot if possible
                Object testInstance = result.getInstance();
                WebDriver driver = null;
                try {
                    java.lang.reflect.Field driverField = testInstance.getClass().getSuperclass().getDeclaredField("driver");
                    driverField.setAccessible(true);
                    driver = (WebDriver) driverField.get(testInstance);
                } catch (Exception ex) {
                    logger.warn("Could not access WebDriver for screenshot: " + ex.getMessage());
                }
                if (driver != null) {
                    String base64 = ScreenshotUtil.captureScreenshotBase64(driver);
                    if (base64 != null) {
                        test.addScreenCaptureFromBase64String(base64, "Failure Screenshot");
                    }
                }
            }
            
            // Log test parameters if available
            Object[] parameters = result.getParameters();
            if (parameters != null && parameters.length > 0) {
                StringBuilder paramStr = new StringBuilder("Test Parameters: ");
                for (Object param : parameters) {
                    paramStr.append(param).append(" | ");
                }
                test.log(Status.INFO, paramStr.toString());
            }
        }
    }

    /**
     * Called when test is skipped
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        logger.warn("⊘ Test Skipped: " + testName);
        
        ExtentTest test = testMap.get(testName);
        if (test != null) {
            test.log(Status.SKIP, "Test Skipped: " + testName);
            if (result.getThrowable() != null) {
                test.log(Status.SKIP, "Reason: " + result.getThrowable().getMessage());
            }
        }
    }

    /**
     * Called when test fails but within success percentage (if configured)
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.info("Test Failed But Within Success Percentage: " + result.getMethod().getMethodName());
    }

    /**
     * Get ExtentTest instance by test name
     * @param testName The name of the test
     * @return ExtentTest instance
     */
    public static ExtentTest getTest(String testName) {
        return testMap.get(testName);
    }
}
