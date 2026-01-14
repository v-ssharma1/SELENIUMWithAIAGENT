# Selenium AI Demo

This is a Selenium automation project using Cucumber and TestNG.

## Features

- BDD testing with Cucumber
- Automated browser testing with Selenium WebDriver
- WebDriverManager for automatic driver management
- Comprehensive reporting with Extent Reports and Cucumber native reports

## How to run

### Run tests
```bash
mvn test
```

## Reports

After running tests, reports are generated in the following locations:

### Extent Reports
- **Spark HTML Report**: `test-output/SparkReport/Spark.html` - Interactive report with charts and detailed logs
- **PDF Report**: `test-output/PdfReport/ExtentPdf.pdf` - PDF version of the test report
- **HTML Report**: `test-output/HtmlReport/ExtentHtml.html` - Additional HTML report

### Cucumber Reports
- **HTML Report**: `target/cucumber-reports.html` - Detailed Cucumber HTML report
- **JSON Report**: `target/cucumber.json` - Machine-readable JSON format
- **JUnit XML Report**: `target/cucumber.xml` - XML format for CI/CD integration

Open the HTML reports in a browser to view detailed test execution results.

## Project Structure

- `src/main/java`: Main source code
- `src/test/java`: Test code including step definitions
- `src/test/resources/features`: Cucumber feature files
- `src/test/resources/extent.properties`: Extent Reports configuration
- `src/test/resources/extent-config.xml`: Extent Reports customization