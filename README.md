# Selenium AI Demo

This is a Selenium automation project using Cucumber and TestNG.

## Features

- BDD testing with Cucumber
- Automated browser testing with Selenium WebDriver
- WebDriverManager for automatic driver management
- Retry mechanism for failed/flaky tests (retries once)
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

## Project Structure

- `src/main/java`: Main source code
- `src/test/java`: Test code including step definitions and test runner
- `src/test/resources/features`: Cucumber feature files
- `src/test/resources/extent.properties`: Extent Reports configuration (if used)
- `src/test/resources/extent-config.xml`: Extent Reports customization (if used)