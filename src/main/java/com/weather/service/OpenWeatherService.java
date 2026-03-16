package com.weather.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.weather.model.ForecastDay;
import com.weather.model.Weather;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service class for OpenWeatherMap API integration.
 * Handles all API calls and JSON parsing.
 */
public class OpenWeatherService {

    // API Configuration
    private static final String API_BASE_URL = "https://api.openweathermap.org/data/2.5";
    private static final String API_KEY = "9ace53c14d8cd3efc0301b420a9798d3"; // Replace with your API key
    private static final String WEATHER_ENDPOINT = "/weather";
    private static final String FORECAST_ENDPOINT = "/forecast";
    private static final String GEO_ENDPOINT = "/geo/1.0/direct";

    private final HttpClient httpClient;
    private String statusMessage = "";
    private boolean isConnected = true;

    public OpenWeatherService() {
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Get current weather by city name
     */
    public Weather getWeatherByCity(String cityName, String units) throws WeatherServiceException {
        if (cityName == null || cityName.trim().isEmpty()) {
            throw new WeatherServiceException("City name cannot be empty");
        }

        try {
            // URL encode the city name to handle special characters and spaces
            String encodedCityName = URLEncoder.encode(cityName.trim(), StandardCharsets.UTF_8);
            String url = API_BASE_URL + WEATHER_ENDPOINT +
                    "?q=" + encodedCityName +
                    "&units=" + (units.equalsIgnoreCase("metric") ? "metric" : "imperial") +
                    "&appid=" + API_KEY;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                isConnected = false;
                if (response.statusCode() == 404) {
                    throw new WeatherServiceException("City not found: " + cityName);
                } else if (response.statusCode() == 401) {
                    throw new WeatherServiceException("Invalid API key");
                } else {
                    throw new WeatherServiceException("API Error: " + response.statusCode());
                }
            }

            isConnected = true;
            return parseWeatherResponse(response.body());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WeatherServiceException("Request interrupted", e);
        } catch (IOException e) {
            isConnected = false;
            throw new WeatherServiceException("Network error: " + e.getMessage(), e);
        }
    }

