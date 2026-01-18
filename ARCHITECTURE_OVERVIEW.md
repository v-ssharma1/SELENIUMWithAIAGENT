# Architecture & Integration Overview

## 🏗️ Framework Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     TEST EXECUTION FLOW                     │
└─────────────────────────────────────────────────────────────┘

┌──────────────┐
│  Test Start  │
└──────┬───────┘
       │
       ▼
┌──────────────────────────────────────┐
│  TestListener.onTestStart()          │  ◄─── Captures Test Start
│  ├─ Create ExtentTest                │
│  ├─ Log test name                    │
│  └─ Initialize report entry          │
└──────┬───────────────────────────────┘
       │
       ▼
┌──────────────────────────────────────┐
│  @BeforeMethod (Test Setup)          │
│  ├─ initializeDriver()               │  ◄─── Uses LoggerUtil
│  ├─ Create ExtentTest instance       │       Logs: Driver initialization
│  └─ Set up test environment          │
└──────┬───────────────────────────────┘
       │
       ▼
┌──────────────────────────────────────┐
│  Test Execution                      │
│  ├─ logStep("Step description")      │  ◄─── Logs via LoggerUtil
│  ├─ logAction("Action")              │       Logs to file + console
│  ├─ Business logic                   │
│  └─ Assertions                       │
└──────┬───────────────────────────────┘
       │
   ┌───┴────┐
   │        │
   ▼        ▼
PASS      FAIL
   │        │
   └───┬────┘
       │
       ▼
┌──────────────────────────────────────┐
│  TestListener.onTestSuccess()        │
│  OR                                  │
│  TestListener.onTestFailure()        │  ◄─── Captures Failure Step
│  ├─ Log result status                │       Exception details
│  ├─ Log failure details              │       Stack trace
│  ├─ Log exception message            │       Test parameters
│  └─ Add to ExtentReports             │
└──────┬───────────────────────────────┘
       │
       ▼
┌──────────────────────────────────────┐
│  @AfterMethod (Test Teardown)        │
│  ├─ logStep("Cleanup")               │  ◄─── Final logging
│  └─ quitDriver()                     │
└──────┬───────────────────────────────┘
       │
       ▼
┌──────────────────────────────────────┐
│  TestListener.onTestFinish()         │
│  ├─ Flush ExtentReports              │  ◄─── Generate Report
│  └─ Log test completion              │       Name: Selenium_ai_demo_
                                              DD-MM-YYYY_hh:mm:ss.html
└──────────────────────────────────────┘
```

---

## 📦 Component Interaction Diagram

```
┌─────────────────────────────────────────────────────────┐
│                    Your Test Class                      │
│                   (extends BaseTest)                    │
└────┬────────────────────────────────────────────────┬───┘
     │ Uses                                            │ Creates
     ▼                                                 ▼
┌─────────────────────────┐              ┌────────────────────────┐
│  BaseTest               │              │  ExtentTest Instance   │
├─────────────────────────┤              │  (for reporting)       │
│ + driver                │              └────────────────────────┘
│ + wait                  │                        │
│ + logger                │ ◄─────────┐           │
│ + initializeDriver()    │           │ Uses      │
│ + quitDriver()          │           │           ▼
│ + logStep()             │      ┌────────────────────────┐
│ + logAction()           │      │   LoggerUtil           │
└─────────────────────────┘      ├────────────────────────┤
                                 │ + getLogger(Class)     │
                                 │ + getLogger(String)    │
                                 └────┬───────────────────┘
                                      │
                                      ▼
                                 ┌─────────────────┐
                                 │  Log4j2 Logger  │
                                 │                 │
                                 │ Outputs to:     │
                                 │ • Console       │
                                 │ • File          │
                                 │ • Error File    │
                                 └─────────────────┘
```

---

## 📊 Reporting & Logging Flow

```
TEST EXECUTION
    │
    ├─────────────────┬────────────────────┬─────────────────┐
    │                 │                    │                 │
    ▼                 ▼                    ▼                 ▼
