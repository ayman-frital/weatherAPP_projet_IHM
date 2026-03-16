package com.weather.model;

import java.io.Serializable;

/**
 * Model class representing a favorite city.
 */
public class FavoriteCity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cityName;
    private String countryCode;
    private double latitude;
    private double longitude;
    private long addedDate;

    public FavoriteCity() {
        this.addedDate = System.currentTimeMillis();
    }

    public FavoriteCity(String cityName, String countryCode, double latitude, double longitude) {
        this.cityName = cityName;
        this.countryCode = countryCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.addedDate = System.currentTimeMillis();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(long addedDate) {
        this.addedDate = addedDate;
    }

    @Override
    public String toString() {
        return cityName + (countryCode != null ? ", " + countryCode : "");
    }
}
