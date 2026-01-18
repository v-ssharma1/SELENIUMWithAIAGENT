package com.qa.automation.tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

/**
 * Test Runner for Cucumber-based test execution.
 * Includes custom reporting and failure tracking via TestListener.
 * Report naming format: Selenium_ai_demo_DD-MM-YYYY_hh:mm:ss.html
 */
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.qa.automation.stepdefinitions",
    plugin = {
        "pretty",
        "html:test-output/CucumberReport/cucumber-report.html",
        "json:test-output/CucumberReport/cucumber.json",
        "junit:test-output/CucumberReport/cucumber.xml"
    },
    monochrome = false,
    publish = false
)
@Listeners({TestListener.class})
@Test(retryAnalyzer = RetryAnalyzer.class)
public class TestRunner extends AbstractTestNGCucumberTests {
}