TestListener     LoggerUtil           ReportUtil          File Output
(Events)         (Logging)            (HTML Reports)      (Logs)
    │                 │                    │                 │
    ├─ Start          ├─ INFO              ├─ Initialize     ├─ Timestamp
    ├─ Success        ├─ DEBUG             ├─ Add Steps      │
    ├─ Failure        ├─ WARN              ├─ Capture Error  └─ Level
    ├─ Skip           ├─ ERROR             ├─ Format Date    │
    └─ Finish         └─ Stack Trace       └─ Flush Report   └─ Details
         │                 │                    │
         ▼                 ▼                    ▼
    ExtentReports   logs/                 test-output/
                    ├─ selenium-ai-      HtmlReport/
                    │  demo.log           ├─ Selenium_ai_
                    └─ selenium-ai-      │  demo_
                       demo-error.log    │  DD-MM-YYYY_
                                        │  hh:mm:ss.html
                                        └─ (One per test run)
```

---

## 🔄 Data Flow for Failure Capture

```
Test Execution
    │
    ├─ Step 1: Execute Action
    │    │
    │    ▼
    │  Logger.info("Executing step...")  ──►  logs/selenium-ai-demo.log
    │
    ├─ Step 2: Assert
    │    │
    │    └─► AssertionError!
    │          │
    │          ▼
    │    catch(Exception e)
    │          │
    │          ├─► logger.error("Error", e)  ──► logs/ (both files)
    │          │
    │          ├─► TestListener.onTestFailure()
    │          │    │
    │          │    ├─ Get exception
    │          │    ├─ Get stack trace
    │          │    ├─ Get test parameters
    │          │    └─ Log all details
    │          │         │
    │          │         ▼
    │          │    ExtentTest.log(Status.FAIL)
    │          │         │
    │          │         ▼
    │          │  HTML Report
    │          │  ├─ Test Name
    │          │  ├─ Failure Step
    │          │  ├─ Exception Message
    │          │  ├─ Stack Trace
    │          │  │  (Class >> Method >> Line)
    │          │  └─ Timestamp
    │          │
    │          └─► Report File:
    │             test-output/HtmlReport/
    │             Selenium_ai_demo_
    │             DD-MM-YYYY_hh:mm:ss.html
    │
    └─ Test Complete
```

---

## 📂 Directory Structure

```
selenium-ai-demo/
│
├── src/
│   ├── main/
│   │   ├── java/com/qa/automation/
│   │   │   ├── base/
│   │   │   │   └── BaseTest.java          ◄─── Enhanced with Logger
│   │   │   ├── utils/
│   │   │   │   ├── LoggerUtil.java        ◄─── NEW
│   │   │   │   └── ReportUtil.java        ◄─── NEW
│   │   │   └── ...
│   │   │
│   │   └── resources/
│   │       ├── config.properties
│   │       └── log4j2.xml                 ◄─── NEW
│   │
│   └── test/
│       ├── java/com/qa/automation/
│       │   ├── tests/
│       │   │   ├── TestRunner.java        ◄─── Updated
│       │   │   ├── TestListener.java      ◄─── NEW
│       │   │   ├── ExampleTest.java       ◄─── NEW
│       │   │   └── RetryAnalyzer.java
│       │   └── ...
│       │
│       └── resources/
│           └── features/
│
├── logs/                                   ◄─── NEW (created at runtime)
│   ├── selenium-ai-demo.log               ◄─── All logs
│   └── selenium-ai-demo-error.log         ◄─── Errors only
│
├── test-output/
│   └── HtmlReport/                        ◄─── NEW (reports created here)
│       ├── Selenium_ai_demo_15-01-2026_14:30:45.html
│       └── ...
│
├── pom.xml                                ◄─── Updated
├── LOGGING_AND_REPORTING.md               ◄─── NEW
├── IMPLEMENTATION_GUIDE.md                ◄─── NEW
├── QUICK_REFERENCE.md                     ◄─── NEW
├── IMPLEMENTATION_SUMMARY.md              ◄─── NEW
└── README.md
```

---

## 🔌 Integration Points

### 1. Log4j2 Integration
```
log4j2.xml ──► Log4j2 API ──► Logger ──► LoggerUtil
                                          ▲
                                          │
                                    Used by BaseTest
