package com.qa.automation.tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.qa.automation.stepdefinitions",
    plugin = {"pretty", "html:target/cucumber-reports.html", "json:target/cucumber.json", "junit:target/cucumber.xml", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
