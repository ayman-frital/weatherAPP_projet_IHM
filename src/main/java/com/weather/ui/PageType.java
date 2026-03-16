package com.weather.ui;

/**
 * Enum for different pages in the application.
 */
public enum PageType {
    WELCOME("Welcome"),
    DASHBOARD("Dashboard"),
    FORECAST("5-Day Forecast"),
    CHARTS("Weather Charts"),
    FAVORITES("Favorite Cities"),
    ADVANCED("Advanced Details"),
    SETTINGS("Settings");

    private final String displayName;

    PageType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
