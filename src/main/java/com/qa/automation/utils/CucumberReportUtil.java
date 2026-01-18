package com.qa.automation.utils;

import org.apache.logging.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for managing Cucumber Reports.
 * Provides report path management and utilities for Cucumber test execution.
 */
public class CucumberReportUtil {

    private static final String CUCUMBER_REPORT_DIRECTORY = "test-output/CucumberReport";
    private static final String DATE_TIME_FORMAT = "dd-MM-yyyy_HH:mm:ss";
    private static final Logger logger = LoggerUtil.getLogger(CucumberReportUtil.class);

    /**
     * Initialize Cucumber report directory
     */
    public static void initializeCucumberReportDirectory() {
        createReportDirectory();
    }

    /**
     * Get the Cucumber report directory path
     * @return Full path to Cucumber report directory
     */
    public static String getCucumberReportDirectory() {
        return CUCUMBER_REPORT_DIRECTORY;
    }

    /**
     * Get the Cucumber HTML report file path
     * @return Full path to cucumber-report.html
     */
    public static String getCucumberHtmlReportPath() {
        return CUCUMBER_REPORT_DIRECTORY + File.separator + "cucumber-report.html";
    }

    /**
     * Get the Cucumber JSON report file path
     * @return Full path to cucumber.json
     */
    public static String getCucumberJsonReportPath() {
        return CUCUMBER_REPORT_DIRECTORY + File.separator + "cucumber.json";
    }

    /**
     * Get the Cucumber XML report file path
     * @return Full path to cucumber.xml
     */
    public static String getCucumberXmlReportPath() {
        return CUCUMBER_REPORT_DIRECTORY + File.separator + "cucumber.xml";
    }

    /**
     * Get formatted date-time string
     * @return Formatted date-time string (DD-MM-YYYY_hh:mm:ss)
     */
    public static String getFormattedDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        return dateFormat.format(new Date());
    }

    /**
     * Create Cucumber report directory if it doesn't exist
     */
    private static void createReportDirectory() {
        File reportDir = new File(CUCUMBER_REPORT_DIRECTORY);
        if (!reportDir.exists()) {
            if (reportDir.mkdirs()) {
                logger.info("Cucumber report directory created: " + CUCUMBER_REPORT_DIRECTORY);
            } else {
                logger.warn("Failed to create Cucumber report directory: " + CUCUMBER_REPORT_DIRECTORY);
            }
        }
    }

    /**
     * Verify that Cucumber report was generated
     * @return true if report exists, false otherwise
     */
    public static boolean isCucumberReportGenerated() {
        File reportFile = new File(getCucumberHtmlReportPath());
        return reportFile.exists();
    }

    /**
     * Log Cucumber report location
     */
    public static void logReportLocation() {
        logger.info("================================================");
        logger.info("Cucumber Report Generated");
        logger.info("Location: " + getCucumberHtmlReportPath());
        logger.info("================================================");
    }
}
