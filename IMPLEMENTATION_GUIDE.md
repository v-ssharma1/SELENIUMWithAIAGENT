# Implementation Guide - How to Use Logging & Reporting in Your Tests

## Step 1: Extend BaseTest Class

All your test classes should extend `BaseTest`:

```java
public class YourTest extends BaseTest {
    // Your test code here
}
```

This automatically provides:
- `driver` - WebDriver instance
- `wait` - WebDriverWait instance
- `logger` - Log4j2 logger
- `initializeDriver()` - Initialize browser
- `quitDriver()` - Close browser
- `logStep()` - Log test steps
- `logAction()` - Log actions

---

## Step 2: Setup Test with Before/After Methods

```java
public class LoginTest extends BaseTest {
    
    private ExtentTest extentTest;
    
    @BeforeMethod
    public void setUp() {
        logger.info("========== TEST SETUP ==========");
        
        // Initialize driver
        initializeDriver();
        logger.info("Driver initialized successfully");
        
        // Create ExtentTest instance
        extentTest = ReportUtil.getReportInstance()
            .createTest(this.getClass().getSimpleName() + "_" + 
                       Thread.currentThread().getStackTrace()[2].getMethodName());
        
        logStep("Test environment setup completed");
    }
    
    @AfterMethod
    public void tearDown() {
        logger.info("========== TEST TEARDOWN ==========");
        logStep("Closing browser and cleaning up");
        quitDriver();
        logger.info("Test execution completed");
    }
}
```

---

## Step 3: Write Test with Logging

```java
@Test(description = "Valid login test")
public void testValidLogin() {
    try {
        // Step 1: Navigate to Login Page
        logStep("Navigate to login page");
        extentTest.log(Status.INFO, "Step 1: Navigate to login page");
        driver.navigate().to("https://example.com/login");
        
        // Step 2: Create Page Object
        logStep("Creating LoginPage object");
        extentTest.log(Status.INFO, "Step 2: Creating LoginPage object");
        LoginPage loginPage = new LoginPage(driver, wait);
        
        // Step 3: Enter Credentials
        logStep("Entering username");
        logAction("Typing username: testuser");
        extentTest.log(Status.INFO, "Step 3: Entering username");
        loginPage.enterUsername("testuser");
        
        logStep("Entering password");
        logAction("Typing password");
        extentTest.log(Status.INFO, "Entering password");
        loginPage.enterPassword("password123");
        
        // Step 4: Click Login Button
        logStep("Clicking login button");
        logAction("Clicking button");
        extentTest.log(Status.INFO, "Step 4: Clicking login button");
        loginPage.clickLoginButton();
        
        // Step 5: Verify Login Success
        logStep("Verifying successful login");
        extentTest.log(Status.INFO, "Step 5: Verifying successful login");
        
        // Add assertions
        Assert.assertTrue(loginPage.isSuccessMessageDisplayed());
        logger.info("Login successful - test passed");
        
        // Log success
        extentTest.log(Status.PASS, "Test Passed: Valid login successful");
        
    } catch (AssertionError | Exception e) {
        // Log failure
        logger.error("Test failed: " + e.getMessage(), e);
        extentTest.log(Status.FAIL, "Test failed at execution step");
        extentTest.log(Status.FAIL, "Failure Reason: " + e.getMessage());
        
        // Add screenshot (optional)
        // extentTest.addScreenCaptureFromPath(takeScreenshot());
        
        Assert.fail("Test failed: " + e.getMessage());
    }
}
```

---

## Step 4: Using Logger Effectively

### For Information Messages
```java
logger.info("User successfully logged in");
logger.info("Test execution started");
```

### For Debug Messages
```java
logger.debug("Element located: " + element.getText());
logger.debug("Current URL: " + driver.getCurrentUrl());
```

### For Warnings
```java
logger.warn("Element not found, retrying...");
logger.warn("Timeout occurred");
```

### For Errors (Always Include Exception)
```java
try {
    // Some code
} catch (Exception e) {
    logger.error("Failed to click element: " + e.getMessage(), e);
}
```

---

## Step 5: Report Generation

Reports are automatically generated with:
- **Name Format**: `Selenium_ai_demo_DD-MM-YYYY_hh:mm:ss.html`
- **Location**: `test-output/HtmlReport/`
- **Content**:
  - Test results (pass/fail/skip)
  - Execution steps
  - Failure details
  - Stack traces
  - Test parameters

### Example Report Names:
- `Selenium_ai_demo_15-01-2026_14:30:45.html`
- `Selenium_ai_demo_15-01-2026_15:45:22.html`
- `Selenium_ai_demo_16-01-2026_10:15:33.html`