    /**
     * Get weather by coordinates
     */
    public Weather getWeatherByCoordinates(double latitude, double longitude, String units)
            throws WeatherServiceException {
        try {
            String url = API_BASE_URL + WEATHER_ENDPOINT +
                    "?lat=" + latitude +
                    "&lon=" + longitude +
                    "&units=" + (units.equalsIgnoreCase("metric") ? "metric" : "imperial") +
                    "&appid=" + API_KEY;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                isConnected = false;
                throw new WeatherServiceException("Failed to fetch weather data");
            }

            isConnected = true;
            return parseWeatherResponse(response.body());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WeatherServiceException("Request interrupted", e);
        } catch (IOException e) {
            isConnected = false;
            throw new WeatherServiceException("Network error: " + e.getMessage(), e);
        }
    }

    /**
     * Get 5-day forecast by city name
     */
    public List<ForecastDay> get5DayForecast(String cityName, String units) throws WeatherServiceException {
        if (cityName == null || cityName.trim().isEmpty()) {
            throw new WeatherServiceException("City name cannot be empty");
        }

        try {
            // URL encode the city name to handle special characters and spaces
            String encodedCityName = URLEncoder.encode(cityName.trim(), StandardCharsets.UTF_8);
            String url = API_BASE_URL + FORECAST_ENDPOINT +
                    "?q=" + encodedCityName +
                    "&units=" + (units.equalsIgnoreCase("metric") ? "metric" : "imperial") +
                    "&appid=" + API_KEY;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                isConnected = false;
                throw new WeatherServiceException("Failed to fetch forecast data");
            }

            isConnected = true;
            return parseForecastResponse(response.body());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WeatherServiceException("Request interrupted", e);
        } catch (IOException e) {
            isConnected = false;
            throw new WeatherServiceException("Network error: " + e.getMessage(), e);
        }
    }

    /**
     * Get coordinates by city name (for geolocation)
     */
    public double[] getCoordinatesByCity(String cityName) throws WeatherServiceException {
        try {
            // URL encode the city name to handle special characters and spaces
            String encodedCityName = URLEncoder.encode(cityName.trim(), StandardCharsets.UTF_8);
            String url = API_BASE_URL + GEO_ENDPOINT +
                    "?q=" + encodedCityName +
                    "&limit=1" +
                    "&appid=" + API_KEY;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WeatherServiceException("Failed to fetch coordinates");
            }

            JsonArray array = JsonParser.parseString(response.body()).getAsJsonArray();
            if (array.size() == 0) {
                throw new WeatherServiceException("City not found");
            }

            JsonObject first = array.get(0).getAsJsonObject();
            return new double[] {
                    first.get("lat").getAsDouble(),
                    first.get("lon").getAsDouble()
            };

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WeatherServiceException("Request interrupted", e);
        } catch (IOException e) {
            throw new WeatherServiceException("Network error: " + e.getMessage(), e);
        }
    }

    /**
     * Parse weather API response
     */
    private Weather parseWeatherResponse(String jsonResponse) throws WeatherServiceException {
        try {
            JsonObject json = JsonParser.parseString(jsonResponse).getAsJsonObject();
            Weather weather = new Weather();

            // City information
            weather.setCityName(json.get("name").getAsString());
            if (json.has("sys")) {
                JsonObject sys = json.getAsJsonObject("sys");
                if (sys.has("country")) {
                    weather.setCountryCode(sys.get("country").getAsString());
                }
                weather.setSunrise(sys.get("sunrise").getAsLong());
                weather.setSunset(sys.get("sunset").getAsLong());
            }

            // Main weather data
            JsonObject main = json.getAsJsonObject("main");
            weather.setTemperature(main.get("temp").getAsDouble());
            weather.setFeelsLike(main.get("feels_like").getAsDouble());
            weather.setTempMin(main.get("temp_min").getAsDouble());
            weather.setTempMax(main.get("temp_max").getAsDouble());
            weather.setPressure(main.get("pressure").getAsInt());
            weather.setHumidity(main.get("humidity").getAsInt());

            // Weather description
            JsonArray weatherArray = json.getAsJsonArray("weather");
            if (weatherArray.size() > 0) {
                JsonObject weatherObj = weatherArray.get(0).getAsJsonObject();
                weather.setMainWeather(weatherObj.get("main").getAsString());
                weather.setDescription(weatherObj.get("description").getAsString());
                weather.setIconCode(weatherObj.get("icon").getAsString());
            }

            // Wind data
            JsonObject wind = json.getAsJsonObject("wind");
            weather.setWindSpeed(wind.get("speed").getAsDouble());
            if (wind.has("deg")) {
                weather.setWindDegree(wind.get("deg").getAsDouble());
            }

            // Additional data
            if (json.has("clouds")) {
                weather.setCloudiness(json.getAsJsonObject("clouds").get("all").getAsInt());
            }
            if (json.has("visibility")) {
                weather.setVisibility(json.get("visibility").getAsDouble());
            }
            if (json.has("rain")) {
                JsonObject rain = json.getAsJsonObject("rain");
                if (rain.has("1h")) {
                    weather.setRainVolume(rain.get("1h").getAsDouble());
                }
            }
            if (json.has("snow")) {
                JsonObject snow = json.getAsJsonObject("snow");
                if (snow.has("1h")) {
                    weather.setSnowVolume(snow.get("1h").getAsDouble());
                }
            }

            weather.setLastUpdated(System.currentTimeMillis());
            statusMessage = "Weather updated successfully";
            return weather;

        } catch (Exception e) {
            statusMessage = "Error parsing weather data";
            throw new WeatherServiceException("Failed to parse weather response: " + e.getMessage(), e);
        }
    }

    /**
     * Parse forecast API response
     */
    private List<ForecastDay> parseForecastResponse(String jsonResponse) throws WeatherServiceException {
        try {
            JsonObject json = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray list = json.getAsJsonArray("list");
            List<ForecastDay> forecasts = new ArrayList<>();

            // Get only 5 days (40 items / 8 = 5 days with 3-hour intervals)
            for (int i = 0; i < Math.min(40, list.size()); i += 8) {
                JsonObject item = list.get(i).getAsJsonObject();
                ForecastDay forecast = new ForecastDay();

                forecast.setForecastTime(item.get("dt").getAsLong());

                JsonObject main = item.getAsJsonObject("main");
                forecast.setTemperature(main.get("temp").getAsDouble());
                forecast.setTempMin(main.get("temp_min").getAsDouble());
                forecast.setTempMax(main.get("temp_max").getAsDouble());
                forecast.setHumidity(main.get("humidity").getAsInt());
                forecast.setPressure(main.get("pressure").getAsInt());

                JsonObject wind = item.getAsJsonObject("wind");
                forecast.setWindSpeed(wind.get("speed").getAsDouble());

                JsonArray weatherArray = item.getAsJsonArray("weather");
                if (weatherArray.size() > 0) {
                    JsonObject weatherObj = weatherArray.get(0).getAsJsonObject();
                    forecast.setMainWeather(weatherObj.get("main").getAsString());
                    forecast.setDescription(weatherObj.get("description").getAsString());
                    forecast.setIconCode(weatherObj.get("icon").getAsString());
                }

                if (item.has("pop")) {
                    forecast.setRainProbability(item.get("pop").getAsDouble());
                }

                forecasts.add(forecast);
            }

            statusMessage = "Forecast updated successfully";
            return forecasts;

        } catch (Exception e) {
            statusMessage = "Error parsing forecast data";
            throw new WeatherServiceException("Failed to parse forecast response: " + e.getMessage(), e);
        }
    }

    /**
     * Get API connection status
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Get status message
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Set API key
     */
    public static void setApiKey(String key) {
        // Note: This would require making API_KEY non-final to work in production
        // For now, update the API_KEY constant directly
    }

    /**
     * Custom exception for weather service
     */
    public static class WeatherServiceException extends Exception {
        public WeatherServiceException(String message) {
            super(message);
        }

        public WeatherServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
