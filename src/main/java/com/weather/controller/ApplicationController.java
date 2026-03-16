package com.weather.controller;

import com.weather.model.AppSettings;
import com.weather.model.FavoriteCity;
import com.weather.model.Weather;
import com.weather.service.OpenWeatherService;
import com.weather.ui.PageType;
import com.weather.utils.StorageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Main application controller that manages the application state and
 * coordinates between services and UI.
 * This follows the MVC pattern and acts as a bridge between the service layer
 * and UI layer.
 */
public class ApplicationController {

    private final OpenWeatherService weatherService;
    private AppSettings settings;
    private ObservableList<FavoriteCity> favorites;
    private Weather currentWeather;
    private PageType currentPage;

    // Observable lists for UI binding
    private final ObservableList<String> favoritesCityNames;

    // Listeners for UI updates
    private Runnable weatherUpdateListener;
    private Runnable favoritesUpdateListener;
    private Runnable settingsUpdateListener;
    private Runnable pageChangeListener;
    private java.util.function.Consumer<String> errorListener;

    public ApplicationController() {
        this.weatherService = new OpenWeatherService();
        this.settings = StorageManager.loadSettings();
        this.favorites = FXCollections.observableArrayList(StorageManager.loadFavoriteCities());
        this.favoritesCityNames = FXCollections.observableArrayList();
        this.currentPage = PageType.WELCOME;
        updateFavoriteCityNames();
    }

    /**
     * Search for weather by city name
     */
    public void searchWeather(String cityName) {
        new Thread(() -> {
            try {
                String units = settings.getTemperatureUnit() == AppSettings.TemperatureUnit.CELSIUS ? "metric"
                        : "imperial";
                currentWeather = weatherService.getWeatherByCity(cityName, units);
                settings.setLastSearchedCity(cityName);
                StorageManager.saveSettings(settings);
                // Update UI on JavaFX Application Thread
                javafx.application.Platform.runLater(() -> {
                    if (weatherUpdateListener != null) {
                        weatherUpdateListener.run();
                    }
                });
            } catch (OpenWeatherService.WeatherServiceException e) {
                System.err.println("Weather search error: " + e.getMessage());
                // Notify UI of error on JavaFX Application Thread
                String errorMsg = e.getMessage();
                javafx.application.Platform.runLater(() -> {
                    if (errorListener != null) {
                        errorListener.accept(errorMsg);
                    }
                });
            }
        }).start();
    }

    /**
     * Add a city to favorites
     */
    public void addToFavorites(String cityName, String countryCode, double latitude, double longitude) {
        FavoriteCity favoriteCity = new FavoriteCity(cityName, countryCode, latitude, longitude);
        if (!favorites.stream().anyMatch(f -> f.getCityName().equalsIgnoreCase(cityName))) {
            favorites.add(favoriteCity);
            StorageManager.saveFavoriteCities(new java.util.ArrayList<>(favorites));
            updateFavoriteCityNames();
            if (favoritesUpdateListener != null) {
                favoritesUpdateListener.run();
            }
        }
    }

    /**
     * Remove a city from favorites
     */
    public void removeFromFavorites(FavoriteCity favoriteCity) {
        favorites.remove(favoriteCity);
        StorageManager.saveFavoriteCities(new java.util.ArrayList<>(favorites));
        updateFavoriteCityNames();
        if (favoritesUpdateListener != null) {
            favoritesUpdateListener.run();
        }
    }

    /**
     * Set error listener for error notifications
     */
    public void setErrorListener(java.util.function.Consumer<String> listener) {
        this.errorListener = listener;
    }

    /**
     * Update favorite city names for UI binding
     */
    private void updateFavoriteCityNames() {
        favoritesCityNames.clear();
        for (FavoriteCity city : favorites) {
            favoritesCityNames.add(city.toString());
        }
    }

    /**
     * Get current temperature settings (Celsius or Fahrenheit)
     */
    public double getTemperatureInCurrentUnit(double celsiusTemp) {
        if (settings.getTemperatureUnit() == AppSettings.TemperatureUnit.FAHRENHEIT) {
            return (celsiusTemp * 9 / 5) + 32;
        }
        return celsiusTemp;
    }

    /**
     * Get temperature unit symbol
     */
    public String getTemperatureUnitSymbol() {
        return settings.getTemperatureUnit().getSymbol();
    }

    /**
     * Change page
     */
    public void changePage(PageType pageType) {
        currentPage = pageType;
        if (pageChangeListener != null) {
            pageChangeListener.run();
        }
    }

    /**
     * Save settings
     */
    public void saveSettings() {
        StorageManager.saveSettings(settings);
        if (settingsUpdateListener != null) {
            settingsUpdateListener.run();
        }
    }

    // Getters
    public OpenWeatherService getWeatherService() {
        return weatherService;
    }

    public AppSettings getSettings() {
        return settings;
    }

    public ObservableList<FavoriteCity> getFavorites() {
        return favorites;
    }

    public ObservableList<String> getFavoriteCityNames() {
        return favoritesCityNames;
    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(Weather weather) {
        this.currentWeather = weather;
    }

    public PageType getCurrentPage() {
        return currentPage;
    }

    // Event listeners
    public void setWeatherUpdateListener(Runnable listener) {
        this.weatherUpdateListener = listener;
    }

    public void setFavoritesUpdateListener(Runnable listener) {
        this.favoritesUpdateListener = listener;
    }

    public void setSettingsUpdateListener(Runnable listener) {
        this.settingsUpdateListener = listener;
    }

    public void setPageChangeListener(Runnable listener) {
        this.pageChangeListener = listener;
    }
}
