# Selenium AI Demo

This is a Selenium automation project using Cucumber and TestNG.

## Features

- **BDD Testing**: Cucumber with Gherkin syntax for business-readable tests
- **Multi-Browser Support**: Chrome, Firefox, Edge, Safari, and headless variants
- **Factory Design Pattern**: Dynamic driver instantiation based on configuration
- **Page Object Model (POM)**: Centralized locators and page methods
- **Selenium WebDriver**: Latest version with WebDriverManager for auto driver management
- **TestNG Framework**: Test execution and retry analyzer for flaky tests
- **Retry Mechanism**: Automatic retry for failed tests (configurable)
- **Excel Data Management**: POI for test data reading/writing
- **Environment-Specific Configuration**: Dev, Staging, Production with Jenkins overrides
- **Comprehensive Reporting**: Cucumber HTML, JSON, and JUnit XML reports
- **CI/CD Ready**: Jenkins parameter support for dynamic test execution

## How to run

### Run tests with default configuration (Dev environment)
```bash
mvn test
```

### Run tests with specific environment
```bash
# Run with Dev environment
mvn test -Denv=dev

# Run with Staging environment
mvn test -Denv=staging

# Run with Production environment
mvn test -Denv=prod
```

### Run tests with browser override
```bash
# Run with Chrome (default)
mvn test -Dbrowser=chrome

# Run with Firefox
mvn test -Dbrowser=firefox

# Run with Edge
mvn test -Dbrowser=edge

# Run with Safari
mvn test -Dbrowser=safari

# Run with Headless Chrome
mvn test -Dbrowser=headless-chrome

# Run with Headless Firefox
mvn test -Dbrowser=headless-firefox
```

### Run tests with custom wait times
```bash
# Override implicit and explicit wait times
mvn test -DimplicitWait=5 -DexplicitWait=10
```

### Run tests with environment + browser + wait time overrides
```bash
# Complete example: Staging environment, Firefox, custom waits
mvn test -Denv=staging -Dbrowser=firefox -DimplicitWait=15 -DexplicitWait=25
```

### Run tests with URL override
```bash
# Override the base URL
mvn test -Durl=https://custom-url.com
```

## Reports

After running tests, reports are generated in the following locations:

### Cucumber Reports
- **HTML Report**: `target/cucumber-reports.html` - Detailed Cucumber HTML report
- **JSON Report**: `target/cucumber.json` - Machine-readable JSON format
- **JUnit XML Report**: `target/cucumber.xml` - XML format for CI/CD integration

Open the HTML report in a browser to view detailed test execution results.

## Configuration Management

The project supports **environment-specific configuration files** with **Jenkins parameter overrides** for maximum flexibility.

### Environment-Specific Config Files
The framework loads configuration from environment-specific files in priority order:

1. **Development** (`src/main/resources/config-dev.properties`)
   - URL: https://www.google.com
   - Browser: chrome
   - Implicit Wait: 10s
   - Explicit Wait: 20s

2. **Staging** (`src/main/resources/config-staging.properties`)
   - URL: https://staging.google.com
   - Browser: headless-chrome
   - Implicit Wait: 15s
   - Explicit Wait: 25s

3. **Production** (`src/main/resources/config-prod.properties`)
   - URL: https://www.google.com
   - Browser: headless-chrome
   - Implicit Wait: 20s
   - Explicit Wait: 30s

4. **Default Fallback** (`src/main/resources/config.properties`)
   - Used when specific environment config is not found

### Configuration Parameters

| Parameter | Default | Override | Description |
|-----------|---------|----------|-------------|
| `env` | dev | `-Denv=staging` | Which environment config to load |
| `environment` | dev | `-Denvironment=staging` | Environment name (from config) |
| `url` | https://www.google.com | `-Durl=https://custom.com` | Base URL for tests |
| `browser` | chrome | `-Dbrowser=firefox` | Browser to use (chrome, firefox, edge, safari, headless-chrome, headless-firefox) |
| `implicitWait` | 10 | `-DimplicitWait=5` | Implicit wait time in seconds |
| `explicitWait` | 20 | `-DexplicitWait=10` | Explicit wait time in seconds |

### How Configuration Loading Works

