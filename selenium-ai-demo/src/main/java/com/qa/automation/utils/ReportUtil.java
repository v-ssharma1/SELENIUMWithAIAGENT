package com.qa.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for managing Extent Reports with custom naming convention.
 * Report naming format: ProjectName_DD-MM-YYYY_hh:mm:ss
 */
public class ReportUtil {

    private static final String PROJECT_NAME = "Selenium_ai_demo";
    private static final String REPORT_DIRECTORY = "test-output/HtmlReport";
    private static final String DATE_TIME_FORMAT = "dd-MM-yyyy_HH:mm:ss";
    
    private static ExtentReports extent;
    private static ExtentSparkReporter sparkReporter;
    private static final Logger logger = LoggerUtil.getLogger(ReportUtil.class);

    /**
     * Initialize Extent Reports with custom naming convention
     * @return ExtentReports instance
     */
    public static ExtentReports initializeReport() {
        String timeStamp = getFormattedDateTime();
        String reportName = PROJECT_NAME + "_" + timeStamp + ".html";
        String reportPath = REPORT_DIRECTORY + File.separator + reportName;

        // Create directory if it doesn't exist
        createReportDirectory();

        sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setReportName(PROJECT_NAME + " - Test Execution Report");
        sparkReporter.config().setDocumentTitle("Selenium AI Demo - Automation Report");
        sparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Project Name", PROJECT_NAME);
        extent.setSystemInfo("Environment", ConfigReader.getEnvironment());
        extent.setSystemInfo("Browser", ConfigReader.getBrowser());
        extent.setSystemInfo("Report Generated", timeStamp);

        logger.info("Report initialized: " + reportPath);
        return extent;
    }

    /**
     * Get the ExtentReports instance
     * @return ExtentReports instance
     */
    public static ExtentReports getReportInstance() {
        if (extent == null) {
            initializeReport();
        }
        return extent;
    }

    /**
     * Flush and close the report
     */
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
            logger.info("Report flushed and closed successfully");
        }
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
     * Get formatted date string
     * @return Formatted date string (DD-MM-YYYY)
     */
    public static String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(new Date());
    }

    /**
     * Get formatted time string
     * @return Formatted time string (hh:mm:ss)
     */
    public static String getFormattedTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Date());
    }

    /**
     * Create report directory if it doesn't exist
     */
    private static void createReportDirectory() {
        File reportDir = new File(REPORT_DIRECTORY);
        if (!reportDir.exists()) {
            if (reportDir.mkdirs()) {
                logger.info("Report directory created: " + REPORT_DIRECTORY);
            } else {
                logger.warn("Failed to create report directory: " + REPORT_DIRECTORY);
            }
        }
    }

    /**
     * Get the report path with timestamp
     * @return Full path to the report file
     */
    public static String getReportPath() {
        String timeStamp = getFormattedDateTime();
        String reportName = PROJECT_NAME + "_" + timeStamp + ".html";
        return REPORT_DIRECTORY + File.separator + reportName;
    }
}
