package com.qa.automation.utils;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * Utility class for capturing screenshots in Selenium tests.
 */
public class ScreenshotUtil {
    private static final Logger logger = LoggerUtil.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_DIR = "test-output/screenshots";

    /**
     * Capture screenshot and return the file path.
     * @param driver WebDriver instance
     * @param name   Screenshot name prefix
     * @return Path to the saved screenshot file
     */
    public static String captureScreenshot(WebDriver driver, String name) {
        try {
            if (!(driver instanceof TakesScreenshot)) {
                logger.warn("Driver does not support screenshots");
                return null;
            }
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            String fileName = name + "_" + timestamp + ".png";
            Path screenshotDir = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }
            Path dest = screenshotDir.resolve(fileName);
            Files.copy(screenshot.toPath(), dest);
            logger.info("Screenshot saved: " + dest);
            return dest.toString();
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Capture screenshot and return as Base64 string (for embedding in reports).
     * @param driver WebDriver instance
     * @return Base64-encoded screenshot
     */
    public static String captureScreenshotBase64(WebDriver driver) {
        try {
            if (!(driver instanceof TakesScreenshot)) {
                logger.warn("Driver does not support screenshots");
                return null;
            }
            byte[] imageBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot as Base64: " + e.getMessage(), e);
            return null;
        }
    }
}
