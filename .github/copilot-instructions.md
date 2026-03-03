# Copilot Instructions for Selenium AI Demo

## Project Overview
- **Architecture**: This is a multi-module Selenium automation project using Cucumber (BDD), TestNG, and the Page Object Model (POM). It supports multiple browsers and environments, with dynamic driver instantiation and environment-specific configuration.
- **Major Components**:
  - `src/main/java/com/`: Core automation logic, page objects, utilities
  - `src/main/resources/`: Environment configs (`config-*.properties`), logging (`log4j2.xml`)
  - `src/test/java/com/`: Test step definitions, hooks
  - `src/test/resources/features/`: Cucumber feature files
  - `pom.xml`: Maven build and dependency management
  - `testng.xml`: TestNG suite configuration
  - `logs/`, `test-output/`: Generated reports and logs

## Developer Workflows
- **Build & Test**:
  - Standard: `mvn test` (defaults to Dev environment)
  - Override environment: `mvn test -Denv=staging` or `-Denv=prod`
  - Override browser: `mvn test -Dbrowser=firefox` (see README for all options)
  - CI/CD: Jenkins parameters can override environment and browser
- **Debugging**:
  - Logs: See `logs/` and `test-output/` for Cucumber, Extent, Spark reports
  - Retry: TestNG retry analyzer is enabled for flaky tests
- **Reporting**:
  - Cucumber HTML/JSON/XML, Extent, Spark reports generated in `test-output/`

## Project-Specific Conventions
- **Page Object Model**: All locators and page methods are centralized in `src/main/java/com/`
- **Factory Pattern**: WebDriver instantiation is dynamic based on config and CLI args
- **Environment Configs**: Properties files in `src/main/resources/` and `src/test/resources/` (Jenkins can override)
- **Test Data**: Excel files managed via Apache POI
- **Retry Mechanism**: Configurable via TestNG, see `pom.xml` and test classes

## Integration Points
- **External Dependencies**:
  - Selenium WebDriver (managed via WebDriverManager)
  - Cucumber, TestNG, Apache POI
  - Jenkins for CI/CD
- **Cross-Component Communication**:
  - Step definitions call page objects
  - Configurations loaded at runtime from properties files

## Examples
- To run all tests in staging on Firefox:
  ```bash
  mvn test -Denv=staging -Dbrowser=firefox
  ```
- To view the latest test report:
  Open `test-output/HtmlReport/ExtentHtml.html` or `test-output/CucumberReport/cucumber-report.html`

## Key Files & Directories
- `src/main/java/com/` — Page objects, utilities
- `src/test/resources/features/` — Cucumber features
- `src/main/resources/config-*.properties` — Environment configs
- `test-output/` — Reports
- `pom.xml`, `testng.xml` — Build and test config

---
For more details, see the project README files.
