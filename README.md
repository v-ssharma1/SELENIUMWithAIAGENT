# Selenium AI Demo

This is a Selenium automation project using Cucumber and TestNG.

## Features

- BDD testing with Cucumber
- Automated browser testing with Selenium WebDriver
- WebDriverManager for automatic driver management

## How to run

### Run tests
```bash
mvn test
```

### Run main class
```bash
mvn exec:java -Dexec.mainClass="com.qa.automation.Main"
```

## Project Structure

- `src/main/java`: Main source code
- `src/test/java`: Test code including step definitions
- `src/test/resources/features`: Cucumber feature files