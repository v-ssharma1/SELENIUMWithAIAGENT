# Logging and Reporting Framework

## Overview
This document describes the logging and reporting framework implemented in the Selenium AI Demo project.

## Components

### 1. Log4j2 Integration
- **Configuration File**: `src/main/resources/log4j2.xml`
- **Dependency**: `org.apache.logging.log4j:log4j-core:2.22.1`
- **Log Levels**: 
  - DEBUG: Detailed debug information
  - INFO: General information messages
  - WARN: Warning messages
  - ERROR: Error messages with stack traces

### 2. LoggerUtil Class
**Location**: `src/main/java/com/qa/automation/utils/LoggerUtil.java`

Provides centralized logger instantiation:
```java
// Get logger for a class
Logger logger = LoggerUtil.getLogger(YourClass.class);

// Log messages
logger.info("Information message");
logger.debug("Debug message");
logger.warn("Warning message");
logger.error("Error message", exception);
```

### 3. ReportUtil Class
**Location**: `src/main/java/com/qa/automation/utils/ReportUtil.java`

**Features**:
- Generates reports with custom naming convention: `ProjectName_DD-MM-YYYY_hh:mm:ss.html`
- Automatic report directory creation
- System information capture
- Report path management

**Usage**:
```java
// Initialize report
ExtentReports extent = ReportUtil.initializeReport();

// Get report instance
ExtentReports extent = ReportUtil.getReportInstance();

// Flush report (call after all tests complete)
ReportUtil.flushReport();

// Get formatted date-time
String dateTime = ReportUtil.getFormattedDateTime();  // DD-MM-YYYY_hh:mm:ss
String date = ReportUtil.getFormattedDate();          // DD-MM-YYYY
String time = ReportUtil.getFormattedTime();          // hh:mm:ss
```

### 4. TestListener Class
**Location**: `src/test/java/com/qa/automation/tests/TestListener.java`

Captures test execution events:
- Test start/finish
- Test success with logging
- **Test failure with detailed step information**
- Test skip events
- Exception details and stack traces

**Failure Capture Details**:
- Failure step location
- Error message
- Stack trace (class, method, line number)
- Test parameters

### 5. Enhanced BaseTest Class
**Location**: `src/main/java/com/qa/automation/base/BaseTest.java`

Enhanced features:
- Integrated Logger instance
- Detailed initialization logging
- Step logging methods
- Action logging methods

**Methods**:
```java
protected void logStep(String stepDescription)    // Log test steps
protected void logAction(String actionDescription) // Log actions
```

## Report Naming Convention

Reports are automatically named using the following format:

```
Selenium_ai_demo_DD-MM-YYYY_hh:mm:ss.html
```

**Example**: `Selenium_ai_demo_15-01-2026_14:30:45.html`

**Location**: `test-output/HtmlReport/`

## Log File Locations

Logs are stored in the `logs/` directory:

- **selenium-ai-demo.log**: All application logs
- **selenium-ai-demo-error.log**: Error logs only
- Automatic rotation based on size (10MB) and time (daily)
- Maximum 10 backup files retained

## Usage Examples

### Basic Logging in Tests
```java
public class MyTest extends BaseTest {
    
    @Test
    public void testExample() {
        // Log information
        logger.info("Starting test execution");
        
        // Log test steps
        logStep("Navigate to application");
        driver.navigate().to("https://example.com");
        
        // Log actions
        logAction("Clicking on login button");
        loginButton.click();
        
        // Log debug information
        logger.debug("Username entered: " + username);
    }
}
```

### Using ExtentReports with Steps
```java
public class MyTest extends BaseTest {
    private ExtentTest extentTest;
    
    @BeforeMethod
    public void setUp() {
        initializeDriver();
        extentTest = ReportUtil.getReportInstance()
            .createTest(this.getClass().getSimpleName());
    }
    
    @Test
    public void testWithSteps() {
        try {
            logStep("Step 1: Navigate");
            extentTest.log(Status.INFO, "Step 1: Navigate");
            driver.navigate().to("https://example.com");
            
            logStep("Step 2: Verify page loaded");
            extentTest.log(Status.INFO, "Step 2: Verify page loaded");
            
            extentTest.log(Status.PASS, "Test passed");
        } catch (Exception e) {
            logger.error("Test failed", e);
            extentTest.log(Status.FAIL, "Test failed at execution step");
            extentTest.log(Status.FAIL, "Error: " + e.getMessage());
        }
    }
}
```

### Capturing Failure Information
The TestListener automatically captures:

```
Test Failed: testLoginWithInvalidCredentials
Failure Class: com.qa.automation.tests.LoginTest
Failure Exception: Unable to locate element
Failed At: com.qa.automation.pages.LoginPage >> enterUsername (Line: 42)
Test Parameters: username | password |
```

## Log4j2 Configuration

**File**: `src/main/resources/log4j2.xml`

**Appenders**:
1. **Console**: Logs to console with full format
2. **File**: Rolling file with daily rotation
3. **ErrorFile**: Error-only logs

**Pattern Format**:
```
[timestamp] [thread] [level] [class] - [message]
```

**Example**:
```
2026-01-15 14:30:45 [main] INFO  LoginTest - Starting login test execution
2026-01-15 14:30:46 [main] DEBUG LoginPage - Username entered successfully
2026-01-15 14:30:47 [main] ERROR LoginTest - Unable to locate login button
```

## Best Practices

1. **Always use LoggerUtil**:
   ```java
   private final Logger logger = LoggerUtil.getLogger(this.getClass());
   ```

2. **Log at appropriate levels**:
   - INFO: Major steps and actions
   - DEBUG: Detailed execution information
   - WARN: Recoverable issues
   - ERROR: Failures and exceptions

3. **Use logStep() for test steps**:
   ```java
   logStep("Navigating to login page");
   ```

4. **Capture failures with ExtentReports**:
   ```java
   extentTest.log(Status.FAIL, "Failed at step: Login");
   ```

5. **Always flush reports**:
   ```java
   ReportUtil.flushReport();  // In listener or teardown
   ```

## Troubleshooting

### Reports not generated
- Verify `test-output/HtmlReport/` directory exists
- Check write permissions on the directory
- Ensure TestListener is attached to test runner

### Logs not appearing
- Verify `log4j2.xml` is in `src/main/resources/`
- Check log level configuration in XML
- Verify logger initialization

### Report naming issues
- Ensure system date-time is correct
- Check for special characters in report names
- Verify file system supports the naming format

## Integration with CI/CD

For Jenkins/CI pipelines:
1. Reports are automatically named with timestamp
2. Each test run creates unique report file
3. Log files are rotated automatically
4. Configure email notifications to include report path

## Future Enhancements

- Database logging integration
- Remote logging to centralized server
- Custom report formatting
- Performance metrics capture
- Screenshot integration in failure steps
