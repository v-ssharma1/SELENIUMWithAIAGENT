package com.qa.automation.tests;

import com.qa.automation.base.BaseTest;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void openGoogleTest() {
        driver.get("https://www.google.com");
        System.out.println("Page title: " + driver.getTitle());
    }
}