---

## Step 6: Failure Step Capture

When a test fails, the TestListener automatically captures:

```
TEST FAILURE REPORT
===================
Test Name: testValidLogin
Test Class: com.qa.automation.tests.LoginTest
Failed at: loginPage.clickLoginButton() [Line: 87]
Error Message: No such element exception
Stack Trace: com.qa.automation.pages.LoginPage >> clickLoginButton (Line: 87)
Test Parameters: username=testuser | password=password123
Timestamp: 15-01-2026 14:30:47
```

---

## Step 7: Accessing Logs

### Console Output
Logs appear in the console with format:
```
2026-01-15 14:30:45 [main] INFO  LoginTest - User successfully logged in
2026-01-15 14:30:46 [main] DEBUG LoginPage - Element located: Login Button
2026-01-15 14:30:47 [main] ERROR LoginTest - Failed to click element
```

### Log Files
Check `logs/` directory:
- `selenium-ai-demo.log` - All logs
- `selenium-ai-demo-error.log` - Errors only

---

## Complete Test Example

```java
package com.qa.automation.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.LoginPage;
import com.qa.automation.utils.ReportUtil;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    
    private ExtentTest extentTest;
    private LoginPage loginPage;
    
    @BeforeMethod
    public void setUp() {
        logger.info("========== TEST SETUP START ==========");
        initializeDriver();
        extentTest = ReportUtil.getReportInstance()
            .createTest("LoginTest_testValidLogin");
        extentTest.log(Status.INFO, "Test Setup Completed");
        logger.info("========== TEST SETUP COMPLETED ==========");
    }
    
    @Test(description = "Test valid login with correct credentials")
    public void testValidLogin() {
        try {
            logStep("Navigate to login page");
            extentTest.log(Status.INFO, "Step 1: Navigate to login page");
            driver.navigate().to("https://app.example.com/login");
            
            logStep("Initialize LoginPage");
            extentTest.log(Status.INFO, "Step 2: Initialize LoginPage");
            loginPage = new LoginPage(driver, wait);
            
            logStep("Enter valid credentials");
            extentTest.log(Status.INFO, "Step 3: Enter valid credentials");
            logAction("Username: testuser");
            loginPage.enterUsername("testuser");
            
            logAction("Password: Test@123");
            loginPage.enterPassword("Test@123");
            
            logStep("Click login button");
            extentTest.log(Status.INFO, "Step 4: Click login button");
            loginPage.clickLoginButton();
            
            logStep("Verify dashboard displayed");
            extentTest.log(Status.INFO, "Step 5: Verify dashboard displayed");
            Assert.assertTrue(loginPage.isDashboardDisplayed(), 
                "Dashboard not displayed");
            
            logger.info("✓ Test Passed: Valid login successful");
            extentTest.log(Status.PASS, "Test PASSED");
            
        } catch (Exception e) {
            logger.error("✗ Test Failed: " + e.getMessage(), e);
            extentTest.log(Status.FAIL, "Test failed at execution step");
            extentTest.log(Status.FAIL, "Error: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    
    @AfterMethod
    public void tearDown() {
        logger.info("========== TEST TEARDOWN START ==========");
        logStep("Close browser");
        quitDriver();
        logger.info("========== TEST TEARDOWN COMPLETED ==========");
    }
}
```

---

## Key Features Summary

✅ **Automatic Logging** - All actions logged to file and console
✅ **Timestamped Reports** - Format: ProjectName_DD-MM-YYYY_hh:mm:ss
✅ **Failure Capture** - Automatic step and error tracking
✅ **Log Levels** - DEBUG, INFO, WARN, ERROR with appropriate usage
✅ **Rolling Logs** - Automatic rotation and archival
✅ **ExtentReports Integration** - Professional HTML reports
✅ **TestListener** - Automatic test event capture

---

## Troubleshooting Common Issues

### Reports not created
- Verify `test-output/HtmlReport/` directory exists
- Check TestListener is attached to TestRunner
- Ensure `ReportUtil.flushReport()` is called

### Logs not appearing
- Check log level in log4j2.xml
- Verify logger is initialized correctly
- Check file permissions on logs directory

### Timestamp format issues
- Verify system date-time is correct
- Check DATE_TIME_FORMAT in ReportUtil.java
- Use `ReportUtil.getFormattedDateTime()` for consistency

### Test listener not capturing failures
- Ensure TestListener.java is in correct package
- Verify @Listeners annotation in TestRunner
- Check TestNG version compatibility

---

For detailed documentation, refer to: `LOGGING_AND_REPORTING.md`
