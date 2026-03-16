package com.weather.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weather.model.AppSettings;
import com.weather.model.FavoriteCity;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for persisting data to disk using JSON format.
 */
public class StorageManager {

    private static final String DATA_DIRECTORY = System.getProperty("user.home") + File.separator + ".weather_app";
    private static final String FAVORITES_FILE = DATA_DIRECTORY + File.separator + "favorites.json";
    private static final String SETTINGS_FILE = DATA_DIRECTORY + File.separator + "settings.json";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static {
        // Create data directory if it doesn't exist
        Path dataPath = Paths.get(DATA_DIRECTORY);
        try {
            Files.createDirectories(dataPath);
        } catch (IOException e) {
            System.err.println("Error creating data directory: " + e.getMessage());
        }
    }

    /**
     * Save favorite cities to file
     */
    public static void saveFavoriteCities(List<FavoriteCity> favorites) {
        try {
            String json = gson.toJson(favorites);
            Files.write(Paths.get(FAVORITES_FILE), json.getBytes());
            System.out.println("Favorites saved successfully");
        } catch (IOException e) {
            System.err.println("Error saving favorites: " + e.getMessage());
        }
    }

    /**
     * Load favorite cities from file
     */
    public static List<FavoriteCity> loadFavoriteCities() {
        try {
            if (Files.exists(Paths.get(FAVORITES_FILE))) {
                String json = new String(Files.readAllBytes(Paths.get(FAVORITES_FILE)));
                FavoriteCity[] favorites = gson.fromJson(json, FavoriteCity[].class);
                return new ArrayList<>(java.util.Arrays.asList(favorites != null ? favorites : new FavoriteCity[0]));
            }
        } catch (IOException e) {
            System.err.println("Error loading favorites: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Save application settings to file
     */
    public static void saveSettings(AppSettings settings) {
        try {
            String json = gson.toJson(settings);
            Files.write(Paths.get(SETTINGS_FILE), json.getBytes());
            System.out.println("Settings saved successfully");
        } catch (IOException e) {
            System.err.println("Error saving settings: " + e.getMessage());
        }
    }

    /**
     * Load application settings from file
     */
    public static AppSettings loadSettings() {
        try {
            if (Files.exists(Paths.get(SETTINGS_FILE))) {
                String json = new String(Files.readAllBytes(Paths.get(SETTINGS_FILE)));
                return gson.fromJson(json, AppSettings.class);
            }
        } catch (IOException e) {
            System.err.println("Error loading settings: " + e.getMessage());
        }
        return new AppSettings();
    }

    /**
     * Export weather data to CSV file
     */
    public static void exportToCSV(String cityName, String csvContent) throws IOException {
        String filename = DATA_DIRECTORY + File.separator + "weather_" + cityName + "_" + System.currentTimeMillis()
                + ".csv";
        Files.write(Paths.get(filename), csvContent.getBytes());
    }

    /**
     * Get the data directory path
     */
    public static String getDataDirectory() {
        return DATA_DIRECTORY;
    }
}
