package com.qa.automation.tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Test;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.qa.automation.stepdefinitions",
    plugin = {"pretty", "html:target/cucumber-reports.html", "json:target/cucumber.json", "junit:target/cucumber.xml"}
)
@Test(retryAnalyzer = RetryAnalyzer.class)
public class TestRunner extends AbstractTestNGCucumberTests {
}
