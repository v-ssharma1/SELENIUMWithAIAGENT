package com.qa.automation.stepdefinitions;

import com.qa.automation.base.BaseTest;
import com.qa.automation.utils.ScreenshotUtil;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;

public class ScreenshotHooks extends BaseTest {
    @AfterStep
    public void attachScreenshot(Scenario scenario) {
        if (driver != null) {
            // Attach screenshot to Cucumber report after every step
            byte[] screenshot = ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Step Screenshot");
        }
    }
}
