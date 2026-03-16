package com.weather.model;

import java.io.Serializable;

/**
 * Model class for application settings and configuration.
 */
public class AppSettings implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum TemperatureUnit {
        CELSIUS("°C"),
        FAHRENHEIT("°F");

        private final String symbol;

        TemperatureUnit(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    private TemperatureUnit temperatureUnit;
    private int autoRefreshInterval; // in minutes
    private boolean enableNotifications;
    private boolean enableWeatherAlerts;
    private double alertTemperatureThreshold;
    private boolean enableAutoGeolocation;
    private String lastSearchedCity;

    public AppSettings() {
        this.temperatureUnit = TemperatureUnit.CELSIUS;
        this.autoRefreshInterval = 30;
        this.enableNotifications = true;
        this.enableWeatherAlerts = false;
        this.alertTemperatureThreshold = 35.0;
        this.enableAutoGeolocation = false;
    }

    // Getters and Setters
    public TemperatureUnit getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(TemperatureUnit temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public int getAutoRefreshInterval() {
        return autoRefreshInterval;
    }

    public void setAutoRefreshInterval(int autoRefreshInterval) {
        this.autoRefreshInterval = Math.max(5, autoRefreshInterval); // Minimum 5 minutes
    }

    public boolean isEnableNotifications() {
        return enableNotifications;
    }

    public void setEnableNotifications(boolean enableNotifications) {
        this.enableNotifications = enableNotifications;
    }

    public boolean isEnableWeatherAlerts() {
        return enableWeatherAlerts;
    }

    public void setEnableWeatherAlerts(boolean enableWeatherAlerts) {
        this.enableWeatherAlerts = enableWeatherAlerts;
    }

    public double getAlertTemperatureThreshold() {
        return alertTemperatureThreshold;
    }

    public void setAlertTemperatureThreshold(double alertTemperatureThreshold) {
        this.alertTemperatureThreshold = alertTemperatureThreshold;
    }

    public boolean isEnableAutoGeolocation() {
        return enableAutoGeolocation;
    }

    public void setEnableAutoGeolocation(boolean enableAutoGeolocation) {
        this.enableAutoGeolocation = enableAutoGeolocation;
    }

    public String getLastSearchedCity() {
        return lastSearchedCity;
    }

    public void setLastSearchedCity(String lastSearchedCity) {
        this.lastSearchedCity = lastSearchedCity;
    }
}
