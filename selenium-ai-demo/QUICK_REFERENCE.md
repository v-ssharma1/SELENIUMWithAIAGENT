# Quick Reference Guide - Logging & Reporting

## 📋 Files Created/Modified

### New Files Created:
1. **log4j2.xml** - Log4j2 configuration
   - Location: `src/main/resources/log4j2.xml`
   
2. **LoggerUtil.java** - Logger utility class
   - Location: `src/main/java/com/qa/automation/utils/LoggerUtil.java`
   
3. **ReportUtil.java** - Report management utility
   - Location: `src/main/java/com/qa/automation/utils/ReportUtil.java`
   
4. **TestListener.java** - Test execution listener for failure capture
   - Location: `src/test/java/com/qa/automation/tests/TestListener.java`
   
5. **ExampleTest.java** - Example test showing usage
   - Location: `src/test/java/com/qa/automation/tests/ExampleTest.java`

### Files Modified:
1. **pom.xml** - Added Log4j2 dependencies
2. **BaseTest.java** - Added logging support and helper methods
3. **TestRunner.java** - Added TestListener annotation

---

## 🚀 Quick Usage

### 1. Basic Logging
```java
private final Logger logger = LoggerUtil.getLogger(this.getClass());

logger.info("Test started");
logger.debug("Debug info");
logger.warn("Warning message");
logger.error("Error occurred", exception);
```

### 2. Test Steps
```java
logStep("Navigate to application");
logAction("Click login button");
```

### 3. Report with Timestamp
```
Report Name: Selenium_ai_demo_15-01-2026_14:30:45.html
Location: test-output/HtmlReport/
```

### 4. Failure Capture
Automatically captures:
- Failed step
- Exception message
- Stack trace (class, method, line number)
- Test parameters

---

## 📊 Log File Locations

- **Logs**: `logs/` directory
  - `selenium-ai-demo.log` - All logs
  - `selenium-ai-demo-error.log` - Errors only

- **Reports**: `test-output/HtmlReport/`
  - Format: `Selenium_ai_demo_DD-MM-YYYY_hh:mm:ss.html`

---

## 🔧 Configuration

### Log4j2 Levels
- DEBUG: src/main/resources/log4j2.xml
- Configure package-specific logging
- Rolling file with 10MB rotation

### Report Settings
- Edit PROJECT_NAME in ReportUtil.java
- Edit REPORT_DIRECTORY for custom path
- Modify DATE_TIME_FORMAT for different date format

---

## ✅ Build Status
✓ Clean compilation successful
✓ All dependencies resolved
✓ Log4j2 properly configured
✓ TestListener ready for use

---

## 📝 Next Steps

1. Update your test classes to extend BaseTest
2. Add logger initialization in your classes
3. Use logStep() and logAction() for documentation
4. TestListener will automatically capture failures
5. Reports will be generated with timestamp naming

## Example Test Structure
```java
public class LoginTest extends BaseTest {
    
    private ExtentTest extentTest;
    
    @BeforeMethod
    public void setUp() {
        initializeDriver();
        extentTest = ReportUtil.getReportInstance().createTest("LoginTest");
    }
    
    @Test
    public void testValidLogin() {
        logStep("Navigate to login page");
        extentTest.log(Status.INFO, "Navigate to login page");
        
        logStep("Enter credentials");
        extentTest.log(Status.INFO, "Enter credentials");
        
        extentTest.log(Status.PASS, "Test passed");
    }
    
    @AfterMethod
    public void tearDown() {
        quitDriver();
    }
}
```

---

## 📞 Support

For detailed documentation, refer to: `LOGGING_AND_REPORTING.md`

For troubleshooting, check:
- Log files in `logs/` directory
- Report HTML files in `test-output/HtmlReport/`
- Console output for immediate feedback