```

### 2. ExtentReports Integration
```
ReportUtil ──► ExtentReports ──► ExtentSparkReporter
                    ▲                     │
                    │                     ▼
            TestListener ◄────────── HTML Report File
```

### 3. TestNG Integration
```
TestRunner ──► @Listeners({TestListener.class}) ──► TestListener
                                                     ▲
                                              Captures all events
```

---

## 🎯 Usage Pattern

```
Test Class
    │
    ├─ @BeforeMethod
    │   └─ initializeDriver()          ◄─── LoggerUtil logs init
    │   └─ Setup ExtentTest            ◄─── ReportUtil creates test
    │
    ├─ @Test
    │   ├─ logStep("Step 1")          ◄─── LoggerUtil logs step
    │   ├─ logAction("Action")        ◄─── LoggerUtil logs action
    │   ├─ Perform action
    │   ├─ Assert
    │   └─ log result
    │
    └─ @AfterMethod
        └─ quitDriver()               ◄─── LoggerUtil logs cleanup

During Execution:
    ├─ TestListener captures start
    ├─ LoggerUtil writes to file
    ├─ If success: Log pass + ExtentReports pass
    └─ If failure: Log error + TestListener captures + ExtentReports fail

After All Tests:
    └─ TestListener flushes report ──► Timestamp-named HTML file
```

---

## 📈 Logging Levels & Output

```
Logger.DEBUG
    │
    └─► Configuration:
        ├─ File: YES (selenium-ai-demo.log)
        ├─ Console: YES
        └─ Error File: NO
        
Logger.INFO
    │
    └─► Configuration:
        ├─ File: YES (selenium-ai-demo.log)
        ├─ Console: YES
        └─ Error File: NO

Logger.WARN
    │
    └─► Configuration:
        ├─ File: YES (selenium-ai-demo.log)
        ├─ Console: YES
        └─ Error File: NO

Logger.ERROR
    │
    └─► Configuration:
        ├─ File: YES (selenium-ai-demo.log)
        ├─ Console: YES
        └─ Error File: YES (selenium-ai-demo-error.log)
```

---

## 🔐 Thread Safety

```
TestListener ──► testMap (HashMap)  ◄─── Thread-Safe
                  └─ Static
                  └─ Stores ExtentTest per test

Each Test:
    ├─ Unique ExtentTest instance
    ├─ Unique Logger instance
    └─ Unique Log entries
```

---

## 📋 Summary

```
┌─────────────────────────────────────────────────────┐
│         LOGGING & REPORTING FRAMEWORK               │
├─────────────────────────────────────────────────────┤
│                                                     │
│  Input: Test Execution                            │
│    ├─ Steps                                        │
│    ├─ Actions                                      │
│    └─ Failures                                     │
│                                                     │
│  Processing:                                       │
│    ├─ LoggerUtil ──► Log4j2 ──► Files & Console  │
│    ├─ TestListener ──► Captures Events             │
│    └─ ReportUtil ──► ExtentReports                │
│                                                     │
│  Output:                                            │
│    ├─ logs/selenium-ai-demo.log                    │
│    ├─ logs/selenium-ai-demo-error.log              │
│    └─ test-output/HtmlReport/                      │
│        Selenium_ai_demo_DD-MM-YYYY_hh:mm:ss.html  │
│                                                     │
└─────────────────────────────────────────────────────┘
```

---

This architecture ensures:
✅ Centralized logging
✅ Comprehensive failure tracking
✅ Professional reporting
✅ Easy troubleshooting
✅ CI/CD ready
✅ Scalable design