1. System checks for `-Denv=<env>` parameter from Maven/Jenkins
2. If provided, loads `config-<env>.properties` file
3. If not provided or file not found, falls back to `config.properties`
4. System properties (Jenkins parameters) override all file values

### Example: Loading with Jenkins
```bash
# Jenkins job passes environment parameter
mvn test -Denv=staging -Dbrowser=headless-firefox -DimplicitWait=15
```

This will:
- Load `config-staging.properties` as base configuration
- Override browser to headless-firefox
- Override implicit wait to 15 seconds
- Keep explicit wait from staging config (25 seconds)

### Usage in Code
```java
String env = ConfigReader.getEnvironment();
String url = ConfigReader.getUrl();
int implicitWait = ConfigReader.getImplicitWait();
int explicitWait = ConfigReader.getExplicitWait();
```

## Test Data Management

Test data is managed using Excel files for easy maintenance and updates.

### Excel Data File
- **Location**: `src/test/resources/testdata.xlsx`
- **Columns**: `user name`, `Password`
- **Sample Data**:
  - user1, pass1
  - user2, pass2
  - admin, admin123

### Reading Excel Data
Use the `ExcelReader` utility class to read data from Excel files:

```java
List<Map<String, String>> data = ExcelReader.readExcelData("src/test/resources/testdata.xlsx");
for (Map<String, String> row : data) {
    String username = row.get("user name");
    String password = row.get("Password");
    // Use in tests
}
```

## Project Structure

- `src/main/java/com/qa/automation/base`: Base test class with driver initialization
- `src/main/java/com/qa/automation/factory`: Browser factory and factory pattern
- `src/main/java/com/qa/automation/locators`: Centralized locators (PageLocators enum)
- `src/main/java/com/qa/automation/pages`: Page Object Model classes
- `src/main/java/com/qa/automation/utils`: Utility classes (ConfigReader, ExcelReader/Writer)
- `src/main/resources`: Configuration files (dev, staging, prod)
- `src/test/java/com/qa/automation/stepdefinitions`: Cucumber step definitions
- `src/test/java/com/qa/automation/tests`: TestRunner for Cucumber
- `src/test/resources/features`: Cucumber feature files
- `src/test/resources`: Test data and Cucumber reports output

## Design Patterns Used

### 1. Factory Pattern
**File**: `src/main/java/com/qa/automation/factory/`

The BrowserFactory class uses the Factory pattern to create WebDriver instances based on browser type:
- Supports multiple browsers (Chrome, Firefox, Edge, Safari)
- Headless variants available
- Browser type determined from configuration or Jenkins parameters
- Easy to extend with new browsers

```java
BrowserType browserType = BrowserType.fromString(ConfigReader.getBrowser());
driver = BrowserFactory.createDriver(browserType);
```

### 2. Page Object Model (POM)
**Files**: 
- `src/main/java/com/qa/automation/pages/BasePage.java`
- `src/main/java/com/qa/automation/pages/GoogleHomePage.java`
- `src/main/java/com/qa/automation/locators/PageLocators.java`

POM separates page elements and actions from test logic:
- **PageLocators**: Enum containing all page element locators
- **BasePage**: Abstract base class with common element interaction methods
- **GoogleHomePage**: Concrete page object with Google-specific methods

Benefits:
- Centralized locator management
- Easier maintenance (change locator in one place)
- Reusable page methods
- Business-level test steps instead of raw WebDriver calls

```java
// In step definitions
googleHomePage.searchKeyword("selenium");
String title = googleHomePage.getGooglePageTitle();
```

## Architecture Overview

```
┌─────────────────────────────────────────┐
│     Cucumber Step Definitions           │
│   (LoginSteps, other step files)        │
└─────────────────┬───────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────┐
│      Page Object Model Layer            │
│   (GoogleHomePage, other page objects)  │
└─────────────────┬───────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────┐
│     Locators & Base Page                │
│   (PageLocators, BasePage)              │
└─────────────────┬───────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────┐
│   Browser Factory & BaseTest            │
│  (BrowserFactory, BaseTest)             │
└─────────────────┬───────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────┐
│    Selenium WebDriver                   │
│   (Chrome, Firefox, Edge, Safari)       │
└─────────────────────────────────────────┘
```