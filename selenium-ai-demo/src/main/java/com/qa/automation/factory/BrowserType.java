package com.qa.automation.factory;

public enum BrowserType {
    CHROME("chrome"),
    FIREFOX("firefox"),
    EDGE("edge"),
    SAFARI("safari"),
    HEADLESS_CHROME("headless-chrome"),
    HEADLESS_FIREFOX("headless-firefox");

    private final String browserName;

    BrowserType(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserName() {
        return browserName;
    }

    public static BrowserType fromString(String browserName) {
        for (BrowserType type : BrowserType.values()) {
            if (type.browserName.equalsIgnoreCase(browserName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unsupported browser: " + browserName);
    }
}
