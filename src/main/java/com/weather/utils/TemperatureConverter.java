package com.weather.utils;

/**
 * Utility class for temperature conversions.
 */
public class TemperatureConverter {

    /**
     * Convert Celsius to Fahrenheit
     */
    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    /**
     * Convert Fahrenheit to Celsius
     */
    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    /**
     * Format temperature with appropriate unit
     */
    public static String formatTemperature(double temperature, boolean isCelsius) {
        String unit = isCelsius ? "°C" : "°F";
        return String.format("%.1f%s", temperature, unit);
    }

    /**
     * Format temperature for display without decimal places
     */
    public static String formatTemperatureWholeNumber(double temperature, boolean isCelsius) {
        String unit = isCelsius ? "°C" : "°F";
        return String.format("%.0f%s", temperature, unit);
    }

    /**
     * Convert temperature based on unit setting
     */
    public static double convertTemperature(double celsiusTemp, boolean isCelsius) {
        return isCelsius ? celsiusTemp : celsiusToFahrenheit(celsiusTemp);
    }
}
