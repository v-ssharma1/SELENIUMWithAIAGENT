package com.qa.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for logging operations across the framework.
 * Provides centralized logging functionality using Log4j2.
 */
public class LoggerUtil {

    /**
     * Get logger instance for the specified class
     * @param clazz The class for which logger is needed
     * @return Logger instance
     */
    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }

    /**
     * Get logger instance for the specified class name
     * @param className The class name for which logger is needed
     * @return Logger instance
     */
    public static Logger getLogger(String className) {
        return LogManager.getLogger(className);
    }
}
