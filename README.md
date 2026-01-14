# Selenium AI Demo

This is a Selenium automation project using Cucumber and TestNG.

## Features

- BDD testing with Cucumber
- Automated browser testing with Selenium WebDriver
- WebDriverManager for automatic driver management
- Retry mechanism for failed/flaky tests (retries once)
- Excel data reading for test data management
- Configuration management with environment overrides
- Comprehensive Cucumber reporting (HTML, JSON, JUnit XML)

## How to run

### Run tests
```bash
mvn test
```

## Reports

After running tests, reports are generated in the following locations:

### Cucumber Reports
- **HTML Report**: `target/cucumber-reports.html` - Detailed Cucumber HTML report
- **JSON Report**: `target/cucumber.json` - Machine-readable JSON format
- **JUnit XML Report**: `target/cucumber.xml` - XML format for CI/CD integration

Open the HTML report in a browser to view detailed test execution results.

## Configuration Management

The project uses a configuration file for environment settings and wait times.

### Config File
- **Location**: `src/main/resources/config.properties`
- **Settings**:
  - `environment`: Environment name (default: dev)
  - `url`: Base URL (default: https://www.google.com)
  - `implicitWait`: Implicit wait time in seconds (default: 10)
  - `explicitWait`: Explicit wait time in seconds (default: 20)

### Jenkins Override
Environment and URL can be overridden by Jenkins job parameters:
- Set `environment` and `url` as job parameters
- These will override the config file values
- Wait times are always read from config file

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

- `src/main/java`: Main source code
- `src/main/resources/config.properties`: Configuration file
- `src/test/java`: Test code including step definitions and test runner
- `src/test/resources/features`: Cucumber feature files
- `src/test/resources/testdata.xlsx`: Test data Excel file
- `src/test/resources/extent.properties`: Extent Reports configuration (if used)
- `src/test/resources/extent-config.xml`: Extent Reports customization (if used)